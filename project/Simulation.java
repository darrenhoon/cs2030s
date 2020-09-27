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
        this.queue = new PriorityQueue<Event>(eventComp);

        for (Customer c: this.customerList) {
            this.queue.add(new ArriveEvent(c,this.serverList));
        }
    }

    void simulate() {

        while (this.queue.peek() != null) {

            Event event = this.queue.poll();
            System.out.println(event.toString());

            List<Server> listCopy = new ArrayList();
            listCopy.addAll(this.serverList);
            Customer currentCustomer = event.getCustomer();

            if (event instanceof LeaveEvent) {
                customersLeft++;
                continue;
            }

            if (event instanceof DoneEvent) {
                customersServed++;
                DoneEvent currEvent = (DoneEvent) event;

                double customerArrivalTime = currEvent.getCustomer().getArrivalTime(); 
                double time = event.getCurrentTime();
                double waitTime = time - customerArrivalTime;
                totalWaitTime += waitTime;

                Server s = currEvent.getServer();

                Server newServer;
                if (s.canQueue() == false) { //ie there is a customer that was queuing up
                    newServer = new Server(s.getId(), false, false, time);
                } else {
                    newServer = new Server(s.getId(), true, false, time);
                }

                for (int i = 0;i < this.serverList.size(); i++) {
                    Server current = this.serverList.get(i);
                    if (current.getId() == newServer.getId()) {
                        this.serverList.set(i, newServer);
                        break;
                    }
                }
                continue;
            } else {
                boolean customerWait = true;
                
                for (int i = 0; i < this.serverList.size(); i++) {
                    Server currentServer = this.serverList.get(i);
                    if (currentServer.getAvailability()) {
                        Server newServer = new Server(currentServer.getId(), false, false, currentServer.getAvailableTime());
                        this.serverList.set(i, newServer);
                        customerWait = false;
                        break;
                    }
                }

                if(customerWait) {
                    for (int i = 0; i < this.serverList.size(); i++) {
                        Server currentServer = this.serverList.get(i);
                        if (currentServer.canQueue()) {
                            Server newServer = new Server(currentServer.getId(), false, true, currentServer.getAvailableTime());
                            this.serverList.set(i, newServer);
                            break;
                        }
                    }
                }
                event = event.execute();
                this.queue.add(event);
 
            }
      }
       double averageTime = totalWaitTime / customersServed;
       String message = String.format("[%.3f %d %d]", averageTime, customersServed, customersLeft);
       System.out.println(message);
 
    }
}
