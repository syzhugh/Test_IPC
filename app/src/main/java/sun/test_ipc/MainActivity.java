package sun.test_ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import sun.test_ipc.binderpool.ServiceForBinderPool;
import sun.test_ipc.messenger.ServiceForMessenger;
import sun.test_ipc.primary.TestParcelable;
import sun.test_ipc.process.ServiceForProcess;
import sun.test_ipc.testaidl.ServiceForTestAidl;


public class MainActivity extends AppCompatActivity {

    /* 2016/11/11 11:35
    * 下午任务：
    * 1.parcelable  √
    * 2.binder
    *
    * 晚上任务：
    * 1.messenger √
    * 2.aidl*
    * */

    /* 2016/11/12 10:41
    *
    * 1.binder √
    * 2.aidl   这个暂时不看了
    * 3.希尔排序
    *   归并排序
    *   二叉树递归遍历
    *   非递归遍历
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("info", "MainActivity:onCreate----------------------");
        Log.i("info", "pid:" + Process.myPid());
        Log.i("info", "uid:" + Process.myUid());
//        testProcessAndParcelable();
//        testMessenger();
//        testaidl();
        testBinderPool();

    }

    private void testBinderPool() {
        Intent intent = new Intent(getApplicationContext(), ServiceForBinderPool.class);
        intent.setAction("testpool");
        startService(intent);
    }

    private void testaidl() {
        Intent intent = new Intent(getApplicationContext(), ServiceForTestAidl.class);
        intent.setAction("testaidl");
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConn);
    }


    /*process and parcelable*/
    private void testProcessAndParcelable() {
        Log.i("info", "MainActivity:testProcessAndParcelable----------------------");
        Log.i("info", ":" + Process.myPid());
        Intent intent = new Intent(getApplicationContext(), ServiceForProcess.class);
        intent.setAction("testprocess");
        intent.putExtra("test", new TestParcelable(1, "23", 4));
        startService(intent);
    }

    /*messenger*/
    private Messenger messenger;
    private MServiceConn mServiceConn;

    private class MServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("info", "MServiceConn:onServiceConnected----------------------");
            Log.i("info", ":" + name);
            messenger = new Messenger(service);

            Message msg = Message.obtain(null, MyConstants.SUN);
            Bundle bundle = new Bundle();
            bundle.putString("test", "hello");
            msg.setData(bundle);
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("info", "MServiceConn:onServiceDisconnected----------------------");
            Log.i("info", "onServiceDisconnected:" + name);
        }
    }

    private void testMessenger() {
        mServiceConn = new MServiceConn();
        Intent intent = new Intent(getApplicationContext(), ServiceForMessenger.class);
        intent.setAction("testmessenger");
        //会有一定延迟，不能再此后直接开启线程，加标志判断
        bindService(intent, mServiceConn, BIND_AUTO_CREATE);
        new Thread() {
            @Override
            public void run() {
                super.run();

                int i = 0;
                while (true) {
                    Log.i("info", ":" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (messenger == null)
                        continue;
                    if (i == 5) {
                        break;
                    }
                    Message msg = Message.obtain(null, MyConstants.SUN);
                    Bundle bundle = new Bundle();
                    bundle.putString("test", "hello adfasdfasdfasdf");
                    msg.setData(bundle);
                    try {
                        messenger.send(msg);
                        i++;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                unbindService(mServiceConn);
            }
        }.start();
    }


}
