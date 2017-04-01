package net.kjulio.rxpermissions.test;

import android.Manifest;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import net.kjulio.rxpermissions.PermissionDeniedException;
import net.kjulio.rxpermissions.RxPermissions;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import io.reactivex.observers.TestObserver;

import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class RxPermissionsTest {

    private UiDevice uiDevice;
    private Context context;
    private TestObserver<Object> testObserver;

    @Before
    public void setUp() {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getTargetContext();
        testObserver = TestObserver.create();
    }

    @AfterClass
    public static void afterClass() {
        TestUtils.resetPermissions();
    }

    @Test
    public void aTestPermissionsDeny() {
        RxPermissions.with(context).requestPermissions(Manifest.permission.CAMERA).subscribe(testObserver);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestUtils.clickPermissionsDialog(uiDevice, false);
        testObserver.awaitTerminalEvent();
        testObserver.assertError(PermissionDeniedException.class);
    }

    @Test
    public void bTestPermissionsAllow() {
        RxPermissions.with(context).requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(testObserver);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestUtils.clickPermissionsDialog(uiDevice, true);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
    }

    @Test
    public void cTestAlreadyGrantedPermission() {
        RxPermissions.with(context).requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
    }
}
