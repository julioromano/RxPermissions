package net.kjulio.rxpermissions.internal;

import android.os.Handler;
import android.os.Looper;

class UiThreadExecutor {

    private static final Handler handler = new Handler(Looper.getMainLooper());

    static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

}
