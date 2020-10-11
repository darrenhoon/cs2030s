import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Module extends KeyableMap<Assessment> implements Keyable {

    Module(String mod) {
        super(mod);
    }

    @Override
    public Module put(Assessment item) {
        return (Module) super.put(item);
    }
}

/*
 *     for (Map.Entry<Key,Value> entry : set.entrySet()) {
 *             entry.getKey()
 *                     entry.getValue()
 *                         }
 *                         */
