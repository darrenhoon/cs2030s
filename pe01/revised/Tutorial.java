class Tutorial extends Lecture {

    Tutorial(String mod, int cid, String vid, Instructor ins, int start) {
        super(mod, cid, vid, ins, start);
    }

    @Override
    public int getEndTime() {
        return this.getStartTime() + this.getTutorialDuration();
    }

    //added 1 to 4
    @Override
    String getClassType() {return "Tutorial";}
}

//lines added: 1 to 4
