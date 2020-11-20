package cs2030.simulator;

//import cs2030.simulator.RandomGenerator;
import java.util.function.Supplier;

public class Greedy extends Customer {
    
    public Greedy(int id, double arrivalTime) {
        super(id, arrivalTime);
    }

    public Greedy(int id, double arrivalTime, Supplier<Double> serviceTimeSupplier) {
        super(id, arrivalTime, serviceTimeSupplier);
    }
    
    public Greedy(int id, double arrivalTime, Supplier<Double> serviceTimeSupplier, 
            boolean queued) {
        super(id, arrivalTime, serviceTimeSupplier, queued);
    }
    
    public Greedy(int id, double arrivalTime, Supplier<Double> serviceTimeSupplier,
            boolean queued, boolean hijacked) {
        super(id, arrivalTime, serviceTimeSupplier, queued, hijacked);
    }

    @Override
    public String toString() {
        String message = String.format("%d(greedy) arrives at %.1f",
                this.identifier(), this.arrivalTime());      
        return message;
    }

    @Override
    Customer hijack() {
        return (Customer) new Greedy(this.identifier(), this.arrivalTime(),
                this.serviceTimeSupplier(), this.queued(), true);
    }
}
