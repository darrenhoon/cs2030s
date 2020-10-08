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
        else {
            return new Schedule().addClass(this.classesList, c);
        }
    }
    Schedule addClass(List<Lecture> list, Lecture c) {this.classesList.addAll(list); this.classesList.add(c); this.classesList.sort(new ClassComparator()); return this; }
    public String toString() { String message = ""; for(Lecture l: this.classesList) { if (l.getClassType() == "Lecture") { message += String.format("%s L%d @ %s [%s] %d--%d\n", l.getMod(), l.getClassId(), l.getVenue(), l.getIns().getName(), l.getStartTime(), l.getEndTime());} else { message += String.format("%s T%d @ %s [%s] %d--%d\n", l.getMod(), l.getClassId(), l.getVenue(), l.getIns().getName(), l.getStartTime(), l.getEndTime()); } } return message;}
}
