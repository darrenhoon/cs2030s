class Vector{
    private double[] coord2D;

    Vector(double x,double y){
        this.coord2D = new double[]{x,y};
    }

    public String declaration(){

        return "this class's x and y coords are: " + this.coord2D[0] + " and " + this.coord2D[1];
    }

    public String toString(){
        return this.declaration();}

    double getX(){return this.coord2D[0];}
    double getY(){return this.coord2D[1];}

    String add(Vector v){
        this.coord2D[0]=this.coord2D[0]+v.getX();
        this.coord2D[1]=this.coord2D[1]+v.getY();
        return this.declaration();
    }
}

