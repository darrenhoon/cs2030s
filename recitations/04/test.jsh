/open NumberComparator.java
/open Box.java
/open StringComparator.java

<T> T max3a(T a, T b, T c, Comparator<T> comp) {
    T max = a;
    if (comp.compare(b, max) > 0) {
        max = b;
    }
    if (comp.compare(c, max) > 0) {
        max = c;
    }
    return max;
}

<T> Comparable<T> max3di(Comparable<T> a, Comparable<T> b, Comparable<T> c) {
    Comparable<T> max = a;
    if (b.compareTo((T) max) > 0) {
        max = b;
    }
    if (c.compareTo((T) max) > 0) {
        max = c;
    }
    return max;
}

<T> T max3dii(Comparable<T> a, Comparable<T> b, Comparable<T> c) {
    T max = (T) a;
    if (b.compareTo(max) > 0) {
        max = (T) b;
    }
    if (c.compareTo(max) > 0) {
        max = (T) c;
    }
    return max;
}

// to show type erasure. not recommended.
Comparable max3diii(Comparable a, Comparable b, Comparable c) {
    Comparable max = a;
    if (b.compareTo(max) > 0) {
        max = b;
    }
    if (c.compareTo(max) > 0) {
        max = c;
    }
    return max;
}


/*
max3String("hello","good","bye", new Comparator<String>()) {
    public int compare(String x,String y) {
        return x.compareTo(y);
    }
}

*/
