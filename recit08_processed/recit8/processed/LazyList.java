import java.util.function.Supplier;
import java.util.function.Consumer;
import java.lang.IllegalStateException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.BinaryOperator;
import java.util.Scanner;
public class LazyList<T> {
    private final T head;
    private final Supplier<LazyList<T> > tail;
    private final boolean amIEmpty;
    public LazyList(T a, Supplier<LazyList<T> > b) {
        this.head = a;
        this.tail = b;
        this.amIEmpty = false;
    }
    private LazyList() {
        this.head = null;
        this.tail = null;
        this.amIEmpty = true;
    }
    public static <T> T thaw(Supplier<T> ice) {
        return ice.get();
    }
    public static <T> LazyList<T> makeEmpty() {
        return new LazyList<T>();
    }
    public T head() {
        if (this.isEmpty())
            throw new IllegalArgumentException("head() called on empty list!");
        return this.head;
    }
    public LazyList<T> tail() {
        if (this.isEmpty())
            throw new IllegalArgumentException("tail() called on empty list!");
        return thaw(this.tail);
    }
    public boolean isEmpty() {
        return this.amIEmpty;
    }
    @Override
    public String toString() {
        Scanner sc = new Scanner(this.tail.toString()).useDelimiter("Lambda");
        String s1 = sc.next();
        String s2 = sc.next();
        return String.format("head: %s\ntail: thunk%s",
                             this.head.toString(),s2);
    }
    public void print() {
        LazyList<T> me = this;
        System.out.printf("(* ");
        while (!me.isEmpty()) {
            System.out.printf("%s, ", me.head());
            me = me.tail();
        }
        System.out.println("*)");
    }
    @SafeVarargs
    public static <T> LazyList<T> fromList(T ... items) {
        List<T> list = Arrays.asList(items);
        return helper(list);
    }
    private static <T> LazyList<T> helper(List<T> list) {
        if (list.isEmpty())
            return LazyList.makeEmpty();
        else
            return new LazyList<>((list.get(0)), ( ()->(helper(list.subList(1,list.size()))) ));
    }
    public <R> LazyList<R> map(Function<T,R> f) {
        if (this.isEmpty())
            return LazyList.makeEmpty();
        else
            return new LazyList<>((f.apply(this.head())), ( ()->(this.tail().map(f)) ));
    }
    public <R> LazyList<R> flatmap(Function<T, LazyList<R>> f) {
        if (this.isEmpty()) {
            return LazyList.makeEmpty();
        } else {
            return f.apply(this.head()).concat(this.tail().flatmap(f));
        }
    }
    public LazyList<T> filter(Predicate<T> pred) {
        if (this.isEmpty())
            return LazyList.makeEmpty();
        else if (pred.test(this.head()))
            return new LazyList<>(this.head(),
                                  ( ()->(this.tail().filter(pred)) ));
        else
            return this.tail().filter(pred);
    }
    public LazyList<T> limit(long maxSize) {
        if (maxSize == 0)
            return LazyList.makeEmpty();
        else
            return new LazyList<>((this.head()), ( ()->(this.tail().limit(maxSize - 1)) ));
    }
    public T get(int idx) {
        if (this.isEmpty() || idx < 0)
            return null;
        else if (idx == 0)
            return this.head();
        else
            return this.tail().get(idx - 1);
    }
    public static LazyList<Integer> intRange(int a, int b) {
        if (a >= b)
            return LazyList.makeEmpty();
        else
            return new LazyList<>((a), ( ()->(intRange(a + 1, b)) ));
    }
    public LazyList<T> concat(LazyList<T> other) {
        if (this.isEmpty()) {
            return other;
        } else {
            return new LazyList<>((this.head()), ( ()->(this.tail().concat(other)) ));
        }
    }
    public LazyList<T> reverse() {
        LazyList<T> temp = this;
        List<T> tempList = new ArrayList<T>();
        LazyList<T> head;
        while (temp.isEmpty() == false) {
            T value = temp.head();
            tempList.add(value);
            head = temp.tail();
        }
        List<T> finalList = new ArrayList<T>();
        for (int i = tempList.size() - 1; i>=0; i--) {
            finalList.add(tempList.get(i));
        }
        return helper(finalList);
    }
    public <U,R> LazyList<R> combine(LazyList<U> other, BiFunction<T,U,R> combiner) {
        return this.flatmap(x ->
                            other.map(y -> combiner.apply(x,y)));
    }
    public void forEach(Consumer<T> eat) {
        if (this.isEmpty())
            return;
        else {
            eat.accept(this.head());
            this.tail().forEach(eat);
        }
    }
}
