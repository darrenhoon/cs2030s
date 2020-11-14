package cs2030.simulator;

public class Customer {
    private final int id;
    private final double arrivalTime;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public String toString() {
        String message = String.format("%d arrives at %.1f", this.id, this.arrivalTime);      
        return message;
    }

    double arrivalTime() {
        return this.arrivalTime;
    }

    int identifier() {
        return this.id;
    }
}
