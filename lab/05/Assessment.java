import java.util.Map;

public class Assessment implements Keyable {

    private final String key;
    private final String grade;

    Assessment(String test, String grade) {
        this.key = test;
        this.grade = grade;;;
    }

    @Override
    public String toString() {
        return String.format("{%s: %s}", this.key, this.grade);
    }
    public String getKey() {
        return this.key;
    }
    public String getGrade() {
        return this.grade;
    }
}
