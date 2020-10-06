import java.util.function.Function;

<T,U,R> Function<T,R> compose(Function<T,U> f, Function<U,R> g) {
    Function<T, R> result1 = (x) -> g(f(x));
    return result1;
    }
