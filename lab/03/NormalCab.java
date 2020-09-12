class NormalCab extends Driver {
    private final String plate;
    private final int time;

    NormalCab(String licensePlate, int waitingTime) {
        this.plate = licensePlate;
        this.time = waitingTime;
    }

    @Override
    public String toString() {
        return String.format("%s (%s mins away) NormalCab",this.plate,this.time);
    }

    int getWaitingTime() {
        return this.time;
    }

    String getLicensePlate() {
        return this.plate;
    }

    double getOneCost(Request r) {
        return (double) (new JustRide().computeFare(r));
    }

    String getOneName() {
        return "JustRide";
    }

    double getTwoCost(Request r) {
        return (double) (new TakeACab().computeFare(r));
    }

    String getTwoName() {
        return "TakeACab";
    }
}       
