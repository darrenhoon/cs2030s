class Point {
    double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

class Circle {
    Point centre;
    public Circle(Point centre) {
        this.centre = centre;
    }
    @Override
    public String toString() {
        return "Circle: " + centre;
    }
}
