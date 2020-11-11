class A {
    private final int x;
    A() {
        this(0);
    }

    A(int x) {
        this.x = x;
    }

    void sleep() {
        System.out.println(Thread.currentThread().getName() + " " + x);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }

    A incr() {
        sleep();
        return new A(this.x + 1);
    }

    A decr() {
        sleep();
        if (x < 0) {
            throw new IllegalStateException();
        }
        return new A(this.x - 1);
    }

    public String toString() {
        return "" + x;
    }

    CompletableFuture<A> foo() {
        var me = CompletableFuture.supplyAsync(() -> a.incr());
        return me.thenApply(aObject -> aObject.decr());
        //return CompletableFuture.supplyAsync( () -> a.incr().decr());
    }

    CompletableFuture<A> bar(A a) {
        return CompletableFuture.supplyAsync( () -> a.incr());
    }

var b = foo(new A()).thenCompose(  aObject -> bar(aObject));
b.join();

    CompletableFuture<A> baz(A a, int x) {
        if (x == 0) {
            return CompletableFuture.completedFuture(new A(0));
        }
        return CompletableFture.supplyAync( () -> a.incr().decr());
    }
}

