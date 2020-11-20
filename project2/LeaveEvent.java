package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class LeaveEvent extends Event {

    /**
     * Leave Event initialized with only a customer.
     */
    public LeaveEvent(Customer customer) {
        super(customer, shop -> {
            Pair<Shop, Event> pair = new Pair<Shop, Event>(shop, null);
            return pair;
        });
    }

    @Override
    public String toString() {
        double arrivalTime = this.customer().arrivalTime();
        int c = this.customer().identifier();

        String message;
        if (this.customer() instanceof Greedy) {
            message = String.format("%.3f %d(greedy) ", arrivalTime, c);
        
        } else {
            message = String.format("%.3f %d ", arrivalTime, c);       
        }
       
        message += "leaves";
        return message;
    }
}
