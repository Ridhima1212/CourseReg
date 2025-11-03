# CourseRegOOP (Java OOP Mini Project)

A console-based **University Course Registration** app (not library or airlines) that demonstrates all four OOP pillars.

## OOP pillars showcased
- **Encapsulation**: private fields + getters/setters in `User`, `Course`.
- **Inheritance**: `User` → `Student`, `Instructor`.
- **Abstraction**: `User` (abstract), `Course` (abstract), `Registrable` (interface).
- **Polymorphism**: `TheoryCourse`, `LabCourse`, `ProjectCourse` override `credits()` and `feePerCredit()`.
- CSV persistence for students, instructors, courses, and enrollments.

## Structure

```
src/university
├── Course.java
├── CourseApp.java
├── CSV.java
├── Instructor.java
├── LabCourse.java
├── ProjectCourse.java
├── Registrable.java
├── Registrar.java
├── Student.java
├── TheoryCourse.java
├── UniversityException.java
└── User.java
```

CSV files `students.csv`, `instructors.csv`, `courses.csv`, `enrollments.csv` are created in the working directory.

## How to compile & run (JDK 17+)

```bash
cd CourseRegOOP/src
javac university/*.java
java university.CourseApp
```

On first run, demo data is auto-seeded (a couple of students and instructors, and three course types).