import java.util.List;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;


class ImmutableList<T> {
    private final List<T> list;

    @SafeVarargs
    ImmutableList(T ... args) {        
        List<T> tempList = new ArrayList<T>(Arrays.asList(args));
        this.list = tempList;
    }

    ImmutableList(List<T> args) {
        List<T> tempList = new ArrayList<T>();
        tempList.addAll(args);
        this.list = tempList;
    }

    @Override
    public String toString() {
        String message = "[";
        int i =0;
        if(this.getLength() == 0) {
            return "[]";
        }

        while(i<this.getLength()-1) {
            message += this.getItem(i) + ", ";
            i++;
        }
        message += this.getItem(i) +"]";
        return message;
    }

    ImmutableList<T> add(T item) {
        List<T> tempList = new ArrayList<T>();
        tempList.addAll(this.list);
        tempList.add(item);
        return new ImmutableList<T>(tempList);
    }

    ImmutableList<T> remove(T item) {
        List<T> tempList = new ArrayList<T>();
        boolean flag = true;
        for(T i: this.list) {
            if(i == item && flag) {
                flag = false;
                continue;
            } else {
                tempList.add(i);
            }
        }
        return new ImmutableList<T>(tempList);
    }

    ImmutableList<T> replace(T item, T new_item) {
        List<T> tempList = new ArrayList<T>();
        for (T i: this.list) {
            if(i == item) {
                tempList.add(new_item);
            } else {
                tempList.add(i);
            }
        }
        return new ImmutableList<T>(tempList);
    }

    int getLength() {
        int i =  0;
        for(T item: this.list) {
            i++;
        }
        return i;
    }

    ImmutableList<T> limit(long length) {
        List<T> tempList = new ArrayList<T>();
        if (length < 0) {
            throw new IllegalArgumentException("limit size < 0");
        }
        long i = 0;
        while(i<this.getLength() && i<length) {
            tempList.add(this.list.get((int) i));
            i++;
        }
        return new ImmutableList<T>(tempList);
    }

    T getItem(int i) {
        return this.list.get(i);
    }

    boolean equals(ImmutableList<T> list) {
        if (list == this) {
            return true;
        }
        else {
            int i = 0;
            int listLength = list.getLength();
            int thisLength = this.getLength();
            if(listLength != thisLength) {
                return false;
            }

            while(i<listLength) {
                if (this.getItem(i) != list.getItem(i) ) {
                    return false;
                }
                i++;
            }
            return true;
        }
    }

    ImmutableList<T> sorted(Comparator<T> comp) {
        if(comp == null) {
            throw new NullPointerException("Comparator is null");
        }

        List<T> tempList = new ArrayList<T>();
        List<T> referenceList = new ArrayList<T>();
        referenceList.addAll(this.list);

        while(referenceList.size() != 0) {
            T ref = referenceList.get(0);

            for(int j=0;j<referenceList.size();j++) {
                T current = referenceList.get(j);
                if(comp.compare(current,ref) < 0) {
                    ref = current;
                }
            }
            tempList.add(ref);
            referenceList.remove(ref);
        }
        return new ImmutableList<T>(tempList);
    }

    Object[] toArray() {
        List<T> tempList = new ArrayList<T>();
        tempList.addAll(this.list);
        return tempList.toArray();
    }

    <T extends Object> T[] toArray(T arr[]) {
        if(arr == null) {
            throw new NullPointerException("Input array cannot be null");
        }
        try {
            T[] tempArray = this.list.toArray(arr);
            return tempArray;
        } catch(ArrayStoreException e) {
            throw new ArrayStoreException("Cannot add element to array as it is the wrong type");
        }
    }
}
