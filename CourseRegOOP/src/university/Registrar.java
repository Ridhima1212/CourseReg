package university;

import java.nio.file.*;
import java.util.*;

// Core manager: holds users + courses; CSV persistence
public class Registrar {
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Instructor> instructors = new LinkedHashMap<>();
    private final Map<String, Course> courses = new LinkedHashMap<>();

    private static final Path STUDENTS = Paths.get("students.csv");
    private static final Path INSTRUCTORS = Paths.get("instructors.csv");
    private static final Path COURSES = Paths.get("courses.csv");
    private static final Path ENROLLMENTS = Paths.get("enrollments.csv"); // studentId,courseCode

    public Registrar() {
        loadInstructors();
        loadStudents();
        loadCourses();
        loadEnrollments();
        if (courses.isEmpty()) seedDemo();
    }

    private void seedDemo() {
        Instructor i1 = instructors.values().stream().findFirst().orElseGet(() -> {
            Instructor i = new Instructor("T01", "Dr. Meera", "meera@univ.edu", "CSE");
            instructors.put(i.getId(), i); saveInstructors(); return i;
        });
        addCourse(new TheoryCourse("CS101", "Programming Basics", 3, i1));
        addCourse(new LabCourse("CS101L", "Programming Lab", 2, i1));
        addCourse(new ProjectCourse("CS399", "Mini Project", 2, i1));
    }

    // --- CRUD helpers ---
    public void addStudent(Student s) { students.put(s.getId(), s); saveStudents(); }
    public void addInstructor(Instructor i) { instructors.put(i.getId(), i); saveInstructors(); }
    public void addCourse(Course c) { courses.put(c.getCode(), c); saveCourses(); }

    public Student getStudent(String id) { return students.get(id); }
    public Course getCourse(String code) { return courses.get(code); }

    public Collection<Student> listStudents() { return students.values(); }
    public Collection<Instructor> listInstructors() { return instructors.values(); }
    public Collection<Course> listCourses() { return courses.values(); }

    // --- Enrollment actions ---
    public void enroll(String studentId, String courseCode) throws UniversityException {
        Student s = students.get(studentId);
        if (s == null) throw new UniversityException("Student not found.");
        Course c = courses.get(courseCode);
        if (c == null) throw new UniversityException("Course not found.");
        c.enroll(studentId);
        CSV.append(ENROLLMENTS, new String[]{studentId, courseCode});
    }

    public void drop(String studentId, String courseCode) throws UniversityException {
        Course c = courses.get(courseCode);
        if (c == null) throw new UniversityException("Course not found.");
        c.drop(studentId);
        // rewrite enrollments to reflect drop
        List<String[]> rows = CSV.read(ENROLLMENTS);
        rows.removeIf(r -> r.length >= 2 && r[0].equals(studentId) && r[1].equals(courseCode));
        CSV.writeAll(ENROLLMENTS, rows);
    }

    // --- CSV persistence ---
    private void loadStudents() {
        for (String[] r : CSV.read(STUDENTS)) {
            if (r.length < 4) continue;
            students.put(r[0], new Student(r[0], r[1], r[2], r[3]));
        }
        if (students.isEmpty()) {
            addStudent(new Student("S01", "Riya Agarwal", "riya@univ.edu", "B.Tech CSE"));
            addStudent(new Student("S02", "Aditya Singh", "adi@univ.edu", "B.Tech CSE"));
        }
    }
    private void saveStudents() {
        List<String[]> rows = new ArrayList<>();
        for (Student s : students.values())
            rows.add(new String[]{s.getId(), s.getName(), s.getEmail(), s.getProgram()});
        CSV.writeAll(STUDENTS, rows);
    }

    private void loadInstructors() {
        for (String[] r : CSV.read(INSTRUCTORS)) {
            if (r.length < 4) continue;
            instructors.put(r[0], new Instructor(r[0], r[1], r[2], r[3]));
        }
        if (instructors.isEmpty()) {
            addInstructor(new Instructor("T01", "Dr. Meera", "meera@univ.edu", "CSE"));
            addInstructor(new Instructor("T02", "Prof. Arjun", "arjun@univ.edu", "ECE"));
        }
    }
    private void saveInstructors() {
        List<String[]> rows = new ArrayList<>();
        for (Instructor i : instructors.values())
            rows.add(new String[]{i.getId(), i.getName(), i.getEmail(), i.getDepartment()});
        CSV.writeAll(INSTRUCTORS, rows);
    }

    private void loadCourses() {
        for (String[] r : CSV.read(COURSES)) {
            if (r.length < 5) continue;
            String kind = r[0], code = r[1], title = r[2];
            int capacity = Integer.parseInt(r[3]);
            Instructor inst = instructors.get(r[4]);
            Course c = switch (kind) {
                case "Theory" -> new TheoryCourse(code, title, capacity, inst);
                case "Lab" -> new LabCourse(code, title, capacity, inst);
                case "Project" -> new ProjectCourse(code, title, capacity, inst);
                default -> null;
            };
            if (c != null) courses.put(code, c);
        }
    }
    private void saveCourses() {
        List<String[]> rows = new ArrayList<>();
        for (Course c : courses.values())
            rows.add(new String[]{c.kind(), c.getCode(), c.getTitle(), String.valueOf(c.getCapacity()),
                    c.getInstructor() != null ? c.getInstructor().getId() : ""});
        CSV.writeAll(COURSES, rows);
    }

    private void loadEnrollments() {
        for (String[] r : CSV.read(ENROLLMENTS)) {
            if (r.length < 2) continue;
            Course c = courses.get(r[1]);
            if (c != null) {
                try { c.enroll(r[0]); } catch (UniversityException ignored) {}
            }
        }
    }
}