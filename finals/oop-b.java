//**** OO Design (b)
//**** Write your answer below

abstract class Alphabet {
    abstract void set(Alphabet);
    Alphabet abstract get();
}

class A extends Alphabet {
    private Alphabet other;
    void set(Alphabet other) {
        this.other = other;
    }
    Alphabet get() {
        return this.other;
    }
}

class B extends Alphabet {
    private Alphabet other;
    void set(Alphabet other) {
        this.other = other;
    }
    Alphabet get() {
        return this.other;
    }
}
