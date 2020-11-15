//package cs2030.simulator;

//import cs2030.simulator.RandomGenerator;
import java.util.function.Supplier;

public class Customer {
    private final int id;
    private final Supplier<Double> arrivalTime;
    private final Supplier<Double> serviceTime;

    public Customer(int id, Supplier<Double> arrivalTime, Supplier<Double> serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public String toString() {
        String message = String.format("%d arrives at %.1f", this.id, this.arrivalTime);      
        return message;
    }

    double arrivalTime() {
        return this.arrivalTime.get();
    }
    
    double serviceTime() {
        return this.serviceTime.get();
    }

    int identifier() {
        return this.id;
    }
}
