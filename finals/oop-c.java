//**** OO Design (c)
//**** Write your answer below

abstract class Alphabet {
    abstract Alphabet set(Alphabet);
    abstract Alphabet get();
}

class A extends Alphabet {
    private final Alphabet other;
    
    public A() {
        this.other = null;
    }

    private A(Alphabet other) {
        this.other = other;
    }

    A set(Alphabet other) {
        return new A(other);
    }

    Alphabet get() {
        return this.other;
    }
}

class B extends Alphabet { 
    
    private final Alphabet other;
 
    
    public B() {
        this.other = null;
    }

    private B(Alphabet other) {
        this.other = other;
    }

    B set(Alphabet other) {
        return new B(other);
    }

    Alphabet get() {
        return this.other;
    }
}
