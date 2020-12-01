class Troll extends GameObject {
    
    private final String state;
    private final boolean isDead;

    public Troll() {
        this.state = "Troll lurks in the shadows.";
        this.isDead = false;
    }
    public Troll(boolean dead) {
        this.isDead = true;
        this.state = "Should be dead so this does not print!!!!";
    }

    public String toString() {
        return this.state;
    }
    private Troll(String state) {
        this.state = state;
        this.isDead = false;
    }
    public GameObject changeState() {
        if  (this.state.equals("Troll lurks in the shadows.")) {
            Troll t = new Troll("Troll is getting hungry.");
            return (GameObject) t;
        }

        if  (this.state.equals("Troll is getting hungry.")) {
            Troll t = new Troll("Troll is VERY hungry.");
            return (GameObject) t;
        }

        if  (this.state.equals("Troll is VERY hungry.")) {
            Troll t =new Troll("Troll is SUPER HUNGRY and is about to ATTACK!");
            return (GameObject) t;
        }

        if  (this.state.equals("Troll is SUPER HUNGRY and is about to ATTACK!")) {
            Troll t = new Troll("Troll attacks!");
            return (GameObject) t;
        }
        Troll t =new Troll("Troll attacks!");
        return (GameObject) t;
    }

    public boolean isDead() {
        return this.isDead;
    }
}
