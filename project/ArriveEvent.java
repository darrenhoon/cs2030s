import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class ArriveEvent {
    private final Customer c;
    private final List serverList;
    private final double serviceTime = 1.0;

    ArriveEvent(Customer customer, List servers) {
        this.serverList = servers;
        this.c = customer;
    }

    public String toString() {
        String message = String.format("%.3f %d ",this.c.getArrivalTime(),this.c.getId());
        if (c.hasLeft()) {
            message += "leaves";
            return message;
        }

        for (int i = 0; i<serverList.size() ; i++) {
            Server current = serverList.get(i);
            if (current.canQueue() == false) {
                return message + String.format("served by %d", current.getId());
            }
        }

        message += "arrives";
        return message;
    }

    List getList() {
        return this.list;
    }

    ArriveEvent execute() {
        List tempList = this.getList();
        boolean customerLeft = true;

        if (c.isBeingServed()) {
            c.
        }

        for (Server s: tempList) {
            if (s.canServe()) {
                s.serveCustomer(this.c);
                customerLeft = false;
                break;
            }
            else {
                if (s.canQueue()) {
                    customerLeft = false;
                    break;
                }
            }
        }

        if (customerLeft) {
            return new ArriveEvent(this.c.leavePlace(), tempList);
        } else {
            return new ArriveEvent(this.c,tempList);
        }
    }
}
