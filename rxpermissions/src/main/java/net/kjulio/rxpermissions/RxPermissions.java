package net.kjulio.rxpermissions;

import android.content.Context;

import net.kjulio.rxpermissions.internal.RxPermissionsImpl;

import io.reactivex.Completable;

/**
 * RxPermissions.
 *
 * An easy to use Android library to get Android M's runtime permissions in an RxJava way.
 * https://github.com/julioromano/RxPermissions
 *
 * Example usage:
 *
 * RxPermissions.with(context)
 *      .requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
 *      .subscribe(...);
 *
 * The instance returned by RxPermissions.with(context) can be safely cached and reused
 * (e.g. when using a depedency injection framework).
 */
public abstract class RxPermissions {

    public static RxPermissions with(Context context) {
        return new RxPermissionsImpl(context);
    }

    /**
     * Request runtime permissions.
     *
     * @param permissions the permission strings to request takend from Manifest.permission
     * @return an io.reactivex.Completable that completes if all permissions are granted, errors
     *         with a PermissionDeniedException if at least one of the requested permissions has
     *         been denied. More information can be gathered by calling
     *         PermissionDeniedException.getGrantResults()
     */
    public abstract Completable requestPermissions(String... permissions);
}
