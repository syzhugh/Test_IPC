package sun.test_ipc.binderpool;

import android.os.RemoteException;

import java.util.TreeSet;

/**
 * Created by Yaozong on 2016/11/12.
 */

public class Aidl_GetString extends GetString.Stub {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public String delRepetition(String temp) throws RemoteException {

        TreeSet<Character> set = new TreeSet<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < temp.length(); i++) {
            char c = temp.charAt(i);
            if (!set.contains(c)) {
                stringBuilder.append(c);
                set.add(c);
            }
        }
        return stringBuilder.toString();
    }
}
