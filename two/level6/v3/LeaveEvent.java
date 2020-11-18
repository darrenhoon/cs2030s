//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a leave event which comes after an arrive event.
 */
public class LeaveEvent extends Event {
    public static final int ARRIVES = 1;
    public static final int RESTING = 2;
    public static final int BACK = 2;
    public static final int DONE = 3;
    public static final int LEAVES = 4;
    public static final int SERVED = 5;
    public static final int WAITS = 6;
    public static final int WAITING = 7;

    /**
     * Creates a leave event.
     * @param customer an instance of a customer
     */
    public LeaveEvent(Customer customer) {
        super(customer, shop -> {
            return Pair.of(shop, null);
        }, LEAVES);
    }

    /**
     * Returns a string representation.
     */
    @Override
    public String toString() {
        double leaveTime = this.getCustomer().getArrivalTime();
        return String.format("%.3f %s leaves", leaveTime, this.getCustomer().getId());
    }
}
