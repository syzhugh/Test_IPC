package sun.test_ipc.binderpool;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by Yaozong on 2016/11/12.
 */

public class Aidl_BinderPoolManager {
    public static final int BINDER_GETSUM = 0;
    public static final int BINDER_GETSTRING = 1;

    public static class Aidl_BinderPool extends BinderPool.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public IBinder queryBinder(int id) throws RemoteException {
            IBinder binder = null;
            switch (id) {
                case BINDER_GETSUM:
                    binder = new Aidl_GetSum();
                    break;
                case BINDER_GETSTRING:
                    binder = new Aidl_GetString();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}
