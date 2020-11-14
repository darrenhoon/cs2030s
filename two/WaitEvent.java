package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class WaitEvent extends Event {
    
    private static final double SERVICE_TIME = 1.0;

    public WaitEvent(Customer customer, Server s) {
        super(customer, shop -> {
            int currentId = s.identifier();
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            double nextTiming = currentServer.nextAvailableTime() + SERVICE_TIME;
            Server nextServer = new Server(currentId, false, false, nextTiming);
            Shop nextShop = shop.replace(nextServer);
            ServeEvent nextEvent = new ServeEvent(customer, nextServer);
            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
            return pair; 
        }, s);
    }

    @Override
    public String toString() {
        int c = this.customer().identifier();
        int s = this.server().identifier();
        double arrivalTime = this.customer().arrivalTime();
        return String.format("%.3f %d waits to be served by server %d",arrivalTime, c, s);
    }
}
