package rating;

public class Rating
{
    private int stars;
    private Comment review;

    public Rating(int stars, Comment review)
    {
        if (stars < 1 || stars > 5)
        {
            throw new IllegalArgumentException("Stars must be between 1 and 5 inclusive");
        }
        
        this.stars = stars;
        this.review = review;
    }

    public int getStars()
    {
        return stars;
    }

    public Comment getReview()
    {
        return review;
    }

    @Override
    public String toString()
    {
        String result = "";
        
        for (int i = 0; i < stars; i++)
        {
            result += "★";
        }
        
        for (int i = stars; i < 5; i++)
        {
            result += "☆";
        }
        
        return result;
    }
}

