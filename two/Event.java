import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

class Event {

    private static int ARRIVE = 1;
    private static int SERVE = 2;
    private static int WAIT = 3;
    private static int DONE = 4;
    private static int LEAVE = 5;
    private final int stage;
    private final Customer customer;
    private final Function<Shop, Pair<Shop, Event>> func;

    Event(Customer c, int i) {
        this.customer = c;
        this.stage = i;
        this.func = (shop -> {
            Server before = shop.find(x -> x.isAvailable()).get();
            double nextAvailableTime = before.nextAvailableTime() + this.SERVICE_TIME;
            Server after = new Server(before.identifier(), false, false, nextAvailableTime);
            Shop nextShop = shop.replace(after);
            ServeEvent nextEvent = new ServeEvent(this.customer, nextShop, before);
            Pair<Shop, Event> pair = new Pair(nextShop, (Event) nextEvent);
            return pair;
            /*
               if (shop.find(server -> server.isAvailable() && (arrivalTime >= server.nextAvailableTime()) )) {
               Server before = shop.find(x -> x.isAvailable()).get();
               double nextAvailableTime = before.nextAvailableTime() + this.SERVICE_TIME;
               Server after = new Server(before.identifier(), false, false, nextAvailableTime);
               Shop nextShop = shop.replace(after);
               ServeEvent nextEvent = new ServeEvent(this.customer, nextShop, before);
               return (Event) nextEvent;
               }
               if (shop.find(server -> server.getHasWaitingCustomer() == false)) {

               Server before = shop.find(x -> x.hasWaitingCustomer() == false).get();

               double nextAvailableTime = before.nextAvailableTime();
               Server after = new Server(before.identifier(), false, true, nextAvailableTime);
               Shop nextShop = shop.replace(after);

               WaitEvent nextEvent = new WaitEvent(this.customer, nextShop, before);
               return (Event) nextEvent;
               }
               LeaveEvent nextEvent = new LeaveEvent(this.customer, shop);
               return (Event) nextEvent;
               */
        });

;
    }
    
    final Pair<Shop, Event> execute(Shop shop) {
        // declared final to avoid overriding
        return this.func.apply(shop);
        // func is the Function property
    }
}
