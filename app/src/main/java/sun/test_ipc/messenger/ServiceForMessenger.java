package sun.test_ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

import sun.test_ipc.MyConstants;

public class ServiceForMessenger extends Service {

    private MHandler mHandler;
    private Messenger messenger;

    private class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;

            Log.i("info", "handleMessage:" + what);
            if (what == MyConstants.SUN) {

                Bundle data = msg.getData();
                Log.i("info", ":" + data.getString("test"));
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("info", "MService:onCreate----------------------");
        Log.i("info", ":" + Process.myPid());


        mHandler = new MHandler();
        messenger = new Messenger(mHandler);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("info", "MService:onStartCommand----------------------");


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("info", "MService:onBind----------------------");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("info", "MService:onUnbind----------------------");
        return super.onUnbind(intent);
    }


}
