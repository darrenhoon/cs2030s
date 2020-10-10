import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Student implements KeyableMap {
    private final String name;
    private final Map<String, Assessment> map;

    Student(String name) {
        this.name = name;
        this.map = new HashMap<String, Assessment>();
    }
    public Student put(Module mod) {

    }
}
