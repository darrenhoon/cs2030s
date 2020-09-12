class TakeACab extends JustRide {
    private final int rateCab = 33;

    @Override
    int computeFare(Request r) {
        double dist = r.getDist();
        double time = r.getTime();
        double cost = (dist * rateCab) + super.getBookingFee();
        return (int) cost;
    }

    @Override
    public String toString() {
        return "TakeACab";
    }
}
