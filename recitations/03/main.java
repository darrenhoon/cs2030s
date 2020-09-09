interface Shape{
    double getArea();
    void foo();
}

interface Printable{
    void print();
    void foo()
}

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

interface PrintableShape extends Printable, Shape{
}

class Circle implements PrintableShape{
    private final Point centre;
    private final double radius;
    public String message = "Circle!";

    Circle(Point p, double radius){
        this.centre = p;
        this.radius = radius;
    }

    public Circle scaleBy(double factor){
        return new Circle(this.centre, this.radius*factor);
    }
    
    @Override
    public void print(){
        System.out.println(this.toString());
    }
    
    @Override
    public String toString(){
        return "This circle has radius " + this.radius + " and point " + this.centre;
    }

    boolean contains(Point q){
        return this.centre.distbet(q)<this.radius;
    }

    @Override
    public double getArea(){
        return Math.PI*this.radius;
    }

    public double getPerimeter(){
        return 2*this.radius*Math.PI;
    }
    
    @Override
    public void foo(){}

    int countCoverage(Circle c, Point[] arr){
        int counter = 0;
        for(Point p: arr){
            if(this.contains(p)){
                counter++;
            }
        }
        return counter;
    }


}

class UnitCircle extends Circle{
    public String message ="UC!";
    UnitCircle(Point centre){
        super(centre, 1.0);
    }
    public UnitCircle scaleBy(double factor){
        return this;
    }

}
