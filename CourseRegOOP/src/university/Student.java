package university;

public class Student extends User {
    private String program;

    public Student(String id, String name, String email, String program) {
        super(id, name, email);
        this.program = program;
    }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    @Override public String role() { return "Student"; }
}