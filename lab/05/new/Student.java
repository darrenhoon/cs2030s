import java.util.Map;
import java.util.HashMap;

public class Student extends KeyableMap<Module> implements Keyable {
    Student(String key) {
        super(key);
    }

    @Override
    Student put(Module m) {
        return (Student) super.put(m);
    }
}
