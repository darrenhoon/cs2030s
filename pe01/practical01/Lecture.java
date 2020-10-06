public class Lecture {

    private final Instructor ins;
    private final int classId;
    private final String venueId;
    private final int start;
    private String mod;
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
        if (c instanceof Tutorial) {
            Tutorial t = (Tutorial) c;
            return this.getMod() == t.getMod();
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
        
        if (c instanceof Tutorial) {
            Tutorial t = (Tutorial) c;
            Instructor ins = t.getIns();
            return this.getIns().equals(ins);
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
        
        if (c instanceof Tutorial) {
            Tutorial t = (Tutorial) c;
            return this.getVenue() == t.getVenue();
        }

        if (c instanceof Lecture) {
            Lecture l = (Lecture) c;
            return this.getVenue() == l.getVenue();
        } 
        return false;
    }

    boolean clashWith(Lecture c) {
        if (c instanceof Tutorial && this instanceof Tutorial) {
            if (c.getStartTime() == this.getStartTime()) {
                if (this.hasSameVenue(c) || this.hasSameInstructor(c)) {
                    return true;
                }
            }
            return false;
        }
        else {
            
            if ((this.getStartTime() <= c.getStartTime()) && (c.getStartTime() <= this.getEndTime())) {
            }

            if ((this.getStartTime() <= c.getStartTime()) && (c.getEndTime() <= this.getEndTime())) {
            }
            return false;
        }
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
