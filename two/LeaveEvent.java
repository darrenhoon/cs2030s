import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class LeaveEvent extends Event {
    private static final double SERVICE_TIME = 1.0;

    public LeaveEvent(Customer customer) {
        super(customer, shop -> {
        });
    }
    @Override
    public String toString() {
        double arrivalTime = this.customer().arrivalTime();
        int c = this.customer().identifier();
        String message = String.format("%.3f %d ", arrivalTime, c);
        message += "leaves";
        return message;
    }
}
