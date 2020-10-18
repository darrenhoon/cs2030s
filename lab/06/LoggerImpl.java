import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

class LoggerImpl<T> implements Logger<T> {

    private final T item;
    private final List<String> backlog;

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
    
    public boolean equals(Object o) {
        if (o == this) {return true;}

        if (o instanceof LoggerImpl) {
            LoggerImpl<?> l = (LoggerImpl<?>) o;
            int i = this.backlog.size();
            if (l.getBacklog().size() != i) {
                return false;
            }
            for (int j = 0; j < i; j++) {
                if (this.backlog.get(j) != l.getBacklog().get(j)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public <R> Logger<R> flatMap(Function<? super T, ? extends Logger<? extends R>> func) {

        String latestMsg;
        if (this.getItem() == func.apply(this.getItem()).getItem()) {
            latestMsg = String.format("Value unchanged. Value = ", this.getItem());
        } else {
            R item = func.apply(this.getItem()).getItem();
            latestMsg = String.format("Value changed! New value = " + item);
        }

        List<String> tempList = new ArrayList<String>();
        for (String s: this.backlog) {
            tempList.add(s);
        }
        tempList.add(latestMsg);

        Logger<? extends R> log = func.apply(this.getItem());
        LoggerImpl<R> log2 = new LoggerImpl<R>(log.getItem(), tempList);
        
        return log2;
    }
    
    public <R> Logger<R> map(Function<? super T, ? extends R> func) {
        String latestMsg;
        if (this.getItem() == func.apply(this.getItem())) {
            latestMsg = String.format("Value unchanged. Value = ", this.getItem());
        } else {
            R item = func.apply(this.getItem());
            latestMsg = String.format("Value changed! New value = " + item);
        }

        List<String> tempList = new ArrayList<String>();
        for (String s: this.backlog) {
            tempList.add(s);
        }
        tempList.add(latestMsg);

        LoggerImpl<R> log = new LoggerImpl<R>(func.apply(this.getItem()), tempList);        
        return (Logger<R>) log;
    }

    public T getItem() {
        return this.item;
    }

    public List<String> getBacklog() {
        return this.backlog;
    }
}
