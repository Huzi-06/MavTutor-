package people;

import java.io.PrintStream;
import java.util.Scanner;
import session.Course;

public class Tutor extends Person 
{
    private String bio;
    private int ssn;
    private Course course;
    
    public Tutor(String name, String email, int ssn, String bio, Course course) 
    {
        super(name, email);

        if (ssn < 001_01_0001 || ssn > 999_99_9999) 
        {
            throw new IllegalArgumentException("Invalid SSN: " + ssn + " (must be between 001010001 and 999999999)");
        }
        
        this.ssn = ssn;
        this.bio = bio;
        this.course = course;
    }

    /**
     * Constructor to restore Tutor from Scanner input
     * @param in Scanner containing saved Tutor data
     * @since 1.0
     */
    public Tutor(Scanner in) 
    {
        super(in);
        this.bio = in.nextLine();
        this.ssn = in.nextInt();
        in.nextLine();
        this.course = new Course(in);
    }

    /**
     * Saves this Tutor to a PrintStream
     * @param out PrintStream to write Tutor data
     * @since 1.0
     */
    public void save(PrintStream out) 
    {
        super.save(out);
        out.println(bio);
        out.println(ssn);
        course.save(out);
    }
    
    public int getSSN() 
    {
        return ssn;
    }
    
    public String getBio() 
    {
        return bio;
    }
    
    public Course getCourse() 
    {
        return course;
    }
}
