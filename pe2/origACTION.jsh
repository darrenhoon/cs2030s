UnaryOperator<List<GameObject>> takeSword = (List<GameObject> list) -> { 
    /*
    List<GameObject> tempList = new ArrayList<GameObject>();
    for (GameObject x: list) {
        if (x instanceof Sword) {
            String msg = "--> You";
            Sword s = (Sword) x;

            if (s.isTaken()) {
                msg = msg + " already have sword.";
            } else {
                msg = msg + " have taken sword.";
            }
            System.out.println(msg);
            Sword ss = new Sword(true);
            tempList.add((GameObject) ss);
        } else {
            tempList.add(x);
        }
    }
    return tempList.stream().collect(Collectors.toList());
    */
    if (list.stream().filter(x -> x instanceof Sword).count() == 0) { 
        System.out.println("--> There is no sword.");
        return list;
    } else if (list.stream().filter(x -> x instanceof Sword).filter(x -> ((Sword) x).isTaken()).count() == 0) {
        System.out.println("--> You have taken sword.");
        return list.stream()
        .map(x -> {
            if (x instanceof Sword) {
                return (GameObject) new Sword(true);
                }
                return x;
        })
        .collect(Collectors.toList());

    } else {
        System.out.println("--> You already have sword.");
        return list;
    }
};

UnaryOperator<List<GameObject>> killTroll = (List<GameObject> list) -> {
    String DEAD = "--> Troll is killed.";
    String NONE = "--> There is no troll";
    if (list.stream().filter(x -> x instanceof Troll).count() == 0) {
        System.out.println(NONE);
        return list;
    }
    if (list.stream().filter(x -> x instanceof Sword).filter(x -> ((Sword) x).isTaken()).count() == 0) {
        System.out.println("--> You have no sword.");
        return list;
    }
    System.out.println(DEAD);
    return list.stream().filter(x -> !(x instanceof Troll)).collect(Collectors.toList());
};

UnaryOperator<GameObject> dropSword = (GameObject x) -> {
    if (x instanceof Sword) {
        String msg = "--> You have dropped sword.";

        System.out.println(msg);
        Sword ss = new Sword(false);
        return ss;
    } else {
        return x;
    }
};
