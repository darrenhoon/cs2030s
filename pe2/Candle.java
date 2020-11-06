class Candle extends GameObject {
    private final String state;
    public Candle() {
        this.state = "Candle flickers";
    }

    private Candle(String state) {
        this.state = state;
    }

    public String toString() {
        return this.state;
    }

    public GameObject changeState() {
        if  (this.state.equals("Candle flickers.")) {
            Candle c = new Candle("Candle is getting shorter.");
            return (GameObject) c;
        }
        
        if  (this.state.equals("Candle is getting shorter.")) {
            Candle c =  new Candle("Candle is about to burn out.");
            return (GameObject) c;
        }

        if  (this.state.equals("Candle is about to burn out.")) {
            Candle c = new Candle("Candle has burned out.");
            return (GameObject) c;
        }
        
        Candle c =  new Candle("Candle has burned out.");
        return (GameObject) c;
    }
}
