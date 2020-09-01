class Point{
    double x;
    double y;

    /*
       @Override
       public boolean equals(Object obj){
       if (this==obj){return true;}
       else if(obj instanceof Point){
       Point p = (Point) obj;
       return (p.x == this.x) && (p.y ==this.y);
       }
       else{return false;}
       }
       */
    public boolean equals(Point p){
        System.out.println("equals (Point)");
        return p.x==this.x && p.y==this.y;
    }

    Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "Point is (" + this.x + ", " + this.y + ")"; //(1.00, 2.000)
    }

    double distbet(Point q){
        double dx = this.x - q.x;
        double dy = this.y - q.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    Point setX(double xval){
        return new Point(xval,this.y);
    }

    Point setY(double yval){
        return new Point(this.x,yval);
    }
}
