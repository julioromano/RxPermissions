package net.kjulio.rxpermissions.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import net.kjulio.rxpermissions.PermissionsResult;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hidden (no visible UI) activity to handle Android M's permissions request dialog.
 */
public class PermissionsActivity extends AppCompatActivity {

    private static final String EXTRA_KEY_PERMISSIONS = "permissions";
    private static final int RXPERMISSIONS_REQ_CODE = 52049;
    private static final AtomicBoolean permissionsRequestInProgress = new AtomicBoolean();

    static PermissionsResult checkPermissions(Context context, String[] permissions) {
        if (permissions == null) {
            return new PermissionsResult(null, null);
        }
        int[] results = new int[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            results[i] = ActivityCompat.checkSelfPermission(context, permissions[i]);
        }
        return new PermissionsResult(permissions, results);
    }

    /**
     * ALWAYS START THIS ACTIVITY USING THIS HELPER METHOD.
     */
    static void requestPermissions(Context context, String[] permissions,
                                   PermissionsListener permissionsListener) {

        // Register the calling PermissionsListener with the global lock to notify
        // it when PermissionsRequestActivity has finished.
        PermissionsRequestLock.getInstance().addListener(permissionsListener);

        if (!permissionsRequestInProgress.get()) {
            Intent intent = new Intent(context, PermissionsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(EXTRA_KEY_PERMISSIONS, permissions);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Activity recreation due to configChanges has been disabled in the manifest, no need
        // to handle it here.

        String[] permissions = null;
        Intent intent = getIntent();
        if (intent != null) {
            permissions = intent.getStringArrayExtra(EXTRA_KEY_PERMISSIONS);
        }

        // Handles the case in which 2 concurrent invocation of requestPermissions() launched
        // this activity.
        if (!permissionsRequestInProgress.getAndSet(true)) {
            if (checkPermissions(this, permissions).allGranted()) {
                notifyListenersAndDie();
            } else {
                ActivityCompat.requestPermissions(this, permissions, RXPERMISSIONS_REQ_CODE);
            }
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RXPERMISSIONS_REQ_CODE) {
            notifyListenersAndDie();
        }
    }

    private void notifyListenersAndDie() {
        PermissionsRequestLock.getInstance().notifyListeners();
        permissionsRequestInProgress.set(false);
        finish();
    }
}
