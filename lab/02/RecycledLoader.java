class RecycledLoader extends Loader{
    RecycledLoader(int identifier,Cruise cruise){
        super(identifier,cruise);
    }

    @Override
    public String toString(){
        String message = "Recycled " + super.toString();
        return message;
    }

    @Override
    public int getNextAvailableTime(){
        return super.getNextAvailableTime()+ 60;
    }

    @Override
    public Loader serve(Cruise cruise){
        if(this.canServe(cruise)){
            return new RecycledLoader(this.getIdentifier(),cruise);
        }
        return this;
    }
}
