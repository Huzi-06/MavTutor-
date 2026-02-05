package people;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import rating.Rateable;
import rating.Rating;

public class Person implements Rateable 
{
    private String name;
    private String email;
    private ArrayList<Rating> ratings = new ArrayList<>();
    
    public Person(String name, String email) 
    {
        this.name = name;
        this.email = email;
    }

    /**
     * Constructor to restore Person from Scanner input
     * @param in Scanner containing saved Person data
     * @since 1.0
     */
    public Person(Scanner in) 
    {
        this.name = in.nextLine();
        this.email = in.nextLine();
    }

    /**
     * Saves this Person to a PrintStream
     * @param out PrintStream to write Person data
     * @since 1.0
     */
    public void save(PrintStream out) 
    {
        out.println(name);
        out.println(email);
    }
    
    public String getName() 
    {
        return name;
    }
    
    public String getEmail() 
    {
        return email;
    }
    
    @Override
    public String toString() 
    {
        return name + " (" + email + ")";
    }
    
    @Override
    public boolean equals(Object o) 
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || o.getClass() != getClass())
        {
            return false;
        }
        Person p = (Person) o;
        return name.equals(p.name) && email.equals(p.email);
    }
    
    @Override
    public int hashCode() 
    {
        return Objects.hash(name,email);
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
            return 0.0;
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
