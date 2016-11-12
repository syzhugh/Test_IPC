package sun.test_ipc.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.util.Log;

public class ServiceForProcess extends Service {
    public ServiceForProcess() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("info", "MService:onCreate----------------------");
        Log.i("info", ":" + Process.myPid());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("info", "ServiceForProcess:onStartCommand----------------------");
        Parcelable test = intent.getParcelableExtra("test");
        Log.i("info", ":" + test.toString());

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
