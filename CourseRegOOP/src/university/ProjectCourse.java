package university;

public class ProjectCourse extends Course {
    public ProjectCourse(String code, String title, int capacity, Instructor inst) {
        super(code, title, capacity, inst);
    }
    @Override public int credits() { return 4; }
    @Override public double feePerCredit() { return 1200.0; } // discounted for project
    @Override public String kind() { return "Project"; }
}