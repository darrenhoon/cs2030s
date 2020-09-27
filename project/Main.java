import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        int customerId = 1;
        int serverId = 1;
        
        List<Server> serverList = new ArrayList<Server>();
        List<Customer> customerList = new ArrayList<Customer>();

        while (numOfServers) {
            Server s = new Server(serverId, true, false, startTime);
            serverList.add(s);
            numOfServers--;
            serverId++;
        }

        while (sc.hasNextDouble()) {
            double time = sc.nextDouble();
            Customer c = new Customer(customerId, time);
            customerList.add(c);
        }

        Simulation sim = new Simulation(customerList,serverList);
        sim.simulate();
    }
}
