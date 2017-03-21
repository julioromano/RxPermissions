package net.kjulio.rxpermissions.test;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

public class TestUtils {

    private static final int DENY_BUTTON_INDEX = 0;
    private static final int GRANT_BUTTON_INDEX = 1;

    public static void grantPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + InstrumentationRegistry.getTargetContext().getPackageName() + " " + permission
            );
        }
    }

    public static void denyPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm revoke " + InstrumentationRegistry.getTargetContext().getPackageName() + " " + permission
            );
        }
    }

    public static void resetPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm reset-permissions " + InstrumentationRegistry.getTargetContext().getPackageName()
            );
        }
    }

    public static  void clickPermissionsDialog(UiDevice uiDevice, boolean allow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            UiObject uiObject = uiDevice.findObject(new UiSelector().clickable(true).checkable(false).index(allow ? GRANT_BUTTON_INDEX : DENY_BUTTON_INDEX));
            if (uiObject.exists()) {
                try {
                    uiObject.click();
                } catch (UiObjectNotFoundException e) {
                    Log.e("TestUtils", String.format("There is no permissions dialog to interact with :%s", e));
                }
            }
        }
    }
}
