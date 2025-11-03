package university;

public class TheoryCourse extends Course {
    public TheoryCourse(String code, String title, int capacity, Instructor inst) {
        super(code, title, capacity, inst);
    }
    @Override public int credits() { return 3; }
    @Override public double feePerCredit() { return 1500.0; }
    @Override public String kind() { return "Theory"; }
}