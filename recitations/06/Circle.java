public class Circle {
    private final Point centre;
    private final double radius;
    public Circle(Point c, double r) {
        this.centre = c;
        this.radius = r;
    }
    public Circle add(Circle other) {
        Point newCentre = this.getCentre().add(other.getCentre());
        double newRadius = this.getRadius() + other.getRadius();
        return new Circle(newCentre, newRadius);
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
