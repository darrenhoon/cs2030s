class Tutorial extends Lecture {

    Tutorial(String mod, int cid, String vid, Instructor ins, int start) {
        super(mod, cid, vid, ins, start);
    }
    
    String getClassType() { return "Tutorial";}
}
