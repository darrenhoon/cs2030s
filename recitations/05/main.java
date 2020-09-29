public class Point {
    private final int x;
    private final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Point) {
            Point point = (Point) o;
            if (point.toString() == this.toString()) {
                return true;
            }
        }
        return false;
}


