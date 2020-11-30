//**** OO Design (d)
//**** Write your answer below

public class Main {
    public static void main(String[] args) {

        A a1 = new A();
        A a2 = new A();
        B b1 = new B();
        B b2 = new B();

        B b2i = b2.set(a1);
        B b1i = b1.set(b2i);
        A a2i = a2.set(b1i);
        A a = a.set(a2i);
    }
}
