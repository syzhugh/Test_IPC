package sun.test_ipc;

import android.app.Application;
import android.os.Process;
import android.util.Log;

/**
 * Created by Yaozong on 2016/11/11.
 */

public class MApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("info", "MApp:onCreate----------------------");
        Log.i("info", ":" + Process.myPid());



    }
}
