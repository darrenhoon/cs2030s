import java.util.List;
import java.util.ArrayList;

class Schedule {

    private final List<Lecture> classesList;

    Schedule() {
        this.classesList = new ArrayList<Lecture>();
    }

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

    //added 6 lines
    Schedule addClass(List<Lecture> list, Lecture c) {
        this.classesList.addAll(list);
        this.classesList.add(c);
        this.classesList.sort(new ClassComparator());
        return this;
    }

    //all added
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
