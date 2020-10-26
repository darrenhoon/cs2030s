import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.lang.Comparable;

class Lazy<T extends Comparable> {

    private Optional<Supplier<T>> supplier;
    private Optional<T> value;

    public static <T> Lazy<T> of(T t) {
        return new Lazy<T>(t);
    }

    public static <T> Lazy<T> of(Supplier<T> s) {
        return new Lazy<T>(s);
    }

    private Lazy(T t) {
        this.value = Optional.empty();
        this.supplier = Optional.of(() -> t);
    }

    private Lazy(Supplier<T> s) {
        this.value = Optional.empty();
        try {
            this.supplier = Optional.ofNullable(s);
            this.supplier.orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No value present");
        }
    }

    public T get() {
        return this.value.orElseGet( () -> {
            T value = this.supplier.orElseThrow().get();
            this.value = Optional.of(value);
            return value;
            
        } );
    }
    
    @Override
    public String toString() {
        try {
            return this.value.orElseThrow().toString();
        } catch (NoSuchElementException e) {
            return "?";
        }
    }

    public <R> Lazy<R> map(Function<? super T,? extends R> func) {
        return Lazy.of(() -> func.apply(this.get()));
    }

    public <R> Lazy<R> flatMap(Function<? super T, ? extends Lazy<R>> func){
        return Lazy.of(() -> func.apply(this.get()).get());
    }

    public <R,U> Lazy<R> combine(Lazy<U> lazy, BiFunction<T,U,R> func) {
        return Lazy.of(() -> func.apply(this.get(), lazy.get()));
    }

    public Lazy<Boolean> test(Predicate<T> pred) {
        return Lazy.of(pred.test(this.get()));
    }

    public boolean equals(Object o) {
        if ((o instanceof Lazy) == false) {
            return false;
        }
        Lazy<?> lazy = (Lazy<?>) o;
        boolean result = (this.get() == lazy.get());
        return result;
    }
}
