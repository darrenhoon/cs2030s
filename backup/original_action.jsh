
UnaryOperator<GameObject> takeSword(GameObject x) {
    if (x instanceof Sword) {
        String msg = "--> You";
        
        if (s.isTaken()) {
            msg = msg + " already have sword.";
        } else {
            msg = msg + " have taken sword.";
        }
        System.out.println(msg);
        Sword ss = new Sword(true);
        return ss;
    } else {
        return x;
    }
}

UnaryOperator<GameObject> killTroll() {

    String DEAD = "--> Troll is killed.";
    String NONE = "--> There is no troll";
    /*
    if (x instance of Troll) {
        String msg;
        if (x.isAlive()) {
            }

    }
    */
    
}

UnaryOperator<GameObject> dropSword(GameObject x) {
    if (x instanceof Sword) {
        String msg = "--> You have dropped sword.";
        
        System.out.println(msg);
        Sword ss = new Sword(false);
        return ss;
    } else {
        return x;
    }
}
