package sun.test_ipc.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

public class ServiceForBinderPool extends Service {
    public ServiceForBinderPool() {
    }

    private Binder mBinder = new Aidl_BinderPoolManager.Aidl_BinderPool();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("info", "ServiceForBinderPool:onCreate----------------------");
        Log.i("info", "pid:" + Process.myPid());
        Log.i("info", "uid:" + Process.myUid());
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("info", "ServiceForBinderPool:onBind----------------------");
        return mBinder;
    }
}
