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
        List<Server> = serverList = new ArrayList<Server>();
        Comparator<ArriveEvent> comp = new EventComparator<ArriveEvent>();
        PriorityQueue<Customer> queue = new PriorityQueue<Customer>(numberOfServers,comp);

        while (sc.hasNextDouble()) {
            double time = sc.nextDouble();

            if (customerId == 1) {
                for (int i = 0; i < numOfServers; i++) {
                    Server s = new Server(serverId, true, false, time);
                    serverId++;
                    serverList.add(s);
                }
            }

            Customer c = new Customer(customerId, time);
            for (Server s: serverList) {
                queue.add(new ArriveEvent(c,s);
            }
            customerId++;
        }
    }
}
