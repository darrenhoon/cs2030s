import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class KeyableMap<String, V> implements Keyable {

    private final String mod;
    private Map<String, V> map;

    KeyableMap(String mod) {
        this.mod = mod;
        this.map = new HashMap<String, V>();
    }

    public String getKey() {
        return this.mod;
    }

    @Override
    public String toString() {
        String msg = String.format("%s: {", this.mod);
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

    public Map<String, V> getMap() {
        return this.map;
    }

    public KeyableMap put(Object o) {
        if (o instanceof V == false) {
            return this;
        }
        V ass = (V) o;
        String key = ass.getKey();
        if (this.map.containsKey(key)) {
            return this;
        }
        
        Map<String, ? extends Keyable> tempMap = new HashMap<String, ? extends Keyable>();
        Module nextMod = new Module(this.getMod());
        
        for (Map.Entry<String, V> entry: this.map.entrySet()) {
            String currKey = entry.getKey();
            V currAss = entry.getValue();
            tempMap.put(currKey, currAss);
        }
        tempMap.put(ass.getKey(), ass);
        nextMod.addMap(tempMap);
        return nextMod;
    }

    public Assessment get(String name) {
        if (this.map.containsKey(name) == false) {
            return null;
        }
        return this.map.get(name);
    }
    
    public void addMap(Map<String, ? extends Keyable> map) {
        for (Map.Entry<String, V> entry: map.entrySet()) {
            String currKey = entry.getKey();
            Assessment currAss = entry.getValue();
            this.map.put(currKey, currAss);
        }
    }
    public String getMod() {
        return this.mod;
    }
}
