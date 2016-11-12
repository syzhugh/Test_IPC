package sun.test_ipc.testaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ServiceForTestAidl extends Service {
    public ServiceForTestAidl() {
    }

    private IBinder mBinder;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("info", "ServiceForTestAidl:onCreate----------------------");

        mBinder = new TestAidl.Stub() {

            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public int add(int num1, int num2) throws RemoteException {
                Log.i("info", "处理请求。。。");
                return num1 + num2;
            }
        };

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("info", "ServiceForTestAidl:onBind----------------------");
        return mBinder;
    }


}
