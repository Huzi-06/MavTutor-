package mdi;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import menu.Menu;
import menu.MenuItem;
import people.Student;
import people.Tutor;
import rating.Comment;
import rating.Rateable;
import rating.Rating;
import session.Course;
import session.InvalidCourseException;
import session.Session;

/**
* MavTutor - A menu-driven interface for managing tutoring sessions
* This class coordinates tutors, students, courses, and tutoring sessions
*/

public class MavTutor 
{
    private List<Course> courses;
    private List<Student> students;
    private List<Tutor> tutors;
    private List<Session> sessions;
    private List<?> view;
    private Menu menu;
    private File file;
    private boolean dirty;
    private people.Person user;

    /**
    * Constructor - initializes the MavTutor application
    */

    public MavTutor() 
    {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        tutors = new ArrayList<>();
        sessions = new ArrayList<>();
        view = courses;
        file = null;
        dirty = false;
        user = null;

        menu = new Menu("\n\nWelcome to MavTutor!\n=============================", new Object[]{this, "\nSelection? "});

        menu.addMenuItem(new MenuItem("Quit", () -> quit()));
        menu.addMenuItem(new MenuItem("New", () -> newz()));
        menu.addMenuItem(new MenuItem("Save As", () -> saveAs()));
        menu.addMenuItem(new MenuItem("Save", () -> save()));
        menu.addMenuItem(new MenuItem("Open", () -> open()));
        menu.addMenuItem(new MenuItem("View Courses", () -> selectView(courses)));
        menu.addMenuItem(new MenuItem("New Course", () -> newCourse()));
        menu.addMenuItem(new MenuItem("View Students", () -> selectView(students)));
        menu.addMenuItem(new MenuItem("New Student", () -> newStudent()));
        menu.addMenuItem(new MenuItem("View Tutors", () -> selectView(tutors)));
        menu.addMenuItem(new MenuItem("New Tutor", () -> newTutor()));
        menu.addMenuItem(new MenuItem("View Sessions", () -> selectView(sessions)));
        menu.addMenuItem(new MenuItem("New Session", () -> newSession()));
        menu.addMenuItem(new MenuItem("Review Student", () -> review(students)));
        menu.addMenuItem(new MenuItem("Review Tutor", () -> review(tutors)));
        menu.addMenuItem(new MenuItem("Review Session", () -> review(sessions)));

        menu.run();
    }
    /**
    * Checks if it's safe to discard current data
    * @return true if safe to discard, false otherwise
    */
    private boolean safeToDiscardData() 
    {
        if (!dirty) 
        {
            return true;  // No unsaved data
        }

        while (true) 
        {
            String response = Menu.getString("Unsaved data exists! (S)ave, (D)iscard, or (A)bort? ");
            response = response.toUpperCase().trim();

            if (response.equals("S") || response.equals("SAVE")) 
            {
                save();
                return !dirty;
            } 
            else if (response.equals("D") || response.equals("DISCARD")) 
            {
                dirty = false;
                return true;  
            } 
            else if (response.equals("A") || response.equals("ABORT")) 
            {
                return false; 
            } 
            else 
            {
                System.out.println("Invalid response. Please enter S, D, or A.");
            }
        }
    }


    /**
    * Creates a new (empty) MavTutor
    */
    private void newz() 
    {
        if (!safeToDiscardData()) 
        {
            menu.result = new StringBuilder("New operation cancelled - data preserved");
            return;
        }
        courses.clear();
        students.clear();
        tutors.clear();
        sessions.clear();
        file = null;
        dirty = false;
        menu.result = new StringBuilder("New MavTutor created - all data cleared");
    }

    /**
    * Save As - save to a selected filename
    */
    private void saveAs() 
    {
        file = null;
        save();
    }

