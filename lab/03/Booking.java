class Booking {
    private final int larger = 1;
    private final int equal = 0;
    private final int smaller = -1;
    private final Request request;
    private final Driver cab;
    private final double fare;
    private final int dollarConversion = 100;
    private final String message;

    Booking(Driver cab, Request r) {
        this.cab = cab;
        this.request = r;
        Driver c = this.cab;
        double a = c.getOneCost(this.request) / dollarConversion;
        double b = c.getTwoCost(this.request) / dollarConversion;
        if (a < b) {
            this.fare = a;
            this.message = String.format("$%.2f using %s (%s)",a,c,c.getOneName());
        } else {
            this.fare = b;
            this.message = String.format("$%.2f using %s (%s)",b,c,c.getTwoName());
        }
    }

    @Override
    public String toString() {
        return this.message;
    }

    Request getRequest() {
        return this.request;
    }

    double getFare() {
        return this.fare;
    }

    int compareTo(Booking b) {
        if (this.getFare() < b.getFare()) {
            return smaller;
        }
        if (this.getFare() > b.getFare()) {
            return larger;
        } else {
            if (this.cab.getWaitingTime() < b.getCab().getWaitingTime()) {
                return smaller;
            }
            if (this.cab.getWaitingTime() > b.getCab().getWaitingTime()) {
                return larger;
            } else {
                return equal;
            }
        }
    }
    
    Driver getCab() {
        return this.cab;
    }
}
