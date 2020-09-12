class PrivateCar extends NormalCab {
    private final String plate;
    private final int time;
    
    PrivateCar(String licensePlate, int waitingTime) {
        super(licensePlate,waitingTime);
        this.plate = licensePlate;
        this.time = waitingTime;
    }

    @Override
    public String toString() {
        return String.format("%s (%s mins away) PrivateCar",this.plate,this.time);
    }

    @Override
    double getTwoCost(Request r) {
        return (double) (new ShareARide().computeFare(r));
    }
    
    @Override
    String getTwoName() {
        return "ShareARide";
    }
}