    /**
    * Save - save to current filename or request one if needed
    */
    private void save() 
    {
        try {
            if (file == null) 
            {
                file = Menu.selectFile("Select file to save to: ", null, null);
                if (file == null) 
                {
                    menu.result = new StringBuilder("Save cancelled - no file selected ");
                    return;
                }
            }

            try (PrintStream out = new PrintStream(file)) 
            {
                out.println(courses.size());
                for (Course course : courses) 
                {
                    course.save(out);
                }

                out.println(students.size());
                for (Student student : students) 
                {
                    student.save(out);
                }

                out.println(tutors.size());
                for (Tutor tutor : tutors) 
                {
                    tutor.save(out);
                }

                out.println(sessions.size());
                for (Session session : sessions) 
                {
                    session.save(out);
                }
                dirty = false;
                menu.result = new StringBuilder("Data saved successfully to: " + file.getName());
            }

        } 
        catch (IOException e) 
        {
            menu.result = new StringBuilder("Error saving file: " + e.getMessage());
        }
    }

    /**
    * Open - restore data from a selected file
    */
    private void open() 
    {
        if (!safeToDiscardData()) 
        {
            menu.result = new StringBuilder("Open operation cancelled - data preserved");
            return;
        }
        try {
            File selectedFile = Menu.selectFile("Select file to open: ", null, null);
            if (selectedFile == null) 
            {
                menu.result = new StringBuilder("Open cancelled - no file selected ");
                return;
            }

            try (Scanner in = new Scanner(selectedFile)) 
            {
                courses.clear();
                students.clear();
                tutors.clear();
                sessions.clear();

                int numCourses = in.nextInt();
                in.nextLine();
                for (int i = 0; i < numCourses; i++) 
                {
                    courses.add(new Course(in));
                }

                int numStudents = in.nextInt();
                in.nextLine();
                for (int i = 0; i < numStudents; i++) 
                {
                    students.add(new Student(in));
                }

                int numTutors = in.nextInt();
                in.nextLine();
                for (int i = 0; i < numTutors; i++) 
                {
                    tutors.add(new Tutor(in));
                }

                int numSessions = in.nextInt();
                in.nextLine();
                for (int i = 0; i < numSessions; i++) 
                {
                    sessions.add(new Session(in));
                }

                file = selectedFile;
                dirty = false;
                menu.result = new StringBuilder("Data loaded successfully from: " + file.getName());
            }

        } 
        catch (IOException e) 
        {
            menu.result = new StringBuilder("Error loading file: " + e.getMessage());
            newz();
        } 
        catch (RuntimeException e) 
        {
            menu.result = new StringBuilder("Error loading file: " + e.getMessage());
            newz(); 
        }
    }

    /**
    * Creates a new course
    */
    private void newCourse() 
    {
        try {
            String dept = Menu.getString("Enter dept: ");
            Integer number = Menu.getInt("Enter course number: ");
        
            Course course = new Course(dept, number);

            if (courses.indexOf(course) == -1) 
            {
                courses.add(course);
                dirty = true;
                menu.result = new StringBuilder("Course created: " + course);
            } 
            else 
            {
                menu.result = new StringBuilder("Course already exists!");
            }
            
        } 
        catch (InvalidCourseException e) 
        {
            menu.result = new StringBuilder("Error: " + e.getMessage());
        }
    }
    /**
    * Creates a new student
    */

    private void newStudent() 
    {
        if (courses.isEmpty()) 
        {
            menu.result = new StringBuilder("Error: No courses defined. Create courses first.");
            return;
        }

        String name = Menu.getString("Student Name: ");
        String email = Menu.getString("Student Email: ");

        Student student = new Student(name, email);
    
        boolean addingCourses = true;
        while (addingCourses)
        {
            System.out.println(Menu.listToString(null, courses, null));
            String input = Menu.getString("Select Course #: ");
            
            if (input.isEmpty()) 
            {
                addingCourses = false;
            } 
            else 
            {
                int index = Integer.parseInt(input);
                if (index >= 0 && index < courses.size()) 
                {
                    Course course = courses.get(index);
                    student.addCourse(course);
                } 
                else 
                {
                    menu.result = new StringBuilder("Invalid selection.");
                }
            }
        }
    
        students.add(student);
        dirty = true;
        menu.result = new StringBuilder("Student created: " + student.getName());
    }

    /**
    * Creates a new tutor
    */
    
