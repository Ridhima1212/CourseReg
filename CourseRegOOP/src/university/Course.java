package university;

import java.util.*;

// Abstraction: base course defines shared behavior
public abstract class Course implements Registrable {
    private final String code;
    private String title;
    private int capacity;
    private final Set<String> enrolled = new LinkedHashSet<>();
    private Instructor instructor;

    protected Course(String code, String title, int capacity, Instructor instructor) {
        this.code = code;
        this.title = title;
        this.capacity = capacity;
        this.instructor = instructor;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
    public Set<String> getEnrolled() { return Collections.unmodifiableSet(enrolled); }

    @Override
    public void enroll(String studentId) throws UniversityException {
        if (enrolled.contains(studentId)) throw new UniversityException("Already enrolled.");
        if (enrolled.size() >= capacity) throw new UniversityException("Course full.");
        enrolled.add(studentId);
    }

    @Override
    public void drop(String studentId) throws UniversityException {
        if (!enrolled.remove(studentId)) throw new UniversityException("Student not in course.");
    }

    @Override
    public int seatsLeft() { return capacity - enrolled.size(); }

    // Polymorphism: credits/fee computed differently for each kind
    public abstract int credits();
    public abstract double feePerCredit();

    public double totalFee() { return credits() * feePerCredit(); }

    @Override
    public String toString() {
        return String.format("%s %s (%s) | by %s | seats: %d/%d | credits=%d, fee=₹%.2f",
                kind(), code, title, instructor != null ? instructor.getName() : "TBA",
                enrolled.size(), capacity, credits(), totalFee());
    }

    public abstract String kind();
}