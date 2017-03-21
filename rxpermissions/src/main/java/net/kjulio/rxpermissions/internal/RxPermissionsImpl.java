package net.kjulio.rxpermissions.internal;

import android.content.Context;

import net.kjulio.rxpermissions.PermissionDeniedException;
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
                PermissionsRequest request = new PermissionsRequest(permissions, new PermissionsRequest.Callback() {
                    @Override
                    public void onPermissionGranted() {
                        e.onComplete();
                    }

                    @Override
                    public void onPermissionDenied(int[] grantResults) {
                        e.onError(new PermissionDeniedException(grantResults));
                    }
                });
                request.execute(context);
            }
        });
    }

}
