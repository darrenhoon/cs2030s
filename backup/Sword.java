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

    private Sword(boolean tru) {
        this.taken = true;
        this.state = "Sword is shimmering.";
    }
    public GameObject changeState() {
        Sword s = new Sword();
        return (GameObject) s;
    }

    public boolean isTaken() {
        return this.taken;
    }

    public GameObject takenSword() {
        Sword s = new Sword(true);
        return (GameObject) s;
    }
}
