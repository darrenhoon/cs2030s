import cs2030.simulator.Customer;
import cs2030.simulator.Greedy;
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
import cs2030.simulator.ServerRest;
import cs2030.simulator.ServerBack;
import cs2030.simulator.SelfCheckout;

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
        int seedValue;
        int numOfServers;
        int selfCheckout;
        int maxQ;
        int numOfCustomers;
        double arrivalRate;
        double serviceRate;
        double restRate;
        double restProb;
        double greedyProb;
        
        //level3
        if (args.length == 5) {

            seedValue = Integer.parseInt(args[0]);
            numOfServers = Integer.parseInt(args[1]);
            selfCheckout = 0;
            maxQ = 1;
            numOfCustomers = Integer.parseInt(args[2]); //also number of ARRIVAL EVENTS
            arrivalRate = Double.parseDouble(args[3]);
            serviceRate =  Double.parseDouble(args[4]);
            restRate = 1.0;
            restProb = 0.0;
            greedyProb = 0.0;
        } else if (args.length == 6) { //level4
            seedValue = Integer.parseInt(args[0]);
            numOfServers = Integer.parseInt(args[1]);
            selfCheckout = 0;
            maxQ = Integer.parseInt(args[2]);
            numOfCustomers = Integer.parseInt(args[3]); //also number of ARRIVAL EVENTS
            arrivalRate = Double.parseDouble(args[4]);
            serviceRate =  Double.parseDouble(args[5]);
            restRate = 1.0;
            restProb = 0.0;
            greedyProb = 0.0;
        } else if (args.length == 8) { //level5
            seedValue = Integer.parseInt(args[0]);
            numOfServers = Integer.parseInt(args[1]);
            selfCheckout = 0;
            maxQ = Integer.parseInt(args[2]);
            numOfCustomers = Integer.parseInt(args[3]); //also number of ARRIVAL EVENTS
            arrivalRate = Double.parseDouble(args[4]);
            serviceRate =  Double.parseDouble(args[5]);
            restRate = Double.parseDouble(args[6]);
            restProb = Double.parseDouble(args[7]);
            greedyProb = 0.0; 
        } else if (args.length == 9) { //level6
            seedValue = Integer.parseInt(args[0]);
            numOfServers = Integer.parseInt(args[1]);
            selfCheckout = Integer.parseInt(args[2]);
            maxQ = Integer.parseInt(args[3]);
            numOfCustomers = Integer.parseInt(args[4]); //also number of ARRIVAL EVENTS
            arrivalRate = Double.parseDouble(args[5]);
            serviceRate =  Double.parseDouble(args[6]);
            restRate = Double.parseDouble(args[7]);
            restProb = Double.parseDouble(args[8]);
            greedyProb = 0.0;
        } else { //level7
            seedValue = Integer.parseInt(args[0]);
            numOfServers = Integer.parseInt(args[1]);
            selfCheckout = Integer.parseInt(args[2]);
            maxQ = Integer.parseInt(args[3]);
            numOfCustomers = Integer.parseInt(args[4]); //also number of ARRIVAL EVENTS
            arrivalRate = Double.parseDouble(args[5]);
            serviceRate =  Double.parseDouble(args[6]);
            restRate = Double.parseDouble(args[7]);
            restProb = Double.parseDouble(args[8]);
            greedyProb = Double.parseDouble(args[9]);
        }

        Simulation sim = new Simulation(seedValue, numOfServers, selfCheckout,
                maxQ, numOfCustomers, arrivalRate, serviceRate,
                restRate, restProb, greedyProb);
        sim.simulate();
    }
}
