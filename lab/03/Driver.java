abstract class Driver {
    abstract String getLicensePlate();
    
    abstract double getOneCost(Request r);
    
    abstract String getOneName();
    
    abstract double getTwoCost(Request r);
    
    abstract String getTwoName();
    
    abstract int getWaitingTime();
}
