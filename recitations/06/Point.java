public class Point {
    private final double x;
    private final double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    double getY() {
        return this.y;
    }
    double getX() {
        return this.x;
    }
    public Point add(Point q) {
        return new Point(this.x + q.getX(), this.y + q.getY());
    }
    public Point scale(double k) {
        return new Point(this.x*k, this.y*k);
    }
    public double distanceTo(Point q) {
        return Math.sqrt(sumOfSquares(this.x - q.x, this.y - q.y));
    }
    public static double sumOfSquares(double a, double b) {
        return a*a + b*b;
    }
}
