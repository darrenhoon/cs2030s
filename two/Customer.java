package cs2030.simulator;

//import cs2030.simulator.RandomGenerator;
import java.util.function.Supplier;

public class Customer {
    private final int id;
    private final Supplier<Double> serviceTimeSupplier;
    private final double arrivalTime;
    private final boolean queued;
    private final boolean hijacked;

    /**
     * customer with identifier and arrival time initialized.
     */
    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTimeSupplier = () -> 1.0;
        this.queued = false;
        this.hijacked = false;
    }

    /**
     * customer with identifier, arrival time, and service time supplier initialized.
     */
    public Customer(int id, double arrivalTime, Supplier<Double> serviceTimeSupplier) {
        this.id = id;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.arrivalTime = arrivalTime;
        this.queued = false;
        this.hijacked = false;
    }
 
    /**
     * customer with identifier, arrival time, service time supplier, and queue status initialized.
     */   
    public Customer(int id, double arrivalTime, Supplier<Double> serviceTimeSupplier,
            boolean queued) {
        this.id = id;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.arrivalTime = arrivalTime;
        this.queued = queued;
        this.hijacked = false;
    }
 
    /**
     * customer with identifier, arrival time, service time supplier,
     * queue status, and hijacked status initialized
     * hijacked status is for events that are ServeEvents that hijack
     * the natural ordering events queue in Simulation.
     */   
    public Customer(int id, double arrivalTime, Supplier<Double> serviceTimeSupplier,
            boolean queued, boolean hijacked) {
        this.id = id;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.arrivalTime = arrivalTime;
        this.queued = queued;
        this.hijacked = hijacked;
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

    Customer hijack() {
        return new Customer(this.id, this.arrivalTime,
                this.serviceTimeSupplier, this.queued, true);
    }

    boolean isHijacker() {
        return this.hijacked;
    }
}
