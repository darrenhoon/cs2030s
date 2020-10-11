import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Module<V extends Keyable> extends KeyableMap {

    Module(String mod) {
        super(mod);
    }

    @Override
    public Module<V> put(V item) {
        return (Module) super.put(item);
    }
}

/*
 *     for (Map.Entry<Key,Value> entry : set.entrySet()) {
 *             entry.getKey()
 *                     entry.getValue()
 *                         }
 *                         */
