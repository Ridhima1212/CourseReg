package university;

import java.util.Scanner;

public class CourseApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Registrar reg = new Registrar();

        System.out.println("Welcome to Course Registration OOP Demo");
        int ch;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. List courses");
            System.out.println("2. List students");
            System.out.println("3. Enroll student to course");
            System.out.println("4. Drop student from course");
            System.out.println("5. Add new student");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
            while (!sc.hasNextInt()) { System.out.print("Enter a number: "); sc.next(); }
            ch = sc.nextInt(); sc.nextLine();

            try {
                switch (ch) {
                    case 1 -> reg.listCourses().forEach(c -> System.out.println(" - " + c));
                    case 2 -> reg.listStudents().forEach(s -> System.out.println(" - " + s));
                    case 3 -> {
                        System.out.print("Student ID: ");
                        String sid = sc.nextLine().trim();
                        System.out.print("Course Code: ");
                        String code = sc.nextLine().trim();
                        reg.enroll(sid, code);
                        System.out.println("Enrolled successfully.");
                    }
                    case 4 -> {
                        System.out.print("Student ID: ");
                        String sid = sc.nextLine().trim();
                        System.out.print("Course Code: ");
                        String code = sc.nextLine().trim();
                        reg.drop(sid, code);
                        System.out.println("Dropped successfully.");
                    }
                    case 5 -> {
                        System.out.print("Student ID (e.g., S10): ");
                        String id = sc.nextLine().trim();
                        System.out.print("Name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("Email: ");
                        String email = sc.nextLine().trim();
                        System.out.print("Program: ");
                        String prog = sc.nextLine().trim();
                        reg.addStudent(new Student(id, name, email, prog));
                        System.out.println("Student added.");
                    }
                    case 6 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid.");
                }
            } catch (UniversityException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected: " + e.getMessage());
            }
        } while (ch != 6);
        sc.close();
    }
}