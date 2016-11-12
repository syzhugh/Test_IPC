package sun.app_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.CountDownLatch;

import sun.test_ipc.binderpool.BinderPool;
import sun.test_ipc.binderpool.GetString;
import sun.test_ipc.testaidl.TestAidl;


public class Client_MainAct extends AppCompatActivity {

    private Button bt;

    private class MClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            testaidlEvent();
            testBinderPoolEvent();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__main);
        bt = (Button) findViewById(R.id.main_bt);
        bt.setOnClickListener(new MClickListener());

        Log.i("info", "Client_MainAct:onCreate----------------------");
        Log.i("info", "pid:" + Process.myPid());
        Log.i("info", "uid:" + Process.myUid());

//        bindServer();

        bindBinderPoolServer();

    }

    /*test binderpool*/
    private BinderPool binderPool;
    private MPoolServiceConn poolConn;

    private class MPoolServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("info", "MPoolServiceConn:onServiceConnected----------------------");
            Log.i("info", ":" + name);
            binderPool = BinderPool.Stub.asInterface(service);
            try {
                binderPool.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("info", "MPoolServiceConn:onServiceDisconnected----------------------");
            binderPool = null;
        }
    }

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (binderPool == null)
                return;
            Log.i("info", "Client_MainAct:binderDied----------------------");
            binderPool.asBinder().unlinkToDeath(this, 0);
            binderPool = null;
            bindBinderPoolServer();
        }
    };

    private void testBinderPoolEvent() {
        if (binderPool == null)
            return;
        Log.i("info", "客户端请求发送。。。");
        try {
            TestAidl testAidl = TestAidl.Stub.asInterface(binderPool.queryBinder(0));
            int add = testAidl.add(1, -7);
            Log.i("info", "服务端返回结果:" + add);

            GetString getString = GetString.Stub.asInterface(binderPool.queryBinder(1));
            String result = getString.delRepetition("iii  a mma sssyyzzz");
            Log.i("info", "服务端返回结果:" + result);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindBinderPoolServer() {
        Intent intent = new Intent();
        poolConn = new MPoolServiceConn();
        intent.setComponent(new ComponentName("sun.test_ipc", "sun.test_ipc.binderpool.ServiceForBinderPool"));
        intent.setAction("testpool");
        bindService(intent, poolConn, BIND_AUTO_CREATE);

//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("sun.binder_server", "sun.binder_server.MyService"));
//        startService(intent);

    }


    /*testaidl*/
    private TestAidl testAidl;
    private MServiceConn conn;

    private class MServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            testAidl = TestAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            testAidl = null;

        }
    }

    private void bindServer() {
        conn = new MServiceConn();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("sun.test_ipc", "sun.test_ipc.testaidl.ServiceForTestAidl"));
        intent.setAction("testaidl");
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    private void testaidlEvent() {
        if (testAidl == null)
            return;
        Log.i("info", "客户端请求发送。。。");
        try {
            int result = testAidl.add(2, 4);
            Log.i("info", "服务端返回结果:" + result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(conn);
    }
}
