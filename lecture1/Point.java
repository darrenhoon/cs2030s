class Point{
    double x;
    double y;

    public boolean equals(Object obj){
        if (this==obj){return true;}
        else if(obj instanceof Point){
            Point p = (Point) obj;
            return (p.x == this.x) && (p.y ==this.y);
        }
        else{return false;}
    }

    Point(double x, double y){
        this.x = x;
        this.y = y;
    }

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

class Circle{
    Point centre;
    private final double radius;

    Circle(Point p, double radius){
        this.centre = p;
        this.radius = radius;
    }

    public String toString(){
        return "This circle has radius " + this.radius + " and point " + this.centre;
    }

    boolean contains(Point q){
        return this.centre.distbet(q)<this.radius;
    }

    double getArea(){
        return Math.PI*this.radius;
    }

    double getPerimeter(){
        return 2*this.radius*Math.PI;
    }

    int countCoverage(Point[] arr){
        int counter = 0;
        for(Point p: arr){
            if(this.contains(p)){
                counter++;
            }
        }
        return counter;
    }

}

