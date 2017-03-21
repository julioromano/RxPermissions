package net.kjulio.rxpermissions.internal;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class RequestProcessor {

    private static final RequestProcessor instance = new RequestProcessor();
    private static final AtomicInteger requestIdGenerator = new AtomicInteger(1);

    private final Map<Integer, PermissionsRequest> pool = new ConcurrentHashMap<>();

    private RequestProcessor() {
    }

    static RequestProcessor getInstance() {
        return instance;
    }

    // TODO: Ensure that process() will be invoked on the ui thread and that notify() on the caller's thread.

    void processRequest(@NonNull Context context, @NonNull PermissionsRequest request) {

        purgePool();

        int newRequestId = requestIdGenerator.getAndIncrement();
        request.requestId = newRequestId;
        pool.put(newRequestId, request);

        // Short circuit: if permissions are already granted don't start PermissionsActivity but
        // invoke callback immediately. Since we're using PermissionsUtils this also covers cases
        // when Build.VERSION.SDK_INT < 23
        int[] result = PermissionsUtils.checkPermissions(context, request.permissions);
        if (PermissionsUtils.isResultAllGranted(result)) {
            notifyAndRemoveRequest(request.requestId, result);
        } else {
            PermissionsActivity.requestPermissions(context, request);
        }
    }

    void notifyAndRemoveRequest(int requestId, final int[] grantResults) {
        final PermissionsRequest request = pool.get(requestId);
        if (request != null) {
            request.result = grantResults;
            final PermissionsRequest.Callback callback = request.getCallback();
            if (callback != null) {
                if (PermissionsUtils.isResultAllGranted(grantResults)) {
                    UiThreadExecutor.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onPermissionGranted();
                        }
                    });
                } else {
                    UiThreadExecutor.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onPermissionDenied(grantResults);
                        }
                    });
                }
            }
        }
        pool.remove(requestId);
    }

    /**
     * The pool can fill with stale entries if for some reason PermissionsActivity is killed
     * before calling PermissionsActivity.notifyListenerAndDie() . This can happen if
     * neither "Allow" nor "Deny" is clicked but the PermissionsActivity is swiped out of the
     * recent tasks list.
     * This method takes care of purging any stale entries.
     */
    private void purgePool() {
        Iterator<PermissionsRequest> iterator = pool.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCallback() == null) {
                iterator.remove();
            }
        }
    }

}
