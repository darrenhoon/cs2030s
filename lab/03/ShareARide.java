class ShareARide extends JustRide {
    private final int rateShare = 50;
    
    @Override
    public String toString() {
        return "ShareARide";
    }

    @Override
    int computeFare(Request r) {
        double dist = r.getDist();
        double time = r.getTime();
        double cost = dist * rateShare;
        if ((this.getLowerBound() <= time) && (time <= this.getUpperBound())) {
            cost = cost + 500.0;
        }
        double perPaxFare = cost / r.getPassengers();
        return (int) perPaxFare;
    }
}
