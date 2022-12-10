import java.util.List;

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

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    // Method to add a friend to the user's friends list for the event
    public void addFriend(String friend) {
        friends.add(friend);
    }

    // Method to remove a friend from the user's friends list for the event
    public void removeFriend(String friend) {
        friends.remove(friend);
    }

    // Method to add a post to the user's timeline for the event
    public void addPost(String post) {
        posts.add(post);
    }

    // Method to log out of the event
    public void logOut() {
        System.out.println("You have been logged out of the event.");
    }
}
