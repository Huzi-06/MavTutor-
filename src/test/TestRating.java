package test;

import people.Person;
import rating.Comment;
import rating.Rating;

public class TestRating
{
    public static void main(String[] args)
    {
        for (int stars = 1; stars <= 5; stars++)
        {
            Rating r = new Rating(stars, null);

            if (r.getStars() != stars)
            {
                throw new AssertionError("getStars() expected " + stars + " but was " + r.getStars());
            }

            String expected = "";
            for (int i = 0; i < stars; i++) 
            {
                expected += "★";
            }    
            for (int i = stars; i < 5; i++) 
            {
                expected += "☆";
            }    

            if (!expected.equals(r.toString()))
            {
                throw new AssertionError("toString() expected '" + expected + "' but was '" + r.toString() + "'");
            }
        }

        Person huzaifa = new Person("Huzaifa", "huzaifa@example.com");
        Comment review = new Comment("Good!", huzaifa, null);
        Rating rated = new Rating(5, review);
        if (rated.getReview() != review)
        {
            throw new AssertionError("getReview() did not return the same Comment reference");
        }
    }
}

