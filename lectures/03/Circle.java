class Circle extends Shape{
    Point centre;
    double radius;

    Circle(Point p, double radius){
        this.centre = p;
        this.radius = radius;
    }

    Circle scaleBy(double factor){
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
