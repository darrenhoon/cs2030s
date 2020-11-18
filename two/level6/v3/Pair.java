//package cs2030.simulator;

public class Pair<T, U> {
    private final T firstValue;
    private final U secondValue;

    private Pair(T firstValue, U secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public static <T, U> Pair<T, U> of(T firstValue, U secondValue) {
        return new Pair<T, U>(firstValue, secondValue);
    }

    public T first() {
        return this.firstValue;
    }

    public U second() {
        return this.secondValue;
    }
}
