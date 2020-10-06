public class Lecture {

    private final Instructor ins;
    private final int classId;
    private final String venueId;
    private final int start;
    private final String mod;
    private final int lectureDuration = 2;
    private final int tutorialDuration = 1;

    Lecture(String mod, int cid, String vid, Instructor ins, int start) {
        this.mod = mod;
        this.classId = cid;
        this.venueId = vid;
        this.ins = ins;
        this.start = start;
    }

    //8lines removed, 1 line added = 9
    boolean hasSameModule(Lecture c) {
        if (c == this) {
            return true;
        }
        return this.getMod() == c.getMod();
    }

    //10 lines removed, 1 added = 11
    boolean hasSameInstructor(Lecture c) {
        if (c == this) {
            return true;
        } 
        return this.getIns().getName() == c.getIns().getName();
    }

    //10 lines removed, 1 line added = 11
    boolean hasSameVenue(Lecture c) {
        if (this.equals(c)) {
            return true;
        }
        return this.getVenue() == c.getVenue();
    }

    //17 lines removed, everything in the body added 6 lines
    boolean clashWith(Lecture c) {
        if (((this.getStartTime() <= c.getStartTime()) && (c.getStartTime() < this.getEndTime())) || ((this.getStartTime() < c.getEndTime()) && (c.getEndTime() <= this.getEndTime()))) {
            if (((this.getClassType() == "Lecture" && c.getClassType() == "Lecture") || (this.getClassType() == "Lecture" && c.getClassType() == "Tutorial") || (this.getClassType() == "Tutorial" && c.getClassType() == "Lecture")) && (this.hasSameModule(c) || this.hasSameVenue(c)|| this.hasSameInstructor(c))) {
                return true;
            }
            return (this.hasSameInstructor(c) || this.hasSameVenue(c));
        }
        return false;
    }

    String getMod() {
        return this.mod;
    }

    Instructor getIns() {
        return this.ins;
    }

    String getVenue() {
        return this.venueId;
    }

    int getStartTime() {
        return this.start;
    }

    //added 3 lines
    int getEndTime() {
        if (this.getClassType() == "Tutorial") {
            return this.getStartTime() + tutorialDuration;
        }
        return this.getStartTime() + lectureDuration;
    }

    int getClassId() {
        return this.classId;
    }

    //added 3 lines
    String getClassType() {
        return "Lecture";
    }

    int getTutorialDuration() {
        return this.tutorialDuration;
    }
}
