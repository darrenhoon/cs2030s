//package cs2030.simulator;

/**
 * Represents a server.
 */
public class SelfCheckout extends Server {
    public SelfCheckout(int identifier, boolean isAvailable, int waitingCustomer, double nextTime) {
        super(identifier, isAvailable, waitingCustomer, nextTime);
    }

    public SelfCheckout(int identifier, boolean isAvailable, int waitingCustomer, double nextTime, int maxQ) {
        super(identifier, isAvailable, waitingCustomer, nextTime, maxQ);
    }

    public String getName() {
        return "self-check " + this.getId();
    }
}
