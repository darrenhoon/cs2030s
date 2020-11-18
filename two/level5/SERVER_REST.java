//package cs2030.simulator;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class SERVER_REST extends Event {
    
    public SERVER_REST(Customer customer, Server s, Supplier<Double> restPeriod) {
        super(customer, shop -> {

            int currentId = s.identifier();
            
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            
            List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
            int maxQ = currentServer.maxQ();

            double period = restPeriod.get();
            double nextTiming = currentServer.nextAvailableTime() + period;

            Server nextServer = (new Server(currentId, currentServer.isAvailable(), currentServer.hasWaitingCustomer(), nextTiming,
                    maxQ, cusList)).rest();
            
            Shop nextShop = shop.replace(nextServer);
            SERVER_BACK nextEvent = new SERVER_BACK(customer, nextServer);
            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent); 
            return pair; 
        }, s);
    }

    @Override
    public String toString() {
        return "REST EVENT FOR Server: " + this.server().identifier();
    }
}
