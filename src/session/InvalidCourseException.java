package session;

/**
 * Thrown to indicate that an invalid department code or course number
 * was provided when constructing a Course object.
 * @author             Muhammad Huzaifa
 * @version            1.0
 * @since              1.0
 * @license.agreement Gnu General Public License 3.0
 */

public class InvalidCourseException extends IllegalArgumentException 
{
    /**
     * Constructs an InvalidCourseException for an invalid department code.
     *
     * @param dept  the invalid department code
     * @since       1.0
     */
    
    public InvalidCourseException(String dept) 
    {
        super("Invalid dept in new Course: " + dept);
    }

    /**
     * Constructs an  InvalidCourseException for an invalid course number.
     *
     * @param dept    the department associated with the invalid course number
     * @param number  the invalid course number
     * @since         1.0
     */
    
    public InvalidCourseException(String dept, int number) 
    {
        super("Invalid course number in new Course: " + number);
    }
}