    private void newTutor() 
    {
        if (courses.isEmpty()) 
        {
            menu.result = new StringBuilder("Error: No courses defined. Create courses first.");
            return;
        }
        
        String name = Menu.getString("Tutor Name: ");
        String email = Menu.getString("Email: ");
        String bio = Menu.getString("Bio: ");
        int ssn = Menu.getInt("SSN: ");
        
        System.out.println(Menu.listToString(null, courses, null));
        String input = Menu.getString("Select Course #: ");
        
        int courseIndex = Integer.parseInt(input);
        if (courseIndex >= 0 && courseIndex < courses.size()) 
        {
            Course course = courses.get(courseIndex);
            Tutor tutor = new Tutor(name, email, ssn, bio, course);
            tutors.add(tutor);
            dirty = true;
            menu.result = new StringBuilder("Tutor created: " + tutor.getName());
        } 
        else 
        {
            menu.result = new StringBuilder("Invalid selection.");
        }
    }

    /**
    * Creates a new tutoring session
    */

    private void newSession() 
    {
        if (tutors.isEmpty()) 
        {
            menu.result = new StringBuilder("Error: No tutors defined. Create tutors first.");
            return;
        }
        if (students.isEmpty()) 
        {
            menu.result = new StringBuilder("Error: No students defined. Create students first.");
            return;
        }
        
        System.out.println(Menu.listToString(null, courses, null));
        String courseInput = Menu.getString("Select Course #: ");
        
        int courseIndex = Integer.parseInt(courseInput);
        if (courseIndex < 0 || courseIndex >= courses.size()) 
        {
            menu.result = new StringBuilder("Invalid selection.");
            return;
        }
        Course course = courses.get(courseIndex);
        
        System.out.println(Menu.listToString(null, tutors, null));
        String tutorInput = Menu.getString("Select Tutor #: ");
        
        int tutorIndex = Integer.parseInt(tutorInput);
        if (tutorIndex < 0 || tutorIndex >= tutors.size()) 
        {
            menu.result = new StringBuilder("Invalid selection.");
            return;
        }
        Tutor tutor = tutors.get(tutorIndex);

        Session session = new Session(course, tutor);
    
        String date = Menu.getString("Date (e.g., 2025-10-28): ");
        String time = Menu.getString("Start Time (e.g., 14:00): ");
        int duration = Menu.getInt("Duration (minutes): ");
        
        session.setSchedule(date, time, duration);
    
        boolean addingStudents = true;
        while (addingStudents) 
        {
            System.out.println(Menu.listToString(null, students, null));
            String input = Menu.getString("Select Student #: ");
            
            if (input.isEmpty()) 
            {
                addingStudents = false;
            } 
            else 
            {
                int index = Integer.parseInt(input);
                if (index >= 0 && index < students.size()) 
                {
                    Student student = students.get(index);
                    session.addStudent(student);
                } 
                else 
                {
                    menu.result = new StringBuilder("Invalid selection.");
                }
            }
        }
        
        sessions.add(session);
        dirty = true;
        menu.result = new StringBuilder("Session created successfully");
    }

    /**
    * Changes the current view to display different data
    * @param list The list to view (courses, students, tutors, or sessions)
    */

    private void selectView(List<?> list) 
    {
        view = list;
    }

    /**
    * Exits the application
    */
    
    private void quit() 
    {
        if (!safeToDiscardData()) 
        {
            menu.result = new StringBuilder("Quit cancelled - returning to menu");
            return;
        }
        menu.result = null;
    }
    
