package net.kjulio.rxpermissionstest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RxPermissionsAndroidTest {

    private Context context; // Context of the app under test.
//    private TestObserver<Location> testObserver;
//    private RxPermissions rxPermissions;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
//        testObserver = TestObserver.create();
//        rxPermissions = new RxPermissions.Builder(context).build();
    }

    @Test
    public void appPackageName() {
        assertEquals("net.kjulio.rxpermissions.test", context.getPackageName());
    }

//    @Test
//    public void testLastLocation() {
//        rxPermissions.lastLocation()
//                .subscribe(testObserver);
//        testObserver.awaitTerminalEvent();
//        testObserver.assertValueCount(1);
//        testObserver.assertComplete();
//    }
}
