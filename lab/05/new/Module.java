import java.util.Map;
import java.util.HashMap;

class Module extends KeyableMap<Assessment> implements Keyable {

    Module(String key) {
        super(key);
    }

    @Override
    Module put(Assessment a) {
        return (Module) super.put(a);
    }
}
