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

    boolean hasSameModule(Lecture c) {
        if (c == this) {
            return true;
        }
        if (c instanceof Lecture) {
            Lecture l = (Lecture) c;
            return this.getMod() == l.getMod();
        }
        return false;
    }

    boolean hasSameInstructor(Lecture c) {
        if (c == this) {
            return true;
        }
        if (c instanceof Lecture) {
            Lecture l = (Lecture) c;
            Instructor ins = l.getIns();
            return this.getIns().equals(ins);
        }
        return false;
    }

    boolean hasSameVenue(Lecture c) {
        if (this.equals(c)) {
            return true;
        }
        if (c instanceof Lecture) {
            Lecture l = (Lecture) c;
            return this.getVenue() == l.getVenue();
        } 
        return false;
    }
    boolean clashWith(Lecture c) {
        if (((this.getStartTime() <= c.getStartTime()) && (c.getStartTime() < this.getEndTime())) || ((this.getStartTime() < c.getEndTime()) && (c.getEndTime() <= this.getEndTime()))) {if (((this.getClassType() == "Lecture" && c.getClassType() == "Lecture") || (this.getClassType() == "Lecture" && c.getClassType() == "Tutorial") || (this.getClassType() == "Tutorial" && c.getClassType() == "Lecture")) && (this.hasSameModule(c) || this.hasSameVenue(c)|| this.hasSameInstructor(c))) {return true;} return (this.hasSameInstructor(c) || this.hasSameVenue(c));}return false;}

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

    int getEndTime() {
        if (this.getClassType == "Tutorial") {
            return this.getStartTime() + tutorialDuration;
        }
        return this.getStartTime() + lectureDuration;
    }

    int getClassId() {
        return this.classId;
    }

    int getTutorialDuration() {
        return this.tutorialDuration;
    }

    String getClassType() { return "Lecture";}
}
