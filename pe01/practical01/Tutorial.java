class Tutorial extends Lecture {

    Tutorial(String mod, int cid, String vid, Instructor ins, int start) {
        super(mod, cid, vid, ins, start);
    }

    @Override
    public int getEndTime() {
        return this.getStartTime() + this.getTutorialDuration();
    }
}
