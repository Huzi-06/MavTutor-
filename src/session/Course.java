package session;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a university course identified by a department code and a course number.
 * @author             Muhammad Huzaifa
 * @version            1.0
 * @since              1.0
 * @license.agreement Gnu General Public License 3.0
 */

public class Course 
{
    private String dept;
    private int number;

    /**
     * Constructs a Course object with the specified department code and course number.
     * @param dept   the department code, typically 3–4 uppercase letters (e.g., "CSE")
     * @param number the course number, a 4-digit integer (e.g., 1325)
     * @throws InvalidCourseException if the department or course number is invalid
     * @since  1.0
     */
    
    public Course(String dept, int number) 
    {
        if (dept == null || dept.length() < 3 || dept.length() > 4) 
        {
            throw new InvalidCourseException(dept);
        }
        
        if (number < 1000 || number > 9999) 
        {
            throw new InvalidCourseException(dept, number);
        }
        
        this.dept = dept;
        this.number = number;
    }

    /**
     * Constructor to restore Course from Scanner input
     * @param in Scanner containing saved Course data
     * @since 1.0
     */
    public Course(Scanner in) 
    {
        this(in.nextLine(), in.nextInt());
        in.nextLine();
    }

    /**
     * Saves this Course to a PrintStream
     * @param out PrintStream to write Course data
     * @since 1.0
     */
    public void save(PrintStream out) 
    {
        out.println(dept);
        out.println(number);
    }

    /**
     * Returns a string representation of this  Course in the form DEPT#### 
     *
     * @return a string representation of the course combining department and number
     * @since  1.0
     */
    
    @Override
    public String toString() 
    {
        return dept + number;
    }

    /**
     * Determines whether this Course is equal to another object.
     *
     * @param o the object to compare with this course
     * @return  true if the given object is a  Course with the same
     *          department and number, false otherwise
     * @since   1.0
     */

    @Override
    public boolean equals(Object o) 
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        
        Course c = (Course) o;
        return number == c.number && dept.equals(c.dept);
    }

    /**
     * Returns a hash code value for this Course. 
     *
     * @return a hash code value based on the department and course number
     * @since  1.0
     */

    @Override
    public int hashCode() 
    {
        return Objects.hash(dept,number);
    }
}
