//package cs2030.simulator;

/**
 * Represents a server.
 */
public class Server {
    private final int id;
    private final boolean isAvailable;
    private final int inWaiting;
    private final double nextAvailableTime;
    private final int maxQ;
    private final boolean isResting;
    private static double restingProb;
    private static final String AVAILABLE = "available";
    private static final String BUSY = "busy";

    /**
     * Creates an instance of server.
     * @param identifier         value indicating the server's id number
     * @param isAvailable        boolean value indicating if server is available or not
     * @param hasWaitingCustomer boolean value indicating if server has a waiting customer or not
     * @param nextTime           next time that the server is available to serve
     */
    public Server(int identifier, boolean isAvailable, int waitingCustomer, double nextTime) {
        this.id = identifier;
        this.isAvailable = isAvailable;
        this.inWaiting = waitingCustomer;
        this.nextAvailableTime = nextTime;
        this.maxQ = 1;
        this.isResting = false;
    }

    public Server(int identifier, boolean isAvailable, int waitingCustomer, double nextTime, int maxQ) {
        this.id = identifier;
        this.isAvailable = isAvailable;
        this.inWaiting = waitingCustomer;
        this.nextAvailableTime = nextTime;
        this.maxQ = maxQ;
        this.isResting = false;
    }

    public Server(int identifier, boolean isAvailable, int waitingCustomer, double nextTime, int maxQ, boolean resting) {
        this.id = identifier;
        this.isAvailable = isAvailable;
        this.inWaiting = waitingCustomer;
        this.nextAvailableTime = nextTime;
        this.maxQ = maxQ;
        this.isResting = resting;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return "server " + this.id;
    }
    
    /**
     * Returns a boolean value representing if the server is available.
     */
    public boolean isAvailable() {
        return this.isAvailable;
    }
    
    /**
     * Returns a boolean value representing if the server has a waiting customer.
     */
    public int numOfWaitingCustomer() {
        return this.inWaiting;
    }

    public double getNextAvailableTime() {
        return this.nextAvailableTime;
    }
    
    /**
     * Returns a boolean value. 
     * Indicating whether a customer can queue for a particular Server.
     * @return boolean value to indicate if a customer can queue for a server
     */
    public boolean canQueue() {
        return this.inWaiting < this.maxQ;
    }

    public int maxQ() {
        return this.maxQ;
    }

    public boolean isResting() {
        return isResting;
    }

    public static void setRestingProb(double restProb) {
        Server.restingProb = restProb;
    }

    public static double getRestingProb() {
        return restingProb;
    }

    /**
     * Returns the string representation.
     */
    @Override
    public String toString() {
        String s;
        if (this.isAvailable) {
            s = String.format("%s is %s", this.id, AVAILABLE);
        } else if (isAvailable == false && inWaiting == 0) {
            s = String.format("%s is %s; %s at %.3f", 
                    this.id, BUSY, AVAILABLE, this.nextAvailableTime);
        } else {
            s = String.format("%s is %s; waiting customer to be served at %.3f", 
                    this.id, BUSY, this.nextAvailableTime);
        }       
        return s;
    }
}
