package cs2030.simulator;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class SERVER_BACK extends Event {
    
    public SERVER_BACK(Customer customer, Server s) {
        super(customer, shop -> {

            int currentId = s.identifier();
            
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            
            List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
            int maxQ = currentServer.maxQ();
            
            double nextTiming = currentServer.nextAvailableTime();
            
            Server nextServer = new Server(currentId, currentServer.isAvailable(), currentServer.hasWaitingCustomer(), nextTiming,
                    maxQ, cusList, false);
            
            Shop nextShop = shop.replace(nextServer);

            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, null);
            
            return pair; 
        }, s);
    }

    @Override
    public String toString() {
        return "BACK EVENT FOR Server: " + this.server().identifier();
    }
}
