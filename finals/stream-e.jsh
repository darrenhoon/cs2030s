//**** Streams (d)
//**** Write your answer below
int count(List<String> words) {
    return words.stream()
    .reduce(0, (x,y) -> x + y.length() ,(x,y) -> x+y)
}

