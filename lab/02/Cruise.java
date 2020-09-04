class Cruise {
    private final String identifier;
    private final int arrivalTime;
    private final int numOfLoader;
    private final int serviceTime;

    Cruise(String identifier,int arrivalTime,int numOfLoader, int serviceTime){
        this.arrivalTime = arrivalTime;
        this.numOfLoader = numOfLoader;
        this.serviceTime = serviceTime;
        this.identifier = identifier;
    }
    @Override
        public String toString(){
            String time = Integer.toString(this.arrivalTime);
            while (time.length()<4){
                time = "0"+time;
            }
            String message = String.format("%s@%s",this.identifier,time);
            return message;
        }
    public int calculateTime(int input){
        int hours = input/100;
        int mins = input%100;
        int total = hours*60 + mins;
        return total;
    }
    public String getId(){
        return this.identifier;
    }
    public int getArrivalTime(){
        return this.calculateTime(this.arrivalTime);
    }
    public int getNumOfLoadersRequired(){
        return this.numOfLoader;
    }
    public int getServiceCompletionTime(){
        return this.serviceTime + this.getArrivalTime();
    }
}
