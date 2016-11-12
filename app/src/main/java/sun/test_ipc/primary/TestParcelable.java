package sun.test_ipc.primary;

import android.os.Parcel;
import android.os.Parcelable;


public class TestParcelable extends Base implements Parcelable {

    public TestParcelable(int age, String name, int class1) {
        super(age, name, class1);
    }

    protected TestParcelable(Parcel in) {
        setAge(in.readInt());
        setName(in.readString());
        setClass1(in.readInt());
    }

    public static final Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
        @Override
        public TestParcelable createFromParcel(Parcel in) {
            return new TestParcelable(in);
        }

        @Override
        public TestParcelable[] newArray(int size) {
            return new TestParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getClass1());
        dest.writeString(getName());
        dest.writeInt(getAge());
    }
}
