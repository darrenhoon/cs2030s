class Customer {
    private final int id;
    private final double arrivalTime;
    private boolean leftArea;
    private boolean isQueuing;
    private boolean beingServed;

    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.leftArea = false;
        this.isQueing = false;
        this.beingServed = false;
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

    void getsServed() {
        this.beingServed = true;
    }

    boolean isBeingServed() {
        return this.beingServed;
    }

    Customer leavePlace() {
        this.leftArea = true;
        return this;
    }
}