    /**
    * Bounded wildcard to review a rateable item
    * @param list List of rateable items to review
    */
    private void review(List<? extends Rateable> list) 
    {
        if (list.isEmpty()) 
        {
            menu.result = new StringBuilder("No items to review in this category.");
            return;
        }
        
        Integer selectedIndex = Menu.selectItemFromList("Select an item to review: ", list);
        if (selectedIndex == null) 
        {
            menu.result = new StringBuilder("Review cancelled - no item selected.");
            return;
        }
        
        Rateable item = list.get(selectedIndex);
        
        double averageRating = item.getAverageRating();
        if (Double.isNaN(averageRating)) 
        {
            System.out.println("Average rating: No ratings yet");
        } 
        else 
        {
            System.out.println("Average rating: " + String.format("%.2f", averageRating));
        }
    
        user = login();
        
        if (user != null) 
        {
            Integer stars = Menu.getInt("Enter rating (1-5 stars): ", null, null);
            if (stars != null && stars >= 1 && stars <= 5) 
            {
                String reviewText = Menu.getString("Enter review comment: ");
                
                Comment reviewComment = new Comment(reviewText, user, null);
                
                Rating rating = new Rating(stars, reviewComment);
                item.addRating(rating);
                dirty = true;
            }
        }
        
        Rating[] ratings = item.getRatings();
        if (ratings.length == 0) 
        {
            System.out.println("No ratings to browse.");
        } 
        else 
        {
            String[] ratingDisplay = new String[ratings.length];
            for (int i = 0; i < ratings.length; i++) 
            {
                Rating r = ratings[i];
                String reviewText = r.getReview() != null ? r.getReview().toString() : "No comment";
                if (reviewText.length() > 50) 
                {
                    reviewText = reviewText.substring(0, 47) + "...";
                }
                ratingDisplay[i] = r.getStars() + " stars - " + reviewText;
            }
            
            Integer ratingIndex = Menu.selectItemFromArray("Select a rating to view comments: ", ratingDisplay);
            if (ratingIndex != null) 
            {
                Comment currentComment = ratings[ratingIndex].getReview();
                
                if (currentComment == null) 
                {
                    System.out.println("No comment to browse.");
                } 
                else 
                {
                    while (true) {
                        printExpandedComments(currentComment, 0);
                        
                        StringBuilder prompt = new StringBuilder("(");
                        
                        if (user != null) 
                        {
                            prompt.append("R)eply ");
                        }
                        prompt.append("(L)ogin ");
                        if (currentComment.getInReplyTo() != null) 
                        {
                            prompt.append("(U)p ");
                        }
                        if (currentComment.numReplies() > 0) 
                        {
                            prompt.append("(D)own ");
                        }
                        prompt.append("(M)ain menu? ");
                        
                        String choice = Menu.getString(prompt.toString()).toUpperCase().trim();
                        
                        if (choice.equals("R") && user != null) 
                        {
                            String replyText = Menu.getString("Enter your reply: ");
                            currentComment.addReply(replyText, user);
                            dirty = true;
                        }
                        else if (choice.equals("L")) 
                        {
                            user = login();
                        } 
                        else if (choice.equals("U") && currentComment.getInReplyTo() != null) 
                        {
                            currentComment = currentComment.getInReplyTo();
                        } 
                        else if (choice.equals("D") && currentComment.numReplies() > 0) 
                        {
                            Integer replyChoice = Menu.getInt("Select reply number: ");
                            if (replyChoice != null && replyChoice >= 0 && replyChoice < currentComment.numReplies()) 
                            {
                                currentComment = currentComment.getReply(replyChoice);
                            }
                        } 
                        else if (choice.equals("M")) 
                        {
                            // Main menu
                            break;
                        } 
                        else 
                        {
                            System.out.println("Invalid option.");
                        }
                    }
                }
            }
        }
        
        menu.result = new StringBuilder("Review completed.");
    }

