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
            Optional<Server> serverOptional = shop
                .find(x -> (x.isAvailable()) || (x.hasWaitingCustomer() == false));
            
            if ((serverOptional.isEmpty() == false) && (arrivalTime >= serverOptional.get().nextAvailableTime()) && (serverOptional.get().isAvailable())) {
                
                Server currentServer = serverOptional.get();

                Server before = new Server(currentServer.identifier(), true, false,
                        currentServer.nextAvailableTime());

                Server after = new Server(before.identifier(), false, false, currentServer.nextAvailableTime());
                
                Shop nextShop = shop.replace(after);
                
                ServeEvent nextEvent = new ServeEvent(customer, before);
                
                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            }

            if ((serverOptional.isEmpty() == false) && (serverOptional.get().hasWaitingCustomer() == false)) {
                Server currentServer = serverOptional.get();
                Server before = new Server(currentServer.identifier(), false, false,
                        currentServer.nextAvailableTime());

                Server after = new Server(before.identifier(), false, true, currentServer.nextAvailableTime());
                
                //might need to edit this part to get the timing sequence correct
                
                Shop nextShop = shop.replace(after);

                WaitEvent nextEvent = new WaitEvent(customer, before);
                
                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            }

            LeaveEvent nextEvent = new LeaveEvent(customer);
            Pair<Shop, Event> pair = new Pair<Shop, Event>(shop, (Event) nextEvent);
            return pair;

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
