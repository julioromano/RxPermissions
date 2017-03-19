package net.kjulio.rxpermissions;

import android.content.pm.PackageManager;

public class PermissionsResult {

    private final String[] request;
    private final int[] result;

    public PermissionsResult(String[] request, int[] result) {
        this.request = request;
        this.result = result;
    }

    public String[] getRequest() {
        return request;
    }

    public int[] getResult() {
        return result;
    }

    public boolean allGranted() {
        if (request == null) {
            return true;
        }
        for (int i=0; i<request.length; i++) {
            if (result[i] == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
