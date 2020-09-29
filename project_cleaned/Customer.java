

public class Customer {
    private final int id;
    private final double arrivalTime;

    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public String toString() {
        String message = String.format("%d arrives at %.1f", this.id, this.arrivalTime);      
        return message;
    }

    double getArrivalTime() {
        return this.arrivalTime;
    }

    int getId() {
        return this.id;
    }
}
