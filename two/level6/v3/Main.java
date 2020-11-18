import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Supplier;

//import cs2030.simulator.Customer;
//import cs2030.simulator.Server;
//import cs2030.simulator.Simulator;

/**
 * Represents the main driver class to start the simulation.
 */
class Main {
    public static void main(String[] args) {

        int seedValue = Integer.parseInt(args[0]);
        int numOfServers = Integer.parseInt(args[1]);
        int numOfCheckoutCounters = Integer.parseInt(args[2]);
        int maxQueueLength = Integer.parseInt(args[3]);
        int numOfCustomers = Integer.parseInt(args[4]); //also number of ARRIVAL EVENTS
        double arrivalRate = Double.parseDouble(args[5]);
        double serviceRate =  Double.parseDouble(args[6]);
        double restingRate = Double.parseDouble(args[7]);
        double restingProbability = Double.parseDouble(args[8]);

        int serverId = 1;
        double startTime = 0;

        List<Server> serverList = Stream.iterate(serverId, x -> x + 1)
            .limit(numOfServers)
            .map(id -> new Server(id, true, 0, startTime, maxQueueLength))
            .collect(Collectors.toList());

        Stream.iterate(numOfServers + 1, s -> s + 1)
            .limit(numOfCheckoutCounters)
            .map(counterId -> new SelfCheckout(counterId, true, 0, startTime, maxQueueLength))
            .collect(Collectors.toList())
            .forEach(s -> serverList.add(s));

        Shop shop = new Shop(serverList);
        Simulator sim = new Simulator(shop, seedValue, numOfCustomers, arrivalRate, 
                                serviceRate, restingRate, restingProbability);
        sim.simulate();
    }
}
