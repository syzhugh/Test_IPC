package sun.test_ipc.binderpool;

import android.os.RemoteException;

import sun.test_ipc.testaidl.TestAidl;

/**
 * Created by Yaozong on 2016/11/12.
 */

public class Aidl_GetSum extends TestAidl.Stub {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public int add(int num1, int num2) throws RemoteException {
        return num1 + num2;
    }
}
