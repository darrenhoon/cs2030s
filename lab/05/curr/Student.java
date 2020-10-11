import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Student extends KeyableMap<Module> implements Keyable {

    Student(String name) {
        super(name);
    }

    @Override
    public Student put(Module item) {
        return (Student) super.put(item);
    }
}
