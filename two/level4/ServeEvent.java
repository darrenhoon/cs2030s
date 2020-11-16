//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ServeEvent extends Event {
    
    public ServeEvent(Customer customer, Server s) {
        super(customer, shop -> {
            
            //current Server's details
            int currentId = s.identifier();
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();

            if (currentServer.isAvailable() == false) {
                ServeEvent nextEvent = new ServeEvent(customer, s);
                return new Pair<Shop, Event>(shop, (Event) nextEvent);
            }
            //System.out.println("Current CUSTOMER is: " +  customer.identifier());
            //System.out.println(currentServer.nextAvailableTime() + "\n");
            //serviceTime edited to be based on RandomGenerator
            double SERVICE_TIME = customer.serviceTime();

            double availableTime = currentServer.nextAvailableTime();
            double arrivalTime = customer.arrivalTime();
            double currentTime;
            if (availableTime > arrivalTime) {
                currentTime = availableTime;
            } else {
                currentTime = arrivalTime;
            }            
            double nextTiming = currentTime + SERVICE_TIME;
            int remainingCustomers = currentServer.waitingCustomers();
            Server nextServer = new Server(currentId, false, currentServer.hasWaitingCustomer(), nextTiming, 
                    currentServer.maxQ(), remainingCustomers);

            //Check server's current Time after adding SERVICE TIME
            //System.out.println("Server's current Time (to be doneEvent's): " + nextServer.nextAvailableTime());

            Shop nextShop = shop.replace(nextServer);
            Customer nextCustomer = new Customer(customer.identifier(), nextTiming, customer.serviceTimeSupplier());

            DoneEvent nextEvent = new DoneEvent(nextCustomer, nextServer);
            
            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);
            
            return pair; 
        }, s);
    }

    @Override
    public String toString() {

        double time;

        double availableTime = this.server().nextAvailableTime();
        double arrivalTime = this.customer().arrivalTime();
        
        if (availableTime > arrivalTime) {
            time = availableTime;
        } else {
            time = arrivalTime;
        }
        int c = this.customer().identifier();
        int s = this.server().identifier();
        String message = String.format("%.3f %d served by %d", time, c, s);
        return message;
    }
}
