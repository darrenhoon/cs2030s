import java.util.List;
import java.util.ArrayList;

class Sword extends GameObject {
    private final String state;
    private final boolean taken;
    private final List<Room> roomList;

    public Sword() {
        this.state = "Sword is shimmering.";
        this.taken = false;
        this.roomList = new ArrayList<Room>();
    }
    public String toString() {
        return this.state;
    }

    public Sword(boolean tru) {
        this.taken = tru;
        this.state = "Sword is shimmering.";
        this.roomList = new ArrayList<Room>();
    }
    public GameObject changeState() {
        Sword s = new Sword(this.taken);
        return (GameObject) s;
    }

    public boolean isTaken() {
        return this.taken == true;
    }

    public GameObject takenSword() {
        Sword s = new Sword(true);
        return (GameObject) s;
    }

    public void rememberRoom(Room rm) {
        this.roomList.add(rm);
        System.out.println("ReMeMbered Rooms: " + this.roomList);
    }

    public Room previousRoom() {
        Room lastRoom = this.roomList.remove(this.roomList.size() - 1);
        return lastRoom;
    }
}
