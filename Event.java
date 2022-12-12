import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Event {
    // Instance variables for the event details, the user's friends list, and the user's timeline of posts
    private String name;
    private String location;
    private String date;
    private List<String> friends;
    private List<String> posts;

    // Constructor that takes the event details as parameters
    public Event(String name, String location, String date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    // Getters and setters for the event details, the user's friends list, and the user's timeline of posts
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
    // Method to log out of the event
    public void exitEvent() {
        System.out.println("You have been logged out of the event.");
    }


}
