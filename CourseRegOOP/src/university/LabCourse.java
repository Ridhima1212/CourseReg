package university;

public class LabCourse extends Course {
    public LabCourse(String code, String title, int capacity, Instructor inst) {
        super(code, title, capacity, inst);
    }
    @Override public int credits() { return 2; }
    @Override public double feePerCredit() { return 2000.0; } // labs costlier
    @Override public String kind() { return "Lab"; }
}