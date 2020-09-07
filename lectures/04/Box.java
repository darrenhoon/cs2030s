class Box<T> {
    private final T obj;
    Box(T obj){
        this.obj = obj;
    }

    static <T> Box<T> of(T t){
        return new Box<T>(t);
    }

    @Override
    public String toString(){
        return "[" + this.obj + "]";
    }

    T get(){
        return this.obj;
    }
}
