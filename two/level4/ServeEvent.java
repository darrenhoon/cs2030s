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
           
            
            //to loop
            if ((currentServer.cusList().isEmpty() == false) &&
                    (currentServer.cusList().get(0).identifier() != customer.identifier())) {

                List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());

                double nextTiming = currentServer.nextAvailableTime();
                Server nextServer = new Server(currentId, false, currentServer.hasWaitingCustomer(), nextTiming, 
                    currentServer.maxQ(), cusList);

                
                ServeEvent nextEvent = new ServeEvent(customer, nextServer);
                return new Pair<Shop, Event>(shop, (Event) nextEvent);
            }
            
            

            // if server can serve now && customer is at the front of the queue
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
            List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
            Server nextServer = new Server(currentId, false, currentServer.hasWaitingCustomer(), nextTiming, 
                    currentServer.maxQ(), cusList);

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
        String message = String.format("%.3f %d served by server %d", time, c, s);
        return message;
    }
}
