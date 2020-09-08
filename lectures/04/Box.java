class Box<T> {
    private final T obj;
    Box(T obj){
        this.obj = obj;
    }

    static <WhereDoesThisGo> Box<WhatDoesThisDo> of(WhatIsThis obj){
        return new Box<WhatIsThis2>(obj);
    }

    @Override
    public String toString(){
        return "[" + this.obj + "]";
    }

    T get(){
        return this.obj;
    }
}
