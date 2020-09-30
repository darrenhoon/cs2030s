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

    @Override
    public int hashCode() { //will not work if the x and y are negative values
        return power(2,x)*(3,y);
    }

    private static int power(int a, int b) {
        if (b == 0) {
            return 1;
        } else {
            return a*power(b-1);
        }
    }
    
    public int hashCode() {
        int[] = intArray = {this.x,this.y};
        return Arrays,hashCode(intArray);
    }
}


