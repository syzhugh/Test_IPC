// BinderPool.aidl
package sun.test_ipc.binderpool;

// Declare any non-default types here with import statements

interface BinderPool {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            IBinder queryBinder(int id);
}
