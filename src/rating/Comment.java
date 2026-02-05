package rating;

import java.util.ArrayList;
import people.Person;

public class Comment
{
    private String text;
    private Person author;
    private Comment inReplyTo;
    private ArrayList<Comment> replies;

    public Comment(String text, Person author, Comment inReplyTo)
    {
        if (text == null || text.isEmpty())
        {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        if (author == null)
        {
            throw new IllegalArgumentException("Author cannot be null");
        }
        
        this.text = text;
        this.author = author;
        this.inReplyTo = inReplyTo;
        this.replies = new ArrayList<>();
    }

    public void addReply(String text, Person author)
    {
        if (text == null || text.isEmpty())
        {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        if (author == null)
        {
            throw new IllegalArgumentException("Author cannot be null");
        }
        
        Comment newReply = new Comment(text, author, this);
        replies.add(newReply);
    }

    public int numReplies()
    {
        return replies.size();
    }

    public Comment getReply(int index)
    {
        return replies.get(index);
    }

    public Comment getInReplyTo()  
    {
        return inReplyTo;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Comment by " + author);
        
        if (inReplyTo != null)
        {
            sb.append(" in reply to ").append(inReplyTo.author);
        }

        if (!replies.isEmpty())
        {
            sb.append("\nReplies: ");
            for (int i = 0; i < replies.size(); i++)
            {
                sb.append("  (").append(i).append(") ").append(replies.get(i).author.getName());
            }
        }

        sb.append("\n").append(text);

        return sb.toString();
    }
}    


