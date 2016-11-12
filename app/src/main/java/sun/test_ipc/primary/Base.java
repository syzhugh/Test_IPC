package sun.test_ipc.primary;

/**
 * Created by Yaozong on 2016/11/11.
 */

public class Base {

    private int age;
    private String name;
    private int class1;

    public Base() {

    }

    public Base(int age, String name, int class1) {
        this.age = age;
        this.name = name;
        this.class1 = class1;
    }

    @Override
    public String toString() {
        return age + " " + name + " " + class1;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClass1() {
        return class1;
    }

    public void setClass1(int class1) {
        this.class1 = class1;
    }
}
