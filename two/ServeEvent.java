import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ServeEvent extends Event {
    private final Server server;
    private final Customer customer;
    private final List<Server> serverList;
    private static final double SERVICE_TIME = 1.0;
    private final double nextTime;
    private final Shop shop;

    public ServeEvent(Customer customer, Shop shop, Server s) {
        this.customer = customer;
        this.shop = shop;
        this.server = server;
        this.nextTime = s.nextAvailableTime() + this.SERVICE_TIME;

        Function<Pair<Shop,Event>> func = (shop -> {
            int currentId = s.identifier();
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            Server nextServer;
            double nextTiming = currentServer.nextAvailableTime() + this.SERVICE_TIME;

            if (currentServer.hasWaitingCustomer() == true) {
                Server nextServer = new Server(currentId, false, false, nextTiming);
            } else {
                Server nextServer = new Server(currentId, true, false, nextTiming);
            }
            Shop nextShop = shop.replace(nextServer);
            DoneEvent nextEvent = new DoneEvent(this.customer, nextShop);
            return (Event) nextEvent; 
        });
        super(customer, func);
    }

    @Override
    public String toString() {

        double time;

        double availableTime = this.server.nextAvailableTime();
        double arrivalTime = this.customer.arrivalTime();
        
        if (availableTime > arrivalTime) {
            time = availableTime;
        } else {
            time = arrivalTime;
        }
        int c = this.customer.identifier();
        int s = this.server.identifier();
        String message = String.format("%.3f %d served by %d", time, c, s);
        return message;
    }
}
