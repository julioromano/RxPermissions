package net.kjulio.rxpermissions.internal;

import android.content.Context;

import net.kjulio.rxpermissions.PermissionsDeniedException;
import net.kjulio.rxpermissions.PermissionsResult;
import net.kjulio.rxpermissions.RxPermissions;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

public class RxPermissionsImpl extends RxPermissions {

    private final Context context;

    public RxPermissionsImpl(Context context) {
        this.context = context;
    }

    public Completable requestPermissions(final String... permissions) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter e) throws Exception {
                if (PermissionsActivity.checkPermissions(context, permissions).allGranted()) {
                    e.onComplete();
                } else {
                    PermissionsActivity.requestPermissions(context, permissions, new PermissionsListener() {
                        @Override
                        public void onPermissionsDialogDismissed() {
                            PermissionsResult result =
                                    PermissionsActivity.checkPermissions(context, permissions);
                            if (result.allGranted()) {
                                e.onComplete();
                            } else {
                                e.onError(new PermissionsDeniedException(result));
                            }
                        }
                    });
                }
            }
        });
    }
}
