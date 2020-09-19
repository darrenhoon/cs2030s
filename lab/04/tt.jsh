/open ImmutableList.java

ImmutableList<Integer> list = new ImmutableList<Integer>(1,2,3)

list.sorted(new Comparator<Integer>() {
    public int compare(Integer i1, Integer i2) {
        return i2 - i1;
    }})

