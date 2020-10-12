import java.util.Map;
import java.util.HashMap;

class KeyableMap<V extends Keyable> {
    
    private final String key;

    public Map<String, V> map;
    
    KeyableMap(String key) {
        this.key = key;
        this.map = new HashMap<String, V>();
    }

    @Override
    public String toString() {
        String msg = String.format("%s: {", this.getKey());
        int counter = 0;
        int size = this.map.size();       
        for (Map.Entry<String, V> entry: this.map.entrySet()) {
            String curr = entry.getValue().toString();
            msg += curr;
            if (counter != size-1) {
                msg += ", ";
            }
            counter ++;
        }
        msg += "}";
        return msg;
    }

    KeyableMap<V> put(V a) {
        if (this.map.containsKey(a.getKey())) {
            return this;
        }
        KeyableMap<V> mod = new KeyableMap<V>(this.getKey());
        mod.addItem(a);
        return mod;
    }

    V get(String key) {
        if (this.map.containsKey(key) == false) {
            return null;
        }
        return this.map.get(key);
    }

    public void addItem(V a) {
        this.map.put(a.getKey(), a);
    }

    public String getKey() {
        return this.key;
    }
}
