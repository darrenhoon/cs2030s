import java.util.function.BinaryOperator;
import java.util.function.BiFunction;

<T> T average(List<T> list, BinaryOperator<T> add, BiFunction<T,Double,T> scale) {
    T sum = list.get(0); // assume list is non-empty
    int size = list.size();
    for (T item : list.subList(1, size))
        sum = add.apply(sum, item);
    return scale.apply(sum, new Double(1.0/size));
}

