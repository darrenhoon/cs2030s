/*
import cs2030.simulator.Customer;
import cs2030.simulator.Server;
import cs2030.simulator.Shop;
import cs2030.simulator.Pair;
import cs2030.simulator.Event;
import cs2030.simulator.ArriveEvent;
import cs2030.simulator.ServeEvent;
import cs2030.simulator.WaitEvent;
import cs2030.simulator.LeaveEvent;
import cs2030.simulator.DoneEvent;
import cs2030.simulator.EventComparator;
import cs2030.simulator.Simulation;
import cs2030.simulator.Statistics;
import cs2030.simulator.RandomGenerator;
*/

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Supplier;

public class Main {

    /**
     * Reads user's inputs via scan and creates lists for servers and customers.
     * @param args used to take in CLI arguments for following data in RandomGenerator
     */
    public static void main(String[] args) {
        double startTime = 0.00;

        //values taken in by scanner
        int seedValue = Integer.parseInt(args[0]);
        int numOfServers = Integer.parseInt(args[1]);
        int numOfCustomers = Integer.parseInt(args[2]); //also number of ARRIVAL EVENTS
        double arrivalRate = Double.parseDouble(args[3]);
        double serviceRate =  Double.parseDouble(args[4]);
        double restRate = 0.0; //change later to Double.parseDouble(args[4])
        
        /*
        System.out.println("seedValue: " + seedValue);
        System.out.println("servers: " + numOfServers);
        System.out.println("customers: " + numOfCustomers);
        System.out.println("arrivalrate: " + arrivalRate);
        System.out.println("servicerate: " + serviceRate);
        */

        int serverId = 1;
        int customerId = 1;

        //generate servers
        List<Server> serverList = Stream.iterate(serverId, x-> x + 1)
            .limit(numOfServers)
            .map(id -> new Server(id, true, false, startTime))
            .collect(Collectors.toList());

        Shop shop = new Shop(serverList);
        
        Simulation sim = new Simulation(shop, seedValue, numOfCustomers, arrivalRate, serviceRate, restRate);
        sim.simulate();
    }
}
