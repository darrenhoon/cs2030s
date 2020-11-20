package cs2030.simulator;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.function.BinaryOperator;

public class Shop {

    private static double STARTING_HOUR = 0.00;
    private List<Server> list;
    private Optional<Server> optional;

    /**
     * Constructor that takes in an int n and produces a shop with all servers
     * having startime 0 and IDs ascending from 1 up till and including n.
     */
    public Shop(int num) {
        this.list = new ArrayList<Server>();

        int firstID = 1;
        Stream.iterate(firstID, x -> x + 1)
                .limit(num)
                .forEach(id -> this.list.add(new Server(id, true, false, Shop.STARTING_HOUR)));
    }

    /**
     * Constructor that takes in a predefined set of Servers.
     */
    public Shop(List<Server> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return this.list.stream().map(x -> x.toString()).collect(Collectors.toList()).toString();
    }

    public Optional<Server> find(Predicate<Server> pred) {
        return this.list.stream().filter(x -> pred.test(x)).findFirst();
    }

    /**
     * Replaces the server in the shop and returns a new shop
     * with the edited server.
     */
    public Shop replace(Server s) {
        List<Server> tempList = new ArrayList<Server>(this.list);
        tempList.set(s.identifier() - 1, s);
        return new Shop(tempList);
    }

    Server shortestQ() {
        int shortest = this.list.stream()
            .mapToInt(server -> server.qcount())
            .min().getAsInt();
        return this.list.stream()
            .filter(server -> server.qcount() == shortest)
            .findFirst().get();
    }
}
