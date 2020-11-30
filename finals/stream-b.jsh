//**** Streams (b)
//**** Write your answer below
would run into an error because the lambda expression in forEach would expect s to
be effectively final yet the value of x is being edited.

int b = IntStream.iterate(1, x -> x + 1).limit(100).reduce(0 (x,y) -> x + y);
