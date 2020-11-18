//package cs2030.simulator;

public class ServerBack extends Event {
    public static final int RESTING = 1;
    public static final int BACK = 1;
    public static final int ARRIVES = 2;
    public static final int DONE = 3;
    public static final int LEAVES = 4;
    public static final int SERVED = 5;
    public static final int WAITS = 6;
    public static final int WAITING = 7;
    
    public ServerBack(Customer customer, Server server) {
        super(customer, shop -> {

            //information needed from server to be passed on
            int currentId = server.getId();
            Server currentServer = shop.find(s -> s.getId() == currentId).get();
            int numOfWaitingCustomer = currentServer.numOfWaitingCustomer();
            double nextTiming = currentServer.getNextAvailableTime();
            int maxQLength = currentServer.maxQ();

            //change server to be available and not resting
            Server nextServer = new Server(currentId, true, numOfWaitingCustomer, nextTiming, maxQLength, false);
            Shop nextShop = shop.replace(nextServer);
            return Pair.of(nextShop, null);
        }, server, BACK);
    }
 
    //for debugging purposes
    // @Override
    // public String toString() {
    //     return String.format("%.3f server %s back", this.getServer().getNextAvailableTime(), this.getServer().getId());
    // }
}
