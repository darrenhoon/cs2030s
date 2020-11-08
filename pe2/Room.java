import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Optional;

class Room {

    private final List<GameObject> list;
    private final String loc;
    private final List<Room> prevRoom = new ArrayList<Room>();

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
        Room nextRoom = new Room(this.loc, tempList);
        if (this.prevRoom.isEmpty() == false) {
            nextRoom.addRoom(this.prevRoom.get(0));
        }
        return nextRoom;
    }

    public Room tick(UnaryOperator<List<GameObject>> f) {
        List<GameObject> tempList = new ArrayList<GameObject>();

        for (GameObject o: f.apply(this.list)) {
            tempList.add(o.changeState());
        }
        Room nextRoom = new Room(this.loc, tempList);
        if (this.prevRoom.isEmpty() == false) {
            nextRoom.addRoom(this.prevRoom.get(0));
        }
        return nextRoom;
    }

    public String toString() {
        String msg = "@" + this.loc;
        for (GameObject o: this.list) {
            msg = msg + "\n" + o.toString();
        }
        return msg;
    }

    public Room add(GameObject o) {
        if (this.list.contains(o)) {
            return this;
        }
        List<GameObject> tempList = new ArrayList<GameObject>(this.list);
        tempList.add(o);
        return new Room(this.loc, tempList);
    }

    void editSword(boolean bool, GameObject sword) {
        if (bool) {
            this.list.add(0, sword);
        } else {
            this.list.add(sword);
        }
    }

    void addRoom(Room rm) {
        this.prevRoom.add(rm);
    }

    public Room go(Function<List<GameObject>, Room> f) {
        
        List<GameObject> listWOsword = new ArrayList<GameObject>();
        Room nextRoom = f.apply(this.list);
        
        for(GameObject o: this.list) {
            if (o instanceof Sword && (((Sword) o).isTaken() == true)) {
                nextRoom.editSword(true, o);
            } else {
                listWOsword.add(o);
            }
        }
        Room previousRoom = new Room(this.loc, listWOsword);
        nextRoom.addRoom(previousRoom);
        return nextRoom;
    }

    public Room back() {
        if (this.prevRoom.isEmpty()) {
            return this;
        }
        Room nextRoom = this.prevRoom.get(0);
        
        for(GameObject o: this.list) {
            if (o instanceof Sword && (((Sword) o).isTaken() == true)) {
                nextRoom.editSword(false, o);
            }
        }
        return nextRoom.tick();
    }
}
