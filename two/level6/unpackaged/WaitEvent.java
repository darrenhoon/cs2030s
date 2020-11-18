//package cs2030.simulator;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class WaitEvent extends Event {

    public WaitEvent(Customer customer, Server s) {
        super(customer, shop -> {

            int currentId = s.identifier();            
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();

            Server nextServer;
            Shop nextShop;
            
            //if curSer is sCO
            if (currentServer instanceof SelfCheckout) {
                SelfCheckout nextSC = new SelfCheckout(currentId, false,
                        currentServer.hasWaitingCustomer(),
                        currentServer.nextAvailableTime(), currentServer.maxQ());
                nextServer = (Server) nextSC;
                nextShop = shop.replace(nextServer);
                
                //System.out.println("WE => SCO's SCQlist: " + nextSC.SCQlist());
            } 

            //if curSer is a normal Server
            else {
                List<Customer> cusList = new ArrayList<Customer>(currentServer.cusList());
                nextServer = new Server(currentId, false, currentServer.hasWaitingCustomer(),
                        currentServer.nextAvailableTime(), currentServer.maxQ(), cusList);
                //might have to add a isResting() at the line above for constructor
                nextShop = shop.replace(nextServer);
            }
            ServeEvent nextEvent = new ServeEvent(customer, nextServer);

            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, (Event) nextEvent);

            return pair; 
        }, s);
    }

    @Override
    public String toString() {
        int c = this.customer().identifier();
        int s = this.server().identifier();
        double arrivalTime = this.customer().arrivalTime();

        String message = String.format("%.3f %d waits to be served by ", arrivalTime, c);

        if (this.server() instanceof SelfCheckout) {
            message += String.format("self-check %d", s);
        } else {
            message += String.format("server %d", s);
        }
        return message;
    }
}
