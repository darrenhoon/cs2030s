package cs2030.simulator;

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

            Optional<Server> canQ = shop
                .find(x -> x.canQ() == true);

            //checkout current shop
            //System.out.println("Current Shops @ ArriveEvent: " + shop);

            //serveEvent
            if ((isAvailable.isEmpty() == false)
                    && (arrivalTime >= isAvailable.get().nextAvailableTime())) {
                Server currentServer = isAvailable.get();

                Shop nextShop;
                Customer nextCus = new Customer(customer.identifier(), customer.arrivalTime(),
                            customer.serviceTimeSupplier());

                //if current Server is a self-checkout
                if (currentServer instanceof SelfCheckout) {


                    SelfCheckout after = new SelfCheckout(currentServer.identifier(), false,
                            false, currentServer.nextAvailableTime(),
                            currentServer.maxQ());
                    //changed from scadd to servingadd
                    after.servingAdd(customer);
                    nextShop = shop.replace((Server) after);
                } 

                //current Server is a normal server
                else {
                    List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                    
                    cusList.add(nextCus);
                    Server after = new Server(currentServer.identifier(), false,
                            false, currentServer.nextAvailableTime(),
                            currentServer.maxQ(), cusList, currentServer.isResting());
                    nextShop = shop.replace(after);
                }

                ServeEvent nextEvent = new ServeEvent(nextCus, currentServer);

                Pair<Shop, Event> pair = new Pair<Shop, Event>(shop, (Event) nextEvent);
                return pair;
            }

            //waitEvent
            else if (canQ.isEmpty() == false) {
                Server currentServer = canQ.get();
                Shop nextShop;
                Customer nextCus = new Customer(customer.identifier(), customer.arrivalTime(),
                            customer.serviceTimeSupplier(), true);
 
                //if curSer is a selfcheckout
                if (currentServer instanceof SelfCheckout) {
                   
                    SelfCheckout sc = (SelfCheckout) currentServer;

                    SelfCheckout after = new SelfCheckout(currentServer.identifier(), 
                            false, true, currentServer.nextAvailableTime(),
                            currentServer.maxQ());
                   
                    //customer goes into the static queue
                    after.SCQadd(nextCus);
                    nextShop = shop.replace((Server) after);
                } 

                //curSer is a normal Server
                else {
                    List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                   
                    cusList.add(nextCus);
                    
                    Server after = new Server(currentServer.identifier(), false, true,
                            currentServer.nextAvailableTime(), currentServer.maxQ(), cusList,
                            currentServer.isResting());
                    nextShop = shop.replace(after);
                }

                WaitEvent nextEvent = new WaitEvent(nextCus, currentServer);
                Pair<Shop, Event> pair = new Pair<Shop, Event>(shop, (Event) nextEvent);
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
