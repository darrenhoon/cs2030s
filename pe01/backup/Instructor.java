public class Instructor {
    private final String name;
    Instructor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(o instanceof Instructor) {
            Instructor curr = (Instructor) o;
            String insName = curr.getName();
            if (insName == this.name) {
                return true;
            }
        }
        return false;
    }

    String getName() {
        return this.name;
    }
}
