package com.codsoft.com;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int availableSlots;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.availableSlots = capacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public boolean registerStudent() {
        if (availableSlots > 0) {
            availableSlots--;
            return true;
        }
        return false;
    }

    public void dropStudent() {
        if (availableSlots < capacity) {
            availableSlots++;
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode +
               "\nTitle: " + title +
               "\nDescription: " + description +
               "\nSchedule: " + schedule +
               "\nAvailable Slots: " + availableSlots + "/" + capacity + "\n";
    }
}

class Student {
    private int studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (course.registerStudent()) {
            registeredCourses.add(course);
        }
    }

    public void dropCourse(Course course) {
        course.dropStudent();
        registeredCourses.remove(course);
    }

    @Override
    public String toString() {
        StringBuilder coursesList = new StringBuilder();
        for (Course course : registeredCourses) {
            coursesList.append(course.getTitle()).append(", ");
        }
        return "Student ID: " + studentID +
               "\nName: " + name +
               "\nRegistered Courses: " + (coursesList.length() > 0 ? coursesList.substring(0, coursesList.length() - 2) : "None") + "\n";
    }
}

public class StudentCourseRegistrationSystem {
    private List<Student> students;
    private List<Course> courses;

    public StudentCourseRegistrationSystem() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public Student findStudentByID(int studentID) {
        for (Student student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentCourseRegistrationSystem system = new StudentCourseRegistrationSystem();

        // Add initial students and courses
        system.addStudent(new Student(1, "Mukesh Kumar"));
        system.addStudent(new Student(2, "Rinkesh Kumar"));
        system.addCourse(new Course("CS101", "Introduction to Programming", "Learn the basics of programming", 50, "Mon, Wed 9:00 AM - 10:30 AM"));
        system.addCourse(new Course("MATH202", "Advanced Mathematics", "Advanced math concepts", 40, "Tue, Thu 11:00 AM - 12:30 PM"));

        while (true) {
            displayMenu();
            int choice = getChoice(scanner);

            switch (choice) {
                case 1:
                    registerCourse(scanner, system);
                    break;
                case 2:
                    dropCourse(scanner, system);
                    break;
                case 3:
                    displayStudentInfo(scanner, system);
                    break;
                case 4:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Student Course Registration System");
        System.out.println("1. Register Course");
        System.out.println("2. Drop Course");
        System.out.println("3. Display Student Info");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    private static int getChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.next(); // discard invalid input
        }
        return scanner.nextInt();
    }

    private static void registerCourse(Scanner scanner, StudentCourseRegistrationSystem system) {
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        Student student = system.findStudentByID(studentID);
        Course course = system.findCourseByCode(courseCode);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (course.registerStudent()) {
            student.registerCourse(course);
            System.out.println("Course registered successfully.");
        } else {
            System.out.println("Course is full, registration failed.");
        }
    }

    private static void dropCourse(Scanner scanner, StudentCourseRegistrationSystem system) {
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        Student student = system.findStudentByID(studentID);
        Course course = system.findCourseByCode(courseCode);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.dropCourse(course);
        course.dropStudent();
        System.out.println("Course dropped successfully.");
    }

    private static void displayStudentInfo(Scanner scanner, StudentCourseRegistrationSystem system) {
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();

        Student student = system.findStudentByID(studentID);
        if (student != null) {
            System.out.println("Student Info:\n" + student);
        } else {
            System.out.println("Student not found.");
        }
    }
}
