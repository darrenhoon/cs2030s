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

            //checkout current shop
            //System.out.println("Current Shops @ ArriveEvent: " + shop);


            //serveEvent
            if ((isAvailable.isEmpty() == false) && (arrivalTime >= isAvailable.get().nextAvailableTime())) {
                Server currentServer = isAvailable.get();
                
                List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                Customer currCus = new Customer(customer.identifier(), customer.arrivalTime(), customer.serviceTimeSupplier());
                cusList.add(currCus);

                Server before = new Server(currentServer.identifier(), true, false,
                        currentServer.nextAvailableTime(), currentServer.maxQ(), currentServer.cusList()
                        , currentServer.isResting());

                Server after = new Server(currentServer.identifier(), false, false, currentServer.nextAvailableTime(),
                        currentServer.maxQ(), cusList, currentServer.isResting());

                Shop nextShop = shop.replace(after);
                
                ServeEvent nextEvent = new ServeEvent(customer, before);
                
                Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
                return pair;
            }

            //waitEvent
            else if (hasWaitingCustomer.isEmpty() == false) {
                Server currentServer = hasWaitingCustomer.get();
                
                List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                
                Customer currCus = new Customer(customer.identifier(), customer.arrivalTime(), customer.serviceTimeSupplier(), true);
                
                cusList.add(currCus);

                int newQlength = currentServer.cusList().size() + 1;
                boolean newHWC = (newQlength == currentServer.maxQ());

                Server before = new Server(currentServer.identifier(), false, false, 
                        currentServer.nextAvailableTime(), currentServer.maxQ(), currentServer.cusList(),
                        currentServer.isResting());

                Server after = new Server(before.identifier(), false, newHWC,
                        currentServer.nextAvailableTime(), currentServer.maxQ(), cusList,
                        currentServer.isResting());
                
                //might need to edit this part to get the timing sequence correct                
                Shop nextShop = shop.replace(after);
                WaitEvent nextEvent = new WaitEvent(currCus, before);
                
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
