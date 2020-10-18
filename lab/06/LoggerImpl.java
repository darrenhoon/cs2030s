import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    public <R> Logger<R> flatMap(Function<? super T, ? extends Logger<? extends R>> func) {
        /*
        System.out.println("\nSTEP: " + LoggerImpl.COUNTER + " FlatMAP");
        System.out.println(func.apply(this.getItem()));
        LoggerImpl.COUNTER++;
        */

        List<String> tempList = new ArrayList<String>();
        for (String s: this.backlog) {
            tempList.add(s);
        }
        
        if (this.equals(func.apply(this.getItem())) == false) {
            R item = func.apply(this.getItem()).getItem();
            String latestMsg = String.format("Value changed! New value = " + item);
            tempList.add(latestMsg);
        }

        Logger<? extends R> log = func.apply(this.getItem());
        //Logger<R> log2 = log.map(x -> x);
        LoggerImpl<R> log3 = new LoggerImpl<R>(log.getItem(), tempList);
        return (Logger<R>) log3;
    }
   

    public <R> Logger<R> map(Function<? super T, ? extends R> func) {

        String latestMsg;
        if (this.getItem() == func.apply(this.getItem())) {
            latestMsg = String.format("Value unchanged. Value = %s",this.getItem());
        } else {
            R item = func.apply(this.getItem());
            latestMsg = String.format("Value changed! New value = " + item);
        }
        
        //this.backlog.add(latestMsg);
        List<String> tempList = new ArrayList<String>();

        int i = 0;
        for (String s: this.backlog) {
            tempList.add(s);
            i++;
        }

        tempList.add(latestMsg);

        /*
        System.out.println("\nStep: " + LoggerImpl.COUNTER + " mAP");
        System.out.println(func.apply(this.getItem()));
        LoggerImpl.COUNTER++;

        if (this.backlog.get(i - 1).equals(latestMsg) == false) { 
            tempList.add(latestMsg);
        }
        */

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
        return p.test(this.getItem());
    }

    public T getItem() {
        return this.item;
    }

    public List<String> getBacklog() {
        return this.backlog;
    }
}
