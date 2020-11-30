//**** Lazy Evaluation (c)
//**** Write your answer below

int count() {
    return this.map(x -> 0).reduce(0, (x,y) -> x + 1);
}
