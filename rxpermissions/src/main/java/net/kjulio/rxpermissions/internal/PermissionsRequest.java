package net.kjulio.rxpermissions.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

class PermissionsRequest {

    final String[] permissions;
    private final WeakReference<Callback> callbackWeakReference;
    int requestId;
    int[] result;

    PermissionsRequest(@NonNull String[] permissions, @NonNull Callback callback) {
        this.permissions = permissions;
        callbackWeakReference = new WeakReference<>(callback);
    }

    void execute(Context context) {
        RequestProcessor.getInstance().processRequest(context, this);
    }

    @Nullable
    Callback getCallback() {
        return callbackWeakReference.get();
    }

    interface Callback {

        void onPermissionGranted();

        void onPermissionDenied(int[] grantResults);

    }

}
