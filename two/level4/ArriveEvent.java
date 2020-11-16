//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ArriveEvent extends Event {
    
    public ArriveEvent(Customer customer) {
        super(customer, shop -> {
            
            double arrivalTime = customer.arrivalTime();
            Optional<Server> isAvailable = shop
                .find(x -> x.isAvailable());
 
            Optional<Server> hasWaitingCustomer = shop
                .find(x -> x.hasWaitingCustomer() == false);
            
            if ((isAvailable.isEmpty() == false) && (arrivalTime >= isAvailable.get().nextAvailableTime())) {
                
                Server currentServer = isAvailable.get();
                
                Server before = new Server(currentServer.identifier(), true, false,
                        currentServer.nextAvailableTime(), currentServer.maxQ(), 0);

                Server after = new Server(before.identifier(), true, false, currentServer.nextAvailableTime(),
                        currentServer.maxQ(), 0);
                
                Shop nextShop = shop.replace(after);
                
                ServeEvent nextEvent = new ServeEvent(customer, before);
                
                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            }

            else if (hasWaitingCustomer.isEmpty() == false) {
                Server currentServer = hasWaitingCustomer.get();

                int newQlength = currentServer.waitingCustomers() + 1;
                boolean newHWC = (newQlength == currentServer.maxQ());

                Server before = new Server(currentServer.identifier(), false, false, currentServer.nextAvailableTime(),
                        currentServer.maxQ(), currentServer.waitingCustomers());

                Server after = new Server(before.identifier(), false, newHWC, currentServer.nextAvailableTime(),
                        currentServer.maxQ(), newQlength);
                
                //might need to edit this part to get the timing sequence correct                
                Shop nextShop = shop.replace(after);
                WaitEvent nextEvent = new WaitEvent(customer, before);
                
                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            } 
            
            else {
                LeaveEvent nextEvent = new LeaveEvent(customer);
                Pair<Shop, Event> pair = new Pair<Shop, Event>(shop, (Event) nextEvent);
                return pair;
            }
        });
    }

    @Override
    public String toString() {
        double time = this.customer().arrivalTime();
        int c = this.customer().identifier();
        String message = String.format("%.3f %d arrives", time, c);
        return message;
    }
}
