import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

class Room {

    private final List<GameObject> list;
    private final String loc;

    public Room(String loc) {
        this.loc = loc;
        this.list = new ArrayList<GameObject>();
    }

    private Room(String loc, List<GameObject> list) {
        this.loc = loc;
        this.list = list;
    }

    public Room tick() {
        List<GameObject> tempList = new ArrayList<GameObject>();
        for (GameObject o: this.list) {
            tempList.add(o.changeState());
        }
        return new Room(this.loc, tempList);
    }

    public Room tick(UnaryOperator<GameObject> f) {
        List<GameObject> tempList = new ArrayList<GameObject>();
        for (GameObject o: this.list) {
            tempList.add(f.apply(o.changeState()));
        }
        return new Room(this.loc, tempList);

    }
    public String toString() {
        String msg = "@" + this.loc;
        for (GameObject o: this.list) {
            msg = msg + "\n" + o.toString();
        }
        return msg;
    }

    public Room add(GameObject o) {
        this.list.add(o);
        return new Room(this.loc, this.list);
    }
}
