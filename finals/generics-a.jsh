//**** Generics (a)
//**** Write your answer below

void replace(List<T extends Comparable<T>> src, List<T extends
Comparable<T>> dst) {
    if (src.size() == dst.size()) {
        for (int i = 0; i < src.size(); i++) {
            T srcget = src.get(i);
            T dstget = dst.get(i);
            if (srcget.compareTo(dstget) > 0) {
                dst.set(i, src.get(i));
            }
        }
    }
}
