import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.Function;

class Lazy<T> {

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
}
