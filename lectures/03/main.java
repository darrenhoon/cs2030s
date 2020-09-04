interface Scalable{
    Scalable scaleBy(double factor){}
}

abstract class Shape {
    abstract double getArea();
    abstract double getPerimeter();

    public String toString(){
        return "area" + this.area + " perimeter" + this.perimeter;
    }
}

class Rectangle extends Shape implements Scalable{
    Rectangle(double length, double breadth){
        this.length = length;
        this.breadth = breadth;
    }
    @Override
    public Rectangle scaleBy(double factor){
        return new Rectangle(this.length*factor, this.breadth*factor);
    }

    @Override
    public double getArea(){
        double value = this.length * this.breadth;
        return value;
    }

    @Override
    public double getPerimeter(){
        double value = this.length*2 + this.breadth*2;
        return value;
    }
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

class Circle extends Shape{
    Point centre;
    double radius;

    Circle(Point p, double radius){
        this.centre = p;
        this.radius = radius;
    }

    UnitCircle scaleBy(double factor){
        return new Circle(this.centre, this.radius*factor);
    }

    public String toString(){
        return "This circle has radius " + this.radius + " and point " + this.centre;
    }

    boolean contains(Point q){
        return this.centre.distbet(q)<this.radius;
    }

    @Override
    double getArea(){
        return Math.PI*this.radius;
    }

    @Override
    double getPerimeter(){
        return 2*this.radius*Math.PI;
    }

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
    UnitCircle(Point centre){
        super(centre, 1.0);
    }

    UnitCircle scaleBy(double factor){return this;}
}

/*
   class FilledCircle extends Circle{

   private final Color color;

   FilledCircle(double radius, Color color){
   Point origin = new Point(0,0);
   super(origin,radius);
   this.color = color;
   }

   String getColor(){
   return this.color;}

   String setColor(Color newColor){
   this.color = newColor;
   return "Circle's new colour is: " + this.color;
   }
   public String toString(){
   return super.toString() + ", " + this.getColor();
   }
   }
   */
