class Customer {
    private final int id;
    private final double arrivalTime;
    private boolean leftArea;
    private boolean isQueing;

    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.leftArea = false;
        this.isQueing = false;
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
    
    boolean hasLeft() {
        return this.leftArea;
    }

    Customer leavePlace() {
        this.leftArea = true;
        return this;
    }
}
