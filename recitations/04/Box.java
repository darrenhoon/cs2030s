class Box<T> {
    private final T obj;
    Box(T obj){
        this.obj = obj;
    }

    static <T> Box<T> of(T obj){
        return new Box<T>(obj);
    }

    @Override
    public String toString(){
        return "box contents: [" + this.obj + "]";
    }

    T get(){
        return this.obj;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Box) {
            Box<?> anyBox = (Box<?>) obj;
            return this.get().equals(anyBox.get());
        } else {
            return false;
        }
    }
}
