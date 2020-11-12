import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DoneEvent extends Event {

    private final Customer customer;
    private final Server = server;

    private static final double SERVICE_TIME = 1.0;
    
    public DoneEvent(Customer customer, Shop shop, Server s) {
        this.customer = customer;
        this.server = s;

        int nextTime = s.nextAvailableTime() + this.SERVICE_TIME;

        Function<Shop, Pair<Shop, Event>> func = (shop -> {

            int currentId = s.identifier();
            
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            Server nextServer;
            double nextTiming;
            Event nextEvent;

            if (currentServer.hasWaitingCustomer() == true) {
                nextTiming = currentServer.nextAvailableTime() + this.SERVICE_TIME; 
                Server nextServer = new Server(currentId, false, false, nextTiming);
                Shop nextShop = shop.replace(nextServer);
                ServeEvent nextEvent = new ServeEvent(this.customer, nextShop, nextServer);
                return new Pair<Shop, Event>(nextShop, (Event) nextEvent);
            } else {
                nextTiming = currentServer.nextAvailableTime();
                Server nextServer = new Server(currentId, true, false, nextTiming);
                Shop nextShop = shop.replace(nextServer);
                return new Pair<Shop, Event>(nextShop, this);
            }
        });
        super(customer, func);
    }

    @Override
    public String toString() {
        double completeTime = this.server.getAvailableTime();
        int c = this.customer.identifier();
        int s = this.server.identifier();
        String message = String.format("%.3f %d done serving by %d", completeTime, c, s);
        return message; 
    }

    Customer customer() {
        return this.customer;
    }
    
    Server server() {
        return this.server;
    }
}
