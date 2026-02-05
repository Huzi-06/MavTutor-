package session;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents a time range on a specific date for scheduling purposes.
 * 
 * @author Muhammad Huzaifa
 * @version 1.0
 * @since 1.0
 * @license.agreement Gnu General Public License 3.0
 */
public class DateRange 
{
    private String date;
    private String startTime;
    private String endTime;

    /**
     * Constructs a DateRange with the specified date, start time, and end time.
     * @param date the date in YYYYMMDD format
     * @param startTime the start time in 24-hour format HH:MM
     * @param endTime the end time in 24-hour format HH:MM
     * @since 1.0
     */
    
    public DateRange(String date, String startTime, String endTime)
    {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs a DateRange with the specified date, start time, and duration.
     * @param date the date in YYYYMMDD format
     * @param startTime the start time in 24-hour format HH:MM
     * @param duration the duration in minutes
     * @since 1.0
     */

    public DateRange(String date, String startTime, long duration) 
    {
        this.date = date;
        this.startTime = startTime;

        String[] parts = startTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        minutes += duration;
        hours += minutes / 60;
        minutes = minutes % 60;

        this.endTime = String.format("%02d:%02d", hours, minutes);
    }

    /**
     * Constructor to restore DateRange from Scanner input
     * @param in Scanner containing saved DateRange data
     * @since 1.0
     */
    public DateRange(Scanner in) 
    {
        this.date = in.nextLine();
        this.startTime = in.nextLine();
        this.endTime = in.nextLine();
    }

    /**
     * Saves this DateRange to a PrintStream
     * @param out PrintStream to write DateRange data
     * @since 1.0
     */
    public void save(PrintStream out) 
    {
        out.println(date);
        out.println(startTime);
        out.println(endTime);
    }

    /**
     * Calculates and returns the duration of this date range in minutes.
     * @return the duration in minutes between start time and end time
     * @since 1.0
     */

    public long duration() 
    {
        String[] startTimeComponents = startTime.split(":");
        int startHours = Integer.parseInt(startTimeComponents[0]);
        int startMinutes = Integer.parseInt(startTimeComponents[1]);
        int startTotal = startHours * 60 + startMinutes;

        String[] endTimeComponents = endTime.split(":");
        int endHours = Integer.parseInt(endTimeComponents[0]);
        int endMinutes = Integer.parseInt(endTimeComponents[1]);
        int endTotal = endHours * 60 + endMinutes;

        return endTotal - startTotal;
    }

    /**
     * Returns a string representation of this date range.
     * 
     * @return a string representation of this date range with date, times, and duration
     * @since 1.0
     */

    @Override
    public String toString() 
    {
        return date + " " + startTime + " - " + endTime + " (" + duration() + " minutes)";
    }
}
