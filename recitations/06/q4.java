import java.util.function.BiFunction;

<X,Y,Z> Function<Y, Function<X,Z>> curry(BiFunction<X,Y,Z> f) {
    return y -> (x -> f.apply(x,y));
}
