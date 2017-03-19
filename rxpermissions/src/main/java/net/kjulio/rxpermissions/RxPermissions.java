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
 * TODO: Add quick usage guide.
 */
public abstract class RxPermissions {

    public static RxPermissions with(Context context) {
        return new RxPermissionsImpl(context);
    }

    /**
     * TODO
     * @param permissions
     * @return
     */
    public abstract Completable requestPermissions(String... permissions);
}
