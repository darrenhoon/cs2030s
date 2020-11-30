package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Event {    
    
    private final Customer customer;
    private final Function<Shop, Pair<Shop, Event>> func;
    private Server server;

    Event(Customer c, Function<Shop, Pair<Shop, Event>> func) {
        this.customer = c;
        this.func = func;
        this.server = null;
    }

    Event(Customer c, Function<Shop, Pair<Shop, Event>> func, Server s) {
        this.customer = c;
        this.func = func;
        this.server = s;
    }
    
    final Pair<Shop, Event> execute(Shop shop) {
        // declared final to avoid overriding
        return this.func.apply(shop);
        // func is the Function property
    }

    Customer customer() {
        return this.customer;
    }

    Server server() {
        return this.server;
    }
}
