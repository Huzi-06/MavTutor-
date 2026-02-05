package test;

import people.Person;

public class TestPerson
{
    public static void main(String[] args)
    {
        Person p = new Person("huzaifa", "huzaifa@example.com");

        String exp = "huzaifa (huzaifa@example.com)";
        if (!p.toString().equals(exp))
        {
            throw new AssertionError("toString() expected '" + exp + "' but was '" + p.toString() + "'");
        }

        if (!p.getName().equals("huzaifa"))
        {
            throw new AssertionError("getName() expected 'huzaifa' but was '" + p.getName() + "'");
        }

        if (!p.equals(p))
        {
            throw new AssertionError("equals() should be true for same object");
        }

        if (p.equals(null))
        {
            throw new AssertionError("equals() should be false when compared to null");
        }

        if (p.equals("huzaifa"))
        {
            throw new AssertionError("equals() should be false when compared to a String");
        }

        Person p1 = new Person("huzaifa", "huzaifa@example.com");
        if (!p.equals(p1))
        {
            throw new AssertionError("equals() should be true for identical name and email");
        }
    }
}