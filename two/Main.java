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

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.stream.Stream;


public class Main {

    /**
     * Reads user's inputs via scan and creates lists for servers and customers.
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        double startTime = 0.00;

        //values taken in by scanner
        int seedValue = sc.nextInt();
        int numOfServers = sc.nextInt();
        int numOfCustomers = sc.nextInt();
        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        
        RandomGenerator rg = new RandomGenerator(seedValue, arrivalRate, serviceRate, 0.0);

        int serverId = 1;
        int customerId = 1;

        //generate servers
        List<Server> serverList = Stream.iterate(serverId, x-> x + 1)
            .limit(numOfServer)
            .map(id -> new Server(id, true, false, startTime))
            .collect(Collectors.toList());

        //generate customers
        Customer firstCustomer = new Customer(customerId, startTime);
        List<Customer> remainingCustomers = Stream.iterate(customerId + 1, x -> x + 1)
            .limit(numOfCustomers - 1)
            .map(id -> new Customer(id, rg.getInterArrivalTime()));

        List<Customer> customerList = new ArrayList<Customer>();

        customerList.add(firstCustomer);
        customerList.addAll(remainingCustomers);


        Simulation sim = new Simulation(customerList,serverList);
        sim.simulate();
    }
}
