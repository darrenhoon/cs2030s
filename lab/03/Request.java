class Request {
    private final int d;
    private final int p;
    private final int t;
    
    Request(int dist, int passengers, int time) {
        this.d = dist;
        this.p = passengers;
        this.t = time;
    }

    @Override
    public String toString() {
        return String.format("%skm for %spax @ %shrs",this.d,this.p,this.t);
    }

    int getDist() {
        return this.d;
    }

    int getPassengers() {
        return this.p;
    }

    int getTime() {
        return this.t;
    }
}
