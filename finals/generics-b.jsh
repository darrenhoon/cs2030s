//**** Generics (b)
//**** Write your answer below

<T,U> void replace(List<T extends Comparable<T>> src, List<U extends
Comparable<U>> dst) {
    if (src.size() == dst.size()) {
        for (int i = 0; i < src.size(); i++) {
            T srcget = src.get(i);
            U dstget = dst.get(i);
            if (srcget.compareTo(dstget) > 0) {
                dst.set(i, src.get(i));
            }
        }
    }
}
