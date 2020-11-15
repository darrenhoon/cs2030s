//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Optional;

public class ServeEvent extends Event {
    
    public ServeEvent(Customer customer, Server s) {
        super(customer, shop -> {
            int currentId = s.identifier();
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            Server nextServer;

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

            if (currentServer.hasWaitingCustomer() == true) {
                nextServer = new Server(currentId, false, false, nextTiming);
            } else {
                nextServer = new Server(currentId, true, false, nextTiming);
            }

            //Check server's current Time after adding SERRICE TIME
            //System.out.println("Server's current Time (to be doneEvent's): " + nextServer.nextAvailableTime());

            Shop nextShop = shop.replace(nextServer);
            
            DoneEvent nextEvent = new DoneEvent(customer, nextServer);
            
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
