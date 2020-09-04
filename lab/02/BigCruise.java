class BigCruise extends Cruise{
    BigCruise(String serial, int arrivalTime, int length, int passengers){
        super(serial, arrivalTime,(int)Math.ceil((double)length/40),(int)Math.ceil((double)passengers/50));
    }
}
