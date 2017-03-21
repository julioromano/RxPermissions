package net.kjulio.rxpermissions.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

class PermissionsUtils {

    static int[] checkPermissions(@NonNull Context context, @NonNull String[] permissions) {
        int[] results = new int[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            results[i] = ActivityCompat.checkSelfPermission(context, permissions[i]);
        }
        return results;
    }

    static boolean isResultAllGranted(@NonNull int[] grantResults) {
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

}
