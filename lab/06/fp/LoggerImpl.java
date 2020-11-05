import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class LoggerImpl<T> implements Logger<T> {

    private final T item;
    private final List<String> backlog;
    private static final String UNCHANGED = "Value unchanged. Value = ";
    private static final String CHANGED = "Value changed! New value = "; 

    LoggerImpl(T item) {
        this.item = item;
        this.backlog = new ArrayList<String>();
        String message = String.format("Value initialized. Value = %s", this.item);
        this.backlog.add(message);
    }

    LoggerImpl(T item, List<String> list) {
        this.item = item;
        this.backlog = list;
    }
    
    @Override
    public String toString() {
        return String.format("Logger[%s]", this.item);
    }

    public void printlog() {
        for (String msg: this.backlog) {
            System.out.println(msg);
        }
    }

    public <R> Logger<R> flatMap(Function<? super T, ? extends Logger<? extends R>> func) {
        List<String> tempList = new ArrayList<String>(this.backlog);
        Logger<? extends R> log = func.apply(this.item);

        //new Log's starting point is value initialized but that
        //value is the last product from this class
        List<String> extendedBacklog = log.getBacklog();
        extendedBacklog.remove(0); //get rid of the initialized line
        tempList.addAll(extendedBacklog);

        LoggerImpl<R> log3 = new LoggerImpl<R>(log.getItem(), tempList);
        return (Logger<R>) log3;
    }
   

    public <R> Logger<R> map(Function<? super T, ? extends R> func) {
        String latestMsg;
        if (this.item.equals(func.apply(this.item))) {
            latestMsg = this.UNCHANGED + String.valueOf(this.item);
        } else {
            latestMsg = this.CHANGED + String.valueOf(func.apply(this.item));
        }
        List<String> tempList = new ArrayList<String>(this.backlog);
        tempList.add(latestMsg);
        LoggerImpl<R> log = new LoggerImpl<R>(func.apply(this.getItem()), tempList);        
        return (Logger<R>) log;
    }
    
    public boolean equals(Object o) {
        if (o == this) {return true;}

        if (o instanceof LoggerImpl) {
            LoggerImpl<?> l = (LoggerImpl<?>) o;

            int i = this.backlog.size();
            if (l.getBacklog().size() != i) {
                return false;
            }
            for (int j = 0; j < i; j++) {
                String thisString = this.backlog.get(j);
                String otherString = l.getBacklog().get(j);
                if (thisString.equals(otherString)) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean test(Predicate<T> p) {
        return p.test(this.item);
    }

    public T getItem() {
        return this.item;
    }

    public List<String> getBacklog() {
        return this.backlog;
    }
}
