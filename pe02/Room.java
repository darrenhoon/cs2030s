import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Optional;

class Room {

    private final List<GameObject> list;
    private final String loc;
    private final List<Room> prevRoom;

    public Room(String loc) {
        this.loc = loc;
        this.list = new ArrayList<GameObject>();
        this.prevRoom = new ArrayList<Room>();
    }

    public Room(String loc, List<GameObject> list) {
        this.loc = loc;
        this.list = list;
        this.prevRoom = new ArrayList<Room>();
    }

    public Room(String loc, List<GameObject> list, List<Room> rmList) {
        this.loc = loc;
        this.list = list;
        this.prevRoom = rmList;
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
        return new Room(this.loc, tempList, this.prevRoom);
    }

    public Room tick() {
        List<GameObject> tempList = new ArrayList<GameObject>();
        for (GameObject o: this.list) {
            tempList.add(o.changeState());
        }
        return new Room(this.loc, tempList, this.prevRoom);
    }

    public Room tick(UnaryOperator<List<GameObject>> f) {
        List<GameObject> tempList = new ArrayList<GameObject>();
        for (GameObject o: f.apply(this.list)) {
            tempList.add(o.changeState());
        }
        return new Room(this.loc, tempList, this.prevRoom);
    }

    void editSword(boolean flag, GameObject o) {
        if(flag) {
            this.list.add(0, o);
        } else {
            this.list.remove(o);
        }
    }

    void addRoom(Room rm) {
        this.prevRoom.add(rm);
    }

    public Room go(Function<List<GameObject>, Room> f) {
        Room nextRoom = f.apply(this.list);
        for (GameObject o: this.list) {
            if (o instanceof Sword && ((Sword) o).isTaken() == true) {
                nextRoom.editSword(true, o);
                this.editSword(false, o);
                break;
            }
        }
        nextRoom.addRoom(this);
        return nextRoom;
    }

    public Room back() {
        if (this.prevRoom.isEmpty()) {
            return this;
        }
        Room previousRoom = this.prevRoom
            .get(this.prevRoom.size() - 1).tick();

        if (this.list.stream()
                .filter(x -> x instanceof Sword)
                .anyMatch(x -> ((Sword) x).isTaken() == true)) {
            return previousRoom.add(new Sword(true));
                }
        return previousRoom;
    }
}