    /**
    * Private method to handle user login with persistent state
<<<<<<< HEAD
=======
    * BONUS: Remembers login and offers different menus based on login state
>>>>>>> f73933b (Implement Bonus Part)
    * @return the logged in Person object, or null if no login
    */
    private people.Person login()
    {
        String[] loginOptions;
        
        if (user == null) 
        {
            loginOptions = new String[]{"Cancel login", "Tutor login", "Student login"};
        } 
        else 
        {
            loginOptions = new String[]{
                "Continue as " + user.getName(),
                "Tutor login", 
                "Student login", 
                "Log out"
            };
        }
        
        Integer loginChoice = Menu.selectItemFromArray("Login options: ", loginOptions);
        
        if (loginChoice == null) 
        {
            return user;
        }
        
        if (user == null) 
        {
            if (loginChoice == 0) 
            {
                return null; 
            } 
            else if (loginChoice == 1) 
            {
                Integer tutorIndex = Menu.selectItemFromList("Select a tutor: ", tutors);
                if (tutorIndex != null) 
                {
                    return tutors.get(tutorIndex);
                }
                return null;
            } 
            else if (loginChoice == 2) 
            {
                Integer studentIndex = Menu.selectItemFromList("Select a student: ", students);
                if (studentIndex != null) 
                {
                    return students.get(studentIndex);
                }
                return null;
            }
        } 
        else 
        {
            if (loginChoice == 0) 
            {
                return user;
            } 
            else if (loginChoice == 1) 
            {
                Integer tutorIndex = Menu.selectItemFromList("Select a tutor: ", tutors);
                if (tutorIndex != null) 
                {
                    return tutors.get(tutorIndex);
                }
                return user; 
            } 
            else if (loginChoice == 2) 
            {
                Integer studentIndex = Menu.selectItemFromList("Select a student: ", students);
                if (studentIndex != null) 
                {
                    return students.get(studentIndex);
                }
                return user; 
            } 
            else if (loginChoice == 3) 
            {
                System.out.println("Logged out.");
                return null;
            }
        }
        return user;
    }

    /**
    * Print indented text with specified indentation level
    */
    private void printIndented(String multiline, int level) 
    {
        String[] strings = multiline.split("\n");
        for (String s : strings) 
        {
            System.out.println("  ".repeat(level) + s);
        }
    }

    /**
    * Print expanded comments recursively
    */
    private void printExpandedComments(Comment c, int level) 
    {
        printIndented(c.toString(), level);
        System.out.println("\n");
        for (int i = 0; i < c.numReplies(); i++) 
        {
            printExpandedComments(c.getReply(i), level + 1);
        }
    }

    /**
    * Converts the current view to a string for display
    * @return Formatted string of current data
    */

    @Override
    public String toString() 
    {
        String header = "";
        if (view == courses) 
        {
            header = "Courses";
        } 
        else if (view == students) 
        {
            header = "Students";
        } 
        else if (view == tutors) 
        {
            header = "Tutors";
        } 
        else if (view == sessions) 
        {
            header = "Sessions";
        }
    
        String underline = "=".repeat(header.length());
    
        StringBuilder sb = new StringBuilder();
        sb.append(header).append("\n");
        sb.append(underline).append("\n");
    
        for (Object obj : view) 
        {
            sb.append(" - ").append(obj).append("\n");
        }
    
        return sb.toString();
    }

    private static void showSplashScreen() 
    {
        String splash = 
                "\n"
                + "*************************************************\n"
                + "*                                               *\n"
                + "*         __  __              _______           *\n"
                + "*        |  \\/  |     /\\     |__   __|          *\n"
                + "*        | \\  / |    /  \\       | |   _   _     *\n"
                + "*        | |\\/| |   / /\\ \\      | |  | | | |    *\n"
                + "*        | |  | |  / ____ \\     | |  | |_| |    *\n"
                + "*        |_|  |_| /_/    \\_\\    |_|   \\__, |    *\n"
                + "*                                      __/ |    *\n"
                + "*                                     |___/     *\n"
                + "*                                               *\n"
                + "*           Welcome to MavTutor System          *\n"
                + "*         A Menu-Driven Tutoring Manager        *\n"
                + "*                                               *\n"
                + "*************************************************\n";

        System.out.println(splash);

        System.out.print("Loading MavTutor");
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000); // wait 1 second
                System.out.print("."); // add a dot each second
            }
        } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    System.out.println("\n");
}

    /**
    * Main method - entry point of the application
    */
    public static void main(String[] args) 
    {
        boolean skipSplash = false;
        if (args.length > 0 && args[0].equalsIgnoreCase("--nosplash")) 
        {
            skipSplash = true;
        }

        if (!skipSplash) 
        {
            showSplashScreen();
        }
        new MavTutor();
    }
}