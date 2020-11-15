//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DoneEvent extends Event {
    
    public DoneEvent(Customer customer, Server s) {
        super(customer, shop -> {
            
            int currentId = s.identifier();

            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            
            double nextTiming = currentServer.nextAvailableTime();
            Shop nextShop;
            Server nextServer;

            if (currentServer.hasWaitingCustomer() == true) {
                nextServer = new Server(currentId, false, false, nextTiming);
                nextShop = shop.replace(nextServer);
            } else {
                nextServer = new Server(currentId, true, false, nextTiming);
                nextShop = shop.replace(nextServer);
            }
            
            System.out.println("Server's status: " + nextServer +"\nServer's currentTime: " + nextServer.nextAvailableTime());

            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, null);
            return pair;
 
        }, s);
    }

    public String toString() {
        double completeTime = this.server().nextAvailableTime();
        int c = this.customer().identifier();
        int s = this.server().identifier();
        String message = String.format("%.3f %d done serving by %d", completeTime, c, s);
        return message; 
    }
}
