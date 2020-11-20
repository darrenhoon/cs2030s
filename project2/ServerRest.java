package cs2030.simulator;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class ServerRest extends Event {
   
    /**
     * Server takes a rest, all customers and queues delayed and this
     * event happens only after Done Event if probability condition
     * is met.
     */
    public ServerRest(Customer customer, Server s, Supplier<Double> restPeriod) {
        super(customer, shop -> {

            int currentId = s.identifier();
            
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            
            List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
            int maxQ = currentServer.maxQ();

            double period = restPeriod.get();

            double nextTiming = currentServer.nextAvailableTime() + period;

            Server nextServer = (new Server(currentId, currentServer.isAvailable(),
                        currentServer.hasWaitingCustomer(), nextTiming,
                        maxQ, cusList)).rest();
            
            Shop nextShop = shop.replace(nextServer);
            ServerBack nextEvent = new ServerBack(customer, nextServer);
            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent); 
            return pair; 
        }, s);
    }

    @Override
    public String toString() {
        return "REST EVENT FOR Server: " + this.server().identifier();
    }
}
