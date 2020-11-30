class Foo {
    static int y = 1;
    Runnable bar() {
        int x = 1;
        Runnable r1 = () -> System.out.println(x);
        x = x + 1;
        return r1;
    }
    Runnable baz() {
        Runnable r2 = () -> System.out.println(y);
        y = y + 1;
        return r2;
    }
}
