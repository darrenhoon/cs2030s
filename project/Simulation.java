import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Simulation {

    private List<Server> serverList;
    private final List<Customer> customerList;
    private Queue<Event> queue;
    private Queue<Customer> customersOrder;
    private double totalWaitTime = 0.00;
    private int customersServed = 0;
    private int customersLeft = 0;

    Simulation(List<Customer> customerList, List<Server> serverList) {
        this.customerList = customerList; 
        this.serverList = serverList;

        EventComparator eventComp = new EventComparator();
        CustomerComparator customerComp = new CustomerComparator();

        //sort all customers according to time they arrive
        this.customersOrder = new PriorityQueue<Customer>(this.customerList.size(), customerComp);
        this.queue = new PriorityQueue<Event>(eventComp);
    }

    void simulate() {
        while (this.customersOrder.peek() != null) {

            boolean customerWait = true;
            boolean customerLeave = true;

            List<Server> currentServers = new ArrayList(this.serverList);
            Customer currentCustomer = this.customersOrder.poll();

            //directly serve customer
            for (int i = 0; i < currentServers.size(); i++) {
                Server s = currentServers.get(i);
                if (s.getAvailability()) {
                    Server ns = new Server(s.getId(), false, false, s.getAvailableTime());
                    this.serverList.set(i,ns);
                    customerWait = false;
                    customersServed++;
                    break;
                }
            }

            //customer has to wait
            if (customerWait) {

                for (int i = 0; i < currentServers.size(); i++) {
                    Server s = currentServers.get(i);
                    if (s.canQueue()) {
                        Server ns = new Server(s.getId(), false, true, s.getAvailableTime());
                        this.serverList.set(i,ns);
                        customerLeave = false;
                        customersServed++;
                        break;
                    }
                }
            }

            //all servers have waiting customers, new customer leaves
            if (customerLeave) {
                customersLeft++;
            }

            Event event = new ArriveEvent(currentCustomer, currentServers);
           
            // keep executing event until it reaches DoneEvent
            while (!(event instanceof DoneEvent) && !(event instanceof LeaveEvent)) {
                System.out.println(event.execute());
                event.execute();
            }

            if (event instanceof DoneEvent) {
                DoneEvent event1 = (DoneEvent) event;

                int id = event1.getServer().getId();

                double endTime = event1.getCurrentTime();
                
                customersServed++;
                double waitTime = endTime - currentCustomer.getArrivalTime();
                totalWaitTime += waitTime;

                //to replace the old server done serving
                Server newServer;
                if (event1.getServer().canQueue() == false) {
                    newServer = new Server(id, false, false, endTime);
                } else {
                    newServer = new Server(id, true, false, endTime);
                }

                for (Server s: this.serverList) {
                    int currentId = s.getId();
                    int index = currentId - 1;
                    if (currentId == id) {
                        serverList.set(index, newServer);
                        break;
                    }
                }
            }
            queue.add(event);
        }

        double averageTime = totalWaitTime / customersServed;
        String message = String.format("[%.3f %d %d]", averageTime, customersServed, customersLeft);
        System.out.println(message);
    }
}
