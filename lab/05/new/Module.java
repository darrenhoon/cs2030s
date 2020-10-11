import java.util.Map;
import java.util.HashMap;

class Module implements Keyable {
    private final String key;

    private Map<String,Assessment> map;
    
    Module(String key) {
        this.key = key;
        this.map = new HashMap<String,Assessment>();
    }

    @Override
    public String toString() {
        String msg = String.format("%s: {", this.getKey());
        int counter = 0;
        int size = this.map.size();       
        for (Map.Entry<String, Assessment> entry: this.map.entrySet()) {
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

    Module put(Assessment a) {
        if (this.map.containsKey(a.getKey())) {
            return this;
        }
        Module mod = new Module(this.getKey());
        mod.addItem(a);
        return mod;
    }

    Assessment get(String key) {
        if (this.map.containsKey(a.getKey() == false)) {
            return null;
        }
        return this.map.get(a.getKey());
    }

    public void addItem(Assessment a) {
        this.map.put(a.getKey(), a);
    }

    public String getKey() {
        return this.key;
    }
}
