import java.util.ArrayList;

class Loader{
    private final int identifier;
    private final Cruise cruise;

    Loader(int identifier, Cruise cruise){
        this.identifier = identifier;
        this.cruise = cruise;
    }

    @Override
    public String toString(){
            String message = String.format("Loader %s serving %s",this.identifier,this.cruise);
            return message;
        }
    public int getNextAvailableTime(){
        return this.cruise.getServiceCompletionTime();
    }
    public boolean canServe(Cruise ship){
        /*
        int current_start = this.cruise.getArrivalTime();
        int current_finish = this.cruise.getServiceCompletionTime();
        if((current_start <= ship.getArrivalTime()) && (ship.getArrivalTime() < current_finish)){
            return false;
        }
        return true;
        */
        return ship.getArrivalTime() >= this.getNextAvailableTime();
    }
    public int getIdentifier(){
        return this.identifier;
    }
    public Loader serve(Cruise cruise){
        if(this.canServe(cruise)){
            return new Loader(this.identifier,cruise);
        }
        return this;
    }
}


