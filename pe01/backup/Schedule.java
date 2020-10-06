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
            this.classesList.add(c);
            return this;
        }
    }
}
