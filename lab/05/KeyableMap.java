import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract class KeyableMap<V extends Keyable> {

    private final String key;
    private final Map<String, V> map;

    KeyableMap(String key) {
        this.key = key;
        this.map = new HashMap<String, V>();
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public String toString() {
        String msg = String.format("%s: {", this.getKey());
        int counter = 0;
        int size = this.map.size();       
        for (Map.Entry<String, V> entry: this.map.entrySet()) {
            String curr = entry.getValue().toString();
            msg += curr;
            if (counter != size - 1) {
                msg += ", ";
            }
            counter++;
        }
        msg += "}";
        return msg;
    }

    public Map<String, V> getMap() {
        return this.map;
    }

    public KeyableMap<V> put(V item) {
        String key = item.getKey();
        if (this.map.containsKey(key)) {
            return this;
        }
        this.map.put(item.getKey(), item);
        return this;
    }

    public void addItem(String key, V item) {
        this.map.put(key, item);
    }

    public Optional<V> get(String name) {
        if (this.map.containsKey(name) == false) {
            return Optional.empty();
        }
        Optional<V> o = Optional.of(this.map.get(name));
        return o;
    }
}
