package project;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
class Course {
    private String courseCode;
    private String courseName;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    // Getters and Setters
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
}

class Room {
    private String roomId;
    private int capacity;
    private boolean isAvailable;

    public Room(String roomId, int capacity) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getRoomId() { return roomId; }
    public int getCapacity() { return capacity; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailability(boolean available) { isAvailable = available; }
}

class Exam {
    private Course course;
    private Date date;
    private int duration; // in minutes
    private Room room;
    private String instructor;

    public Exam(Course course, Date date, int duration, Room room, String instructor) {
        this.course = course;
        this.date = date;
        this.duration = duration;
        this.room = room;
        this.instructor = instructor;
    }

    // Getters and Setters
    public Course getCourse() { return course; }
    public Date getDate() { return date; }
    public int getDuration() { return duration; }
    public Room getRoom() { return room; }
    public String getInstructor() { return instructor; }
}
class ExamScheduler {
    private List<Exam> exams = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private Map<String, List<Date>> studentSchedules = new HashMap<>();
    private Map<String, List<Date>> instructorSchedules = new HashMap<>();

    // Add room
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Schedule an exam
    public boolean scheduleExam(Course course, Date date, int duration, String instructor, int classSize) {
        Room allocatedRoom = allocateRoom(classSize, date);
        if (allocatedRoom == null || isInstructorBusy(instructor, date) || hasStudentConflict(course, date)) {
            System.out.println("Scheduling failed due to constraints.");
            return false;
        }

        Exam exam = new Exam(course, date, duration, allocatedRoom, instructor);
        exams.add(exam);
        allocatedRoom.setAvailability(false);
        addInstructorSchedule(instructor, date);
        System.out.println("Exam scheduled successfully.");
        return true;
    }

    // Check room availability
    private Room allocateRoom(int classSize, Date date) {
        for (Room room : rooms) {
            if (room.getCapacity() >= classSize && room.isAvailable()) {
                return room;
            }
        }
        return null;
    }

    // Check instructor's schedule
    private boolean isInstructorBusy(String instructor, Date date) {
        return instructorSchedules.getOrDefault(instructor, new ArrayList<>()).contains(date);
    }

    // Check for student conflicts
    private boolean hasStudentConflict(Course course, Date date) {
        return false;
    }

    // Add instructor schedule
    private void addInstructorSchedule(String instructor, Date date) {
        instructorSchedules.putIfAbsent(instructor, new ArrayList<>());
        instructorSchedules.get(instructor).add(date);
    }

    // Display the schedule
    public void displaySchedule() {
        for (Exam exam : exams) {
            System.out.println("Course: " + exam.getCourse().getCourseName() +
                    ", Date: " + exam.getDate() +
                    ", Room: " + exam.getRoom().getRoomId() +
                    ", Instructor: " + exam.getInstructor());
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        ExamScheduler scheduler = new ExamScheduler();

        // Adding rooms
        scheduler.addRoom(new Room("Room204", 50));
        scheduler.addRoom(new Room("Room221", 30));
        scheduler.addRoom(new Room("Room231",30));

        // Adding courses
        Course course1 = new Course("CS211", "Data Structures");
        Course course2 = new Course("CS299", "Networking");
        Course course3 = new Course("CS292", "Focp");
        // Scheduling exams
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        scheduler.scheduleExam(course2, sdf.parse("2024-12-15 09:00"), 120, "Dr. Prachi", 40);
        scheduler.scheduleExam(course1, sdf.parse("2024-12-15 14:00"), 90, "Dr. Anuradha", 20);
        scheduler.scheduleExam(course3, sdf.parse("2024-12-15 09:00"), 120, "Dr. Garima", 40);

        // Display schedule
        scheduler.displaySchedule();
    }
}

