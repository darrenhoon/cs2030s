import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.function.Function;

class Room {

    private final List<GameObject> list;
    private final String loc;

    public Room(String loc) {
        this.loc = loc;
        this.list = new ArrayList<GameObject>();
    }

    public Room(String loc, List<GameObject> list) {
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

    public Room tick(UnaryOperator<List<GameObject>> f) {
        List<GameObject> tempList = new ArrayList<GameObject>();
        
        for (GameObject o: f.apply(this.list)) {
            tempList.add(o.changeState());
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
        List<GameObject> tempList = new ArrayList<GameObject>(this.list);
        tempList.add(o);
        return new Room(this.loc, tempList);
    }
    void editSword(boolean bool, GameObject sword) {
        if (bool) {
            this.list.add(0, sword);
        } else {
            this.list.remove(sword);
        }
    }

    public Room go(Function<List<GameObject>, Room> f) {
        List<GameObject> bringOverList = new ArrayList<GameObject>(this.list);
        Room nextRoom = f.apply(bringOverList);
        for(GameObject o: this.list) {
            if (o instanceof Sword && (((Sword) o).isTaken() == true)) {
                ((Sword) o).rememberRoom(this);
                nextRoom.editSword(true, (GameObject) o);
                this.editSword(false, (GameObject) o);
                break;
            }
        }
        return nextRoom;
    }

    public Room back() {
        for (GameObject o: this.list) {
            if (o instanceof Sword) {
                return ((Sword) o).previousRoom();
            }
        }
        return this;
    }
}
