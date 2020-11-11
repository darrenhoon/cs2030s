class Pair<T, U> {

    T first;
    U second;

    Pair(T first,U second) {
        this.first = first;
        this.second = second;
    }
    
    T first() {
        return this.first;
    }

    U second() {
        return this.second;
    }

    static Pair<T,U> of(T first, U second) {
        return new Pair(first, second);
    }

     
}
