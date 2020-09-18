import java.util.List;

class ArriveEvent {
    private final Customer c;
    private final List list;
    private final double serviceTime = 1.0;

    ArriveEvent(Customer customer, List list) {
        this.list = list;
        this.c = customer;
    }

    public String toString() {
        String message = String.format("%.3f %d ",this.c.getArrivalTime(),this.c.getId());
        if(c.hasLeft()) {
            message += "leaves";
        } else {
            message += "arrives";
        }
        return message;
    }

    List getList() {
        return this.list;
    }

    ArriveEvent execute() {
        List tempList = this.getList();
        boolean customerLeft = true;

        for(Server s: tempList) {
            if(s.canServe()) {
                s.serveCustomer(this.c);
                customerLeft = false;
                break;
            }
            else {
                if(s.canQueue) {
                    s.addQueue(this.c);
                    customerLeft = false;
                    break;
                }
            }
        }

        if(customerLeft) {
            return new ArriveEvent(c.leavePlace(), tempList());
        } else {
            return new ArriveEvent(c,this.tempList() );
        }
    }
}
