


UnaryOperator<List<GameObject>> takeSword = (List<GameObject> list) -> {
    
    if (list.stream().anyMatch(x -> x instanceof Sword) == false) { 
        System.out.println("--> There is no sword.");
        return list;
    }
    
    else if (list.stream().filter(x -> x instanceof Sword)
        .anyMatch(x -> ((Sword) x).isTaken()) == false) { 
        System.out.println("--> You have taken sword.");
        return list.stream()
        .map(x -> {
            if (x instanceof Sword) {
                return (GameObject) new Sword(true);
                } else {
                return x;
                }
        })
        .collect(Collectors.toList());
    }
    
    else {
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

UnaryOperator<List<GameObject>> dropSword = (List<GameObject> list) -> {
    
    List<GameObject> tempList = new ArrayList<GameObject>();
    
    for (GameObject x: list) {
        if (x instanceof Sword && ((Sword) x).isTaken()) {
            System.out.println("--> You have dropped sword.");
            tempList.add((GameObject) new Sword(false));
            } else {
            tempList.add(x);
        }
    }
    return tempList;
};
