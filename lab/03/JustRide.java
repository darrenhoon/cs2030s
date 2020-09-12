class JustRide {
    private final int upperBound = 900;
    private final int lowerBound = 600;
    private final int surcharge = 500;
    private final int bookingFee = 200;
    private final int rateJust = 22;
    
    int computeFare(Request r) {
        double dist = r.getDist();
        double time = r.getTime();
        double cost = dist * rateJust;
        if ((this.lowerBound <= time) && (time <= this.upperBound)) {
            cost = cost + 500.0;
        }
        return (int) cost;
    }

    int getBookingFee() {
        return bookingFee;
    }

    int getLowerBound() {
        return lowerBound;
    }

    int getUpperBound() {
        return upperBound;
    }
    
    @Override
    public String toString() {
        return "JustRide";
    }
}
