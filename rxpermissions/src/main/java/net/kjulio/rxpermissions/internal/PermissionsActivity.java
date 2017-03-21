package net.kjulio.rxpermissions.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Hidden (no visible UI) activity to handle Android M's permissions request dialog.
 */
public class PermissionsActivity extends AppCompatActivity {

    private static final String EXTRA_KEY_REQ_CODE = "requestCode";
    private static final String EXTRA_KEY_PERMISSIONS = "permissions";

    private int requestCode = 0;

    /**
     * ALWAYS START THIS ACTIVITY USING THIS HELPER METHOD.
     */
    static void requestPermissions(@NonNull Context context,
                                   @NonNull PermissionsRequest permissionsRequest) {
        Intent intent = new Intent(context, PermissionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_KEY_REQ_CODE, permissionsRequest.requestId);
        intent.putExtra(EXTRA_KEY_PERMISSIONS, permissionsRequest.permissions);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Activity recreation due to configChanges has been disabled in the manifest, no need
        // to handle it here.

        String[] permissions = null;
        Intent intent = getIntent();
        if (intent != null) {
            requestCode = intent.getIntExtra(EXTRA_KEY_REQ_CODE, 0);
            permissions = intent.getStringArrayExtra(EXTRA_KEY_PERMISSIONS);
        }

        if (permissions != null && requestCode != 0) {
            int[] result = PermissionsUtils.checkPermissions(this, permissions);
            if (PermissionsUtils.isResultAllGranted(result)) {
                notifyListenerAndDie(requestCode, result);
            } else {
                ActivityCompat.requestPermissions(this, permissions, requestCode);
            }
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Not calling super() as we don't care notifying fragments here.
        if (requestCode == this.requestCode) {
            notifyListenerAndDie(requestCode, grantResults);
        }
    }

    private void notifyListenerAndDie(int requestCode, int[] grantResults) {
        RequestProcessor.getInstance().notifyAndRemoveRequest(requestCode, grantResults);
        finish();
    }
}
