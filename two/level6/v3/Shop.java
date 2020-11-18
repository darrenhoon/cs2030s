//package cs2030.simulator;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Predicate;

public class Shop {
    private final List<Server> serverList;
    
    public Shop(int numOfServers) {
        this.serverList = new ArrayList<Server>();
        int firstId = 1;
        Stream.iterate(firstId, x -> x + 1)
            .limit(numOfServers)
            .forEach(id -> this.serverList.add(new Server(id, true, 0, 0)));
    }

    public Shop(List<Server> serverList) {
        this.serverList = serverList;
    }

    public Optional<Server> find(Predicate<Server> pred) {
        return this.serverList.stream().filter(x -> pred.test(x)).findFirst();
    }

    public Shop replace(Server server) {
        return new Shop(serverList.stream()
                .map(s -> s.getId() == server.getId() ? server : s)
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return this.serverList.stream().map(x -> x.toString())
            .collect(Collectors.toList()).toString();
    }
}