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
        } //8lines removed, 1 line added = 9
       return this.getMod() == c.getMod();
    }

    boolean hasSameInstructor(Lecture c) {
        if (c == this) {
            return true;
        } //10 lines removed, 1 added = 11
        return this.getIns().getName() == c.getIns().getName();
    }

    boolean hasSameVenue(Lecture c) {
        if (this.equals(c)) {
            return true;
        } //10 lines removed, 1 line added = 11
        return this.getVenue() == c.getVenue();
    }

    boolean clashWith(Lecture c) { 
        //17 lines removed, added 1 = 8
        return true;
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

    int getEndTime() {
        return this.getStartTime() + lectureDuration;
    }

    int getClassId() {
        return this.classId;
    }

    int getTutorialDuration() {
        return this.tutorialDuration;
    }
}
