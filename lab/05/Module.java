import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Module {

    private final String mod;
    private Map<String, Assessment> map;

    Module(String mod) {
        this.mod = mod;
        this.map = new HashMap<String, Assessment>();
    }

    public String getKey() {
        return this.mod;
    }

    @Override
    public String toString() {
        String msg = String.format("%d: ",this.mod);
        for (Map.Entry<Key,Value> entry : set.entrySet()) {
            entry.getKey();
            entry.getValue();
        }
    }       
        return msg;
    }

    public HashMap<String, Assessment> getMap() {
        return this.map;
    }

    public Module add(Assessment ass) {
        /*
        Map<String, Assessment> map = new HashMap<String, Assessment>();
        */
        String key = ass.getKey();
        if (this.set.containsKey(key)) {
            return this;
        }
        this.set.put(key, ass);
        return this;
    }
}

/*
    for (Map.Entry<Key,Value> entry : set.entrySet()) {
        entry.getKey()
        entry.getValue()
    }
*/
