import cs2030.simulator.Customer;
import cs2030.simulator.Server;
import cs2030.simulator.Event;
import cs2030.simulator.ArriveEvent;
import cs2030.simulator.ServeEvent;
import cs2030.simulator.WaitEvent;
import cs2030.simulator.LeaveEvent;
import cs2030.simulator.DoneEvent;
import cs2030.simulator.EventComparator;
import cs2030.simulator.Simulation;
import cs2030.simulator.Statistics;
import cs2030.simulator.Shop;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;


public class Main {

    /**
     * Reads user's inputs via scan and creates lists for servers and customers.
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        double startTime = 0.00;
        int numOfServers = sc.nextInt();
        int customerId = 1;
        int serverId = 1;
        
        List<Server> serverList = new ArrayList<Server>();
        List<Customer> customerList = new ArrayList<Customer>();

        while (numOfServers > 0) {
            Server s = new Server(serverId, true, false, startTime);
            serverList.add(s);
            numOfServers--;
            serverId++;
        }

        while (sc.hasNextDouble()) {
            double time = sc.nextDouble();
            Customer c = new Customer(customerId, time);
            customerList.add(c);
            customerId++;
        }

        Simulation sim = new Simulation(customerList,serverList);
        sim.simulate();
    }
}
