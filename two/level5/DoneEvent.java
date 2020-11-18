//package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DoneEvent extends Event {
    
    public DoneEvent(Customer customer, Server s) {
        super(customer, shop -> {
            
            int currentId = s.identifier();

            Server currentServer = shop.find(server -> server.identifier() == currentId).get();
            
            double nextTiming = currentServer.nextAvailableTime();
            Shop nextShop;
            Server nextServer;

            List<Customer> beforeList = new ArrayList<Customer>(currentServer.cusList());
            List<Customer> afterList;

            //System.out.println("DoneEvent, currently server is: " + currentId);
            //System.out.println("Before edit, Que is: " + beforeList);

                afterList = new ArrayList<Customer>(beforeList);
                boolean isAvailable;

                if (beforeList.size() == 1) {
                    isAvailable = true;
                } else {
                    isAvailable = false;
                }
                afterList.remove(0);
                nextServer = new Server(currentId, isAvailable, false, nextTiming, currentServer.maxQ(), afterList);
                nextShop = shop.replace(nextServer);

            //System.out.println("After edit, Que is: " + afterList);
            
            //previous impl
            /*
            if (currentServer.hasWaitingCustomer() == true) {
                nextServer = new Server(currentId, false, false, nextTiming);
                nextShop = shop.replace(nextServer);
            } else {
                nextServer = new Server(currentId, true, false, nextTiming);
                nextShop = shop.replace(nextServer);
            }
            */
            


            //To check for status of server and current time
            //System.out.println("Server's status: " + nextServer +"\nServer's currentTime: " + nextServer.nextAvailableTime());

            Pair<Shop, Event> pair = new Pair<Shop, Event>(nextShop, null);
            return pair;
 
        }, s);
    }

    public String toString() {
        double completeTime = this.server().nextAvailableTime();
        int c = this.customer().identifier();
        int s = this.server().identifier();
        String message = String.format("%.3f %d done serving by server %d", completeTime, c, s);
        return message; 
    }
}
