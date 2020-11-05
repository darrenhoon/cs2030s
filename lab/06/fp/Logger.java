import java.lang.IllegalArgumentException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;
import java.util.ArrayList;

interface Logger<T> {
    
    static <T> Logger<T> make(T t) {
        if (t == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        if (t instanceof Logger) {
            throw new IllegalArgumentException("already a Logger");
        }
        LoggerImpl<T> nextLogger = new LoggerImpl<T>(t);
        
        return (Logger<T>) nextLogger;
    }

    boolean test(Predicate<T> pred);

    void printlog();

    boolean equals(Object o);

    List<String> getBacklog();

    T getItem();

    <R> Logger<R> map(Function<? super T, ? extends R> func); 
    
    <R> Logger<R> flatMap(Function<? super T, ? extends Logger<? extends R>> func); 
}
