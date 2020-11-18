//package cs2030.simulator;

//import cs2030.simulator.RandomGenerator;
import java.util.function.Supplier;

public class Customer {
    private final int id;
    private final Supplier<Double> serviceTimeSupplier;
    private final double arrivalTime;
    private final boolean queued;

    /*
    public Customer(int id, Supplier<Double> arrivalTimeSupplier, Supplier<Double> serviceTimeSupplier) {
        this.id = id;
        this.arrivalTimeSupplier = arrivalTimeSupplier;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.arrivalTime = null;
        this.serviceTime = null;
    }

    public Customer(int id, double arrivalTime, double serviceTime) {
        this.id = id;
        this.arrivalTimeSupplier = null;
        this.serviceTimeSupplier = null;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    */

    public Customer(int id, double arrivalTime, Supplier<Double> serviceTimeSupplier) {
        this.id = id;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.arrivalTime = arrivalTime;
        this.queued = false;
    }
    
    public Customer(int id, double arrivalTime, Supplier<Double> serviceTimeSupplier, boolean queued) {
        this.id = id;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.arrivalTime = arrivalTime;
        this.queued = queued;
    }
 

 
    public String toString() {
        String message = String.format("%d arrives at %.1f", this.id, this.arrivalTime);      
        return message;
    }

    double arrivalTime() {
        return this.arrivalTime;
    }
    
    double serviceTime() {
        return this.serviceTimeSupplier.get();
    }

    Supplier<Double> serviceTimeSupplier() {
        return this.serviceTimeSupplier;
    }

    boolean queued() {
        return this.queued;
    }

    int identifier() {
        return this.id;
    }
}
