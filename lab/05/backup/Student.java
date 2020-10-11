import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Student extends KeyableMap {

    Student(String name) {
        super(name);
    }

    @Override
    public Student put(Module item) {
        return (Student) super.put(item);
    }
}
