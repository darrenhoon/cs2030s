IntStream.rangeClosed(1, 10).parallel().
filter( x-> {
    //System.out.println("Filter: " + x + " " + Thread.currentThread().
    //getName());
    return x%2==0;
    }).
    boxed().
    reduce(0,
    (x,y) -> {
        System.out.println("Accumulate: " + x + " " + y + " " +
        Thread.currentThread().getName());
        return x+y;
    },
    (x, y) -> {
        System.out.println("Combine: " + x + " " + y+ " " +
        Thread.currentThread().getName());
        return x+y;
    })
