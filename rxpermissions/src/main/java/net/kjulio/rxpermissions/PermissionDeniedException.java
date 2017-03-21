package net.kjulio.rxpermissions;

/**
 * Thrown when any of the requested permissions has not been granted.
 * Use getGrantResults() to check which permissions have been granted and which not.
 */
public class PermissionDeniedException extends RuntimeException {

    private final int[] grantResults;

    public PermissionDeniedException(int[] grantResults) {
        this.grantResults = grantResults;
    }

    public int[] getGrantResults() {
        return grantResults;
    }
}
