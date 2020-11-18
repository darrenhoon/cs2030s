//package cs2030.simulator;
import java.util.function.Supplier;

/**
 * Creates a Customer object.
 */
public class Customer {
    private final int id;
    private final double arrivalTime;
    
    /**
     * Creates an instance of Customer.
     * @param id          customer id
     * @param arrivalTime customer's arrival time
     */
    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Returns the string representation.
     */
    public String toString() {
        return String.format("%s arrives at %s", this.id, this.arrivalTime);
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public int getId() {
        return this.id;
    }
}
