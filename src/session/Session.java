package session;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import people.Student;
import people.Tutor;
import rating.Rateable;
import rating.Rating;


/**
 * Represents a tutoring session for a specific course.
 * 
 * @author Muhammad Huzaifa
 * @version 1.0
 * @since 1.0
 * @license.agreement Gnu General Public License 3.0
 */

public class Session implements Rateable
{
    private Course course;
    private Tutor tutor;
    private DateRange dates;
    private List<Student> students;
    private ArrayList<Rating> ratings = new ArrayList<>();

    /**
     * Constructs a new Session for the specified course and tutor.
     * 
     * @param course the course for this tutoring session
     * @param tutor the tutor conducting this session
     * @since 1.0
     */

    public Session(Course course, Tutor tutor) 
    {
        this.course = course;
        this.tutor = tutor;
        this.students = new ArrayList<>();
    }

    /**
     * Constructor to restore Session from Scanner input
     * @param in Scanner containing saved Session data
     * @since 1.0
     */
    public Session(Scanner in) 
    {
        this.course = new Course(in);
        this.dates = new DateRange(in);
        this.tutor = new Tutor(in);
        
        this.students = new ArrayList<>();
        int numStudents = in.nextInt();
        in.nextLine();
        for (int i = 0; i < numStudents; i++) 
        {
            students.add(new Student(in));
        }
    }

    /**
     * Saves this Session to a PrintStream
     * @param out PrintStream to write Session data
     * @since 1.0
     */
    public void save(PrintStream out) 
    {
        course.save(out);
        dates.save(out);
        tutor.save(out);
        out.println(students.size());
        for (Student student : students) 
        {
            student.save(out);
        }
    }

    /**
     * Sets the schedule for this session.
     * 
     * @param date the date in YYYYMMDD format
     * @param startTime the start time in 24-hour format HH:MM
     * @param endTime the end time in 24-hour format HH:MM
     * @since 1.0
     */

    public void setSchedule(String date, String startTime, long duration) 
    {
        this.dates = new DateRange(date, startTime, duration);
    }

    /**
     * Adds a student to this session.
     * 
     * @param student the student to add to this session
     * @since 1.0
     */
    public void addStudent(Student student) 
    {
        students.add(student);
    }

    /**
     * Returns a string representation of this session.
     * 
     * @return a multi-line string representation of this session
     * @since 1.0
     */

    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Session on ").append(course).append(" at ").append(dates).append("\n");
        sb.append("  Tutor: ").append(tutor).append("\n");
        sb.append("  Students: ");
        for (int i = 0; i < students.size(); i++) 
        {
            if (i > 0) 
            {
                sb.append(", ");
            }
            sb.append(students.get(i));
        }
        return sb.toString();
    }

    @Override
    public void addRating(Rating rating) 
    {
        ratings.add(rating);
    }

    @Override
    public double getAverageRating() 
    {
        if (ratings.isEmpty()) 
        {
            return Double.NaN;
        }
    
        double sum = 0.0;
        for (Rating rating : ratings) 
        {
            sum += rating.getStars();
        }
        return sum / ratings.size();
    }

    @Override
    public Rating[] getRatings() 
    {
        return ratings.toArray(new Rating[0]);
    }
}
