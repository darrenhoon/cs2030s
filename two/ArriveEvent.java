import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ArriveEvent extends Event {
    private static final double SERVICE_TIME = 1.0;
    private final Customer customer;
    public ArriveEvent(Customer c) {
        super(c, 1);
    }
    
    @Override
    public String toString() {
        double time = this.customer.arrivalTime();
        int c = this.customer.identifier();
        String message = String.format("%.3f %d arrives", time, c);
        return message;
    }
}
