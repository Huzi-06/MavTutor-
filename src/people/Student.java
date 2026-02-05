package people;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import session.Course;

public class Student extends Person 
{
    private static int nextStudentID = 0;
    private int studentID;
    private ArrayList<Course> courses = new ArrayList<>();
    
    public Student(String name, String email) 
    {
        super(name, email);
        this.studentID = nextStudentID;
        nextStudentID++;
    }

    /**
     * Constructor to restore Student from Scanner input
     * @param in Scanner containing saved Student data
     * @since 1.0
     */
    public Student(Scanner in) 
    {
        super(in);
        this.studentID = in.nextInt();
        in.nextLine();

        nextStudentID = in.nextInt();
        in.nextLine();
        
        this.courses = new ArrayList<>();
        int numCourses = in.nextInt();
        in.nextLine();
        for (int i = 0; i < numCourses; i++) 
        {
            courses.add(new Course(in));
        }
    }

    /**
     * Saves this Student to a PrintStream
     * @param out PrintStream to write Student data
     * @since 1.0
     */
    public void save(PrintStream out) 
    {
        super.save(out);
        out.println(studentID);
        out.println(nextStudentID);
        out.println(courses.size());
        for (Course course : courses) 
        {
            course.save(out);
        }
    }
    
    public void addCourse(Course course) 
    {
        courses.add(course);
    }

    public Course[] getCourses() 
    {
        return courses.toArray(new Course[0]);
    }

    @Override
    public String toString() 
    {
        String superString = super.toString();
        return superString.replace(")", ", #" + studentID + ")");
    }
}
