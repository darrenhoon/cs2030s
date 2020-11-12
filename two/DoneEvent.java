import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DoneEvent extends Event {
    
    private static final double SERVICE_TIME = 1.0;

    public DoneEvent(Customer customer, Server s) {
        super(customer, shop -> {
            int currentId = s.identifier();
            Server currentServer = shop.find(server -> server.identifier() == currentId).get();

            //idk what to do with DoneEvent for now when there are no customers waiting
            /*
               if (currentServer.hasWaitingCustomer() == true) {
               double nextTiming = currentServer.nextAvailableTime() + SERVICE_TIME; 
               Server nextServer = new Server(currentId, false, false, nextTiming);
               Shop nextShop = shop.replace(nextServer);
               ServeEvent nextEvent = new ServeEvent(customer, nextServer);
               return new Pair<Shop, Event>(nextShop, (Event) nextEvent);
               } else {
            //still dk what to do with this for now
            double nextTiming = currentServer.nextAvailableTime();
            Server nextServer = new Server(currentId, true, false, nextTiming);
            Shop nextShop = shop.replace(nextServer);
            ArriveEvent event = 
            return new Pair<Shop, Event>(nextShop, this);
               }
               */
            double nextTiming = currentServer.nextAvailableTime() + SERVICE_TIME; 
            Server nextServer = new Server(currentId, false, false, nextTiming);
            Shop nextShop = shop.replace(nextServer);
            ServeEvent nextEvent = new ServeEvent(customer, nextServer);
            return new Pair<Shop, Event>(nextShop, (Event) nextEvent);
        }, s);
    }

    @Override
    public String toString() {
        double completeTime = this.server().nextAvailableTime();
        int c = this.customer().identifier();
        int s = this.server().identifier();
        String message = String.format("%.3f %d done serving by %d", completeTime, c, s);
        return message; 
    }
}
