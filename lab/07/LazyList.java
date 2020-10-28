import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Optional;

class LazyList<T extends Comparable<T>> {
    private List<Lazy<T>> list;
    private UnaryOperator<T> func;

    private LazyList(List<Lazy<T>> list, UnaryOperator<T> func) {
        this.list = list;
        this.func = func;
    }


    static <T extends Comparable<T>> LazyList<T> generate(int n, T seed, UnaryOperator<T> f) {
        List<Lazy<T>> list = new ArrayList<Lazy<T>>();
        list.add(Lazy.of(seed));
        return new LazyList<T>(list, f);
    }
    
    public T get(int i) {
        int listLastIndex = this.list.size() - 1;

        if (i > listLastIndex) {
            int iterCount = i - listLastIndex;
            Stream.iterate(this.func.apply(this.list.get(listLastIndex).get()), x -> this.func.apply(x))
                .limit(iterCount)
                .forEach(x -> this.list.add(Lazy.of(x)));
        }
        return this.list.get(i).get();
    }

    public int indexOf(T t) {
        List<T> tempList = this.list.stream().map(x -> x.get()).collect(Collectors.toList());
        if (tempList.indexOf(t) == -1) {
            this.get(tempList.size());
            return this.indexOf(t);
        } else {
            return tempList.indexOf(t);
        }
    }
}
