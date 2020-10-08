public class Circle {
    private final Point centre;
    private final double radius;
    public Circle(Point c, double r) {
        this.centre = c;
        this.radius = r;
    }
    public Circle add(Circle other) {
        return new Circle(this.centre.add(other), this.radius + other.radius);
    }

    public Circle scale(double k) {
        return new Circle(this.getCentre().scale(k), this.getRadius()*k);
    }
    double getRadius() {
        return this.radius;
    }
    Point getCentre() {
        return this.centre;
    }
}
