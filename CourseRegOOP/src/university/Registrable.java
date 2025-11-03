package university;

public interface Registrable {
    void enroll(String studentId) throws UniversityException;
    void drop(String studentId) throws UniversityException;
    int seatsLeft();
}