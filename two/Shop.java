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

    public Shop(int num) {
        this.list = new ArrayList<Server>();

        int firstID = 1;
        Stream.iterate(firstID, x -> x + 1)
                .limit(num)
                .forEach(id -> this.list.add(new Server(id,true,false,Shop.STARTING_HOUR)));
    }

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

    public Shop replace(Server s) {
        List<Server> tempList = new ArrayList<Server>(this.list);
        tempList.set(s.identifier() - 1, s);
        return new Shop(tempList);
    }
}
