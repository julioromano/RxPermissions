package net.kjulio.rxpermissions;

public class PermissionsDeniedException extends RuntimeException {

    private final PermissionsResult permissionsResult;

    public PermissionsDeniedException(PermissionsResult permissionsResult) {
        this.permissionsResult = permissionsResult;
    }

    public PermissionsDeniedException(String message, PermissionsResult permissionsResult) {
        super(message);
        this.permissionsResult = permissionsResult;
    }

    public PermissionsDeniedException(String message, Throwable cause,
                                      PermissionsResult permissionsResult) {
        super(message, cause);
        this.permissionsResult = permissionsResult;
    }

    public PermissionsDeniedException(Throwable cause, PermissionsResult permissionsResult) {
        super(cause);
        this.permissionsResult = permissionsResult;
    }

    public PermissionsDeniedException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace,
                                      PermissionsResult permissionsResult) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.permissionsResult = permissionsResult;
    }

    public PermissionsResult getPermissionsResult() {
        return permissionsResult;
    }
}
