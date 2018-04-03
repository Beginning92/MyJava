import java.util.ArrayList;
import java.util.List;

/**
 * Created by beginning on 2016/12/29.
 */
public class Hello {
    public static void main(String args[]) {
//        System.out.println("Hello GitHub!");
//        System.out.println("Hello GitHub!");
//        System.out.println(1 << 10);
//        test();
//        String a = "111";
//        System.out.println(a.concat(null));
//        A ab = new B();
//        ab = new B();

        List<Integer> data = new ArrayList<Integer>();
        data.add(100);
        data.add(11);
        data.add(67);
        data.add(69);
        data.add(43);
        data.add(data.get(1));
        data.remove(data.get(1));
        for (Integer element : data){
            System.out.println(element);
        }

    }

    private static int test() {
        int a = 0;
        try {
            a = 1;
            System.out.println(a);
        } catch (Exception e) {

        } finally {
            a = 2;
            System.out.println(a);
        }
        return a;
    }

}

class A {
static {
        System.out.print("1");
    }

    public A() {
        System.out.print("2");
    }

    {
        System.out.print("3");
    }
}

class B extends A {
    static {
        System.out.print("a");
    }
    public B() {
        System.out.print("b");
    }

    {
        System.out.print("c");
    }
}
