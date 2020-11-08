import java.util.List;
import java.util.ArrayList;

class Sword extends GameObject {
    private final String state;
    private final boolean taken;
    
    public Sword() {
        this.state = "Sword is shimmering.";
        this.taken = false;
    }
    
    public String toString() {
        return this.state;
    }

    public Sword(boolean tru) {
        this.taken = tru;
        this.state = "Sword is shimmering.";
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
}
