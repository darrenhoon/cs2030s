import java.util.List;
import java.util.ArrayList;

class Schedule {

    private final List<Lecture> classesList;

    Schedule() {
        this.classesList = new ArrayList<Lecture>();
    }

    //added: 2 to 3
    Schedule add(Lecture c) {

        boolean flag = false;

        for (Lecture curr: classesList) {
            if (curr.clashWith(c)) {
                flag = true;
            }
        }
        if (flag) {
            return this;
        }
        else { //2lines deleted, 1 line added
            return new Schedule().addClass(this.classesList, c);
        }
    }

    //added: 2 to 6
    Schedule addClass(List<Lecture> list, Lecture c) {
        this.classesList.addAll(list);
        this.classesList.add(c);
        this.classesList.sort(new ClassComparator());
        return this;
    }

    //added: 5 to 12
    @Override
    public String toString() {
        String message = "";
        for(Lecture l: this.classesList) {
            if (l.getClassType() == "Lecture") {
                message += String.format("%s L%d @ %s [%s] %d--%d\n", l.getMod(), l.getClassId(), l.getVenue(), l.getIns().getName(), l.getStartTime(), l.getEndTime());
            } else { 
                message += String.format("%s T%d @ %s [%s] %d--%d\n", l.getMod(), l.getClassId(), l.getVenue(), l.getIns().getName(), l.getStartTime(), l.getEndTime());
            }
        }
        return message;
    }
}

//total lines edited: 9 to 21
