package net.kjulio.rxpermissions.internal;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A singleton object to enable communication between PermissionsActivity and BaseHelper.
 *
 * The main use case is for PermissionsActivity to return control
 * to the calling BaseHelper after its job is done.
 *
 * Listeners are kept into WeakReferences that are deleted as soon as notifyListeners() is called.
 * There is no need for a removeListener() method therefore.
 */
class PermissionsRequestLock {

    private final static PermissionsRequestLock instance = new PermissionsRequestLock();

    private final ConcurrentHashMap<WeakReference<PermissionsListener>, Integer> listeners =
            new ConcurrentHashMap<>();

    private PermissionsRequestLock() {}

    static PermissionsRequestLock getInstance() {
        return instance;
    }

    void addListener(PermissionsListener permissionsListener) {
        listeners.put(new WeakReference<>(permissionsListener), permissionsListener.hashCode());
    }

    void notifyListeners() {
        for (WeakReference<PermissionsListener> weakReference : listeners.keySet()) {
            PermissionsListener permissionsListener = weakReference.get();
            if (permissionsListener != null) {
                permissionsListener.onPermissionsDialogDismissed();
            }
            // Remove the listener as soon as its callback is invoked once.
            listeners.remove(weakReference);
        }
    }
}
