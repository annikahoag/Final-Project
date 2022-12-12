import java.util.Scanner;
import java.io.*;  
/** 
* Class that stores objects representing a user's profile, as on a social media site.
* @author Annika Hoag, Matthew Volpi, and Michael Volpi
* @since 12/7/2022
*/
public class MySocialProfile{
	
	//instance variables for MySocialProfile object
	String name, email, password;
	int year;
	//add events

	String [] timelinePosts = new String[3]; //array to store queue of timeline posts
	int numTimeline	= 0; 	//count of number of timeline posts
	int front = 0; 			//index of front of queue holding the timeline posts, oldest timeline post

	SinglyLinkedList friends = new SinglyLinkedList();

	//constructor 
	public MySocialProfile(){ }

	public MySocialProfile(String name, String password, String email, int year){
		name = name;
		email = email;
		password = password;
		year = year;
	}


	/**
	 * Prints the array storing the user's timeline posts
	 * @author Annika Hoag
	 * @since 12/8/2022
	 */
	public void printTimeline(){
		System.out.println();

		//only print posts if the array is not empty
		if (numTimeline>0){

			/**
			 * arrIndex is the index that goes through the array
			 * printCount is the number of posts that have already been printed
			 * this allows us to loop through the array starting at the front of the queue,
			 * 	and ending at the rear while also making sure not to print any null values.
			 */ 
			int arrIndex = front;		
			int printCount=0;	

			//loop as long as the number of posts that have been printed are less than total number of posts
			while(printCount < numTimeline){

				//print value at arrIndex and a comma
				System.out.print(timelinePosts[arrIndex] + ", ");
				
				//modular arithmetic to have arrIndex wrap back to beginning of array if necessary
				arrIndex = (arrIndex+1) % timelinePosts.length;

				//increase number of posts already printed
				printCount++;

			}//close while loop

			System.out.println();


		//if array is empty print message to inform the user
		}else{
			System.out.println("You have no timeline posts yet! Click 1 to post on your timeline!");
		}

		System.out.println();
	}


	/**
	 * Adds a new post to the queue of posts, represented by an array
	 * Chose to use a queue b/c we only need to display 3 posts at a time,
	 * 	so we have no use for the oldest post once a new one is added.
	 * Dequeues every time a new post is added (if array is not empty), and then enqueues new post
	 * @param post -> the user input storing the new post
	 * @author Annika Hoag
	 * @since 12/8/2022
	 */ 
	public void postTimeline(String post){
		
		if (numTimeline == 3){ 	//only dequeue if queue is full
			this.dequeue();		//remove oldest timeline post
		}
		this.enqueue(post);		//add newest timeline post each time

	}

	
	/** 
	 * Helper method to remove the first element of the queue
	 * Chose to not have this method return anything b/c we have no need to remember old timeline posts 
	 * Modified from dequeue method we received from Prof. Tarimo
	 * @author Annika Hoag
	 * @since 12/8/2022
	 */ 
	private void dequeue() {
		timelinePosts[front] = null;				//old front now stores null	
		front = (front+1)%timelinePosts.length;		//the new front is the next index in the array, or wraps to front
		numTimeline--;								//decrease number of posts
	}


	/** 
	 * Helper method to add an element to the queue 
	 * Modified from enqueue method we received from Prof. Tarimo
	 * @param post -> the user input storing the new post
	 * @author Annika Hoag
	 * @since 12/8/2022
	 */ 
	private void enqueue(String post) {
		int rearIndex = (front+numTimeline)%timelinePosts.length;	//Use modular arithmetic
		timelinePosts[rearIndex] = post;		//store new post at rearIndex
		numTimeline++;        					//increase number of posts
	}

	//method to view list of friends

	//method to add friend

	//method to remove friend

	public void setName(String n){
		name = n;
	}

	public void setPassword(String p){
		password = p;
	}

	public void setEmail(String e){
		email = e;
	}

	public void setYear(int y){
		year = y;
	}

	public void setTimelinePosts(String s1, String s2, String s3){

		this.postTimeline(s1);
		this.postTimeline(s2);
		this.postTimeline(s3);

	}

}//close MySocialProfile


class Event{

//Methods: display next event, display all events, add an event

}//close Event


class Main{
	MySocialProfile profile;
	//Note: I think you need to put static back
    public void main(String[] args) {
        // MySocialProfile profile;
       	int choice;
        boolean run1=true;
        boolean run2=true;

        while(run1){
	        //Main Menu 
	        choice = this.mainMenu();

	        //value if program goes outside switch statement or user wishes to exit program
	        if (choice==-1 || choice==3){
	        	System.out.println("Program is ending.");
	        	run1=false;
	        
	        //user either created a new profile or loaded an existing one
	        }else{

	        	while(run2){
	        		run2 = this.homeScreen();
	        	}

	        }
	        run1=true;
	    }//closes outside while
    
    }//closes public static void

	/**
	 * Creates a Main Menu where the user can: 
	 * 1. Sign into their account 
	 * 2. Load into an existing profile 
	 * 3. Exist the program 
	 * 
	 * These will all be options the user can choose from. 
	 * @return option -> option that the user chose, does this so that the program knows how to proceed
	 * 	1 for create new account, 2 for loading an existing profile, 3 for exiting, -1 if it gets to outside the switch statement
	 * @author Michael Volpi, Matthew Volpi, and Annika Hoag
	 * @since 12/9/2022
	 */
    public int mainMenu() {
        // Prompt the user to select an option from the main menu
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Main Menu: ");
        System.out.println("1. Create a new account/profile");
        System.out.println("2. Load an existing profile");
        System.out.println("3. Exit the program");
        System.out.println("Enter the number of the option you want to select: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                // If the user selects option 1, create a new account/profile
                createAccount();
                return 1;
                break;
            case 2:
                // If the user selects option 2, load an existing profile (if one exists in the file)
                loadProfile();
                return 2;
                break;
            case 3:
                // If the user selects option 3, exit the program
                System.out.println("Goodbye!");
                // System.exit(0);
                return 3;
                break;
            default:
                // If the user enters an invalid option, print an error message and return to the main menu
                System.out.println("Invalid option. Please try again.");
                mainMenu();
        }
        return -1;
    }//closes main menu

    //Note: have to add HomeScreen similar to Main Menu

	/**
	 * Creates a method where the user can create their own account with specified details
	 * @author Michael Volpi 
	 * @since 12/10/2022
	 */
    public void createAccount() {
        // Prompt the user for their account details and create a new MySocialProfile for them
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your full name: ");
        String userName = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your class year: ");
        int classYear = scanner.nextInt();
        profile = new MySocialProfile(userName, password, email, classYear);

        // Write the user's profile to the mysocialprofile.txt file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mysocialprofile.txt", true));
            writer.write(profile.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }//close createAccount

    /**
	 * Creates a method where you can sign into your account with data saved in the mysocialprofile.txt file
	 * @author Michael Volpi 
	 * @since 12/9/2022
	 */
    public void loadProfile() {
	    // Prompt the user for their username and password and check if they have an existing profile in the mysocialprofile.txt file
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Enter your email ID: ");
	    String email = scanner.nextLine();
	    System.out.println("Enter your password: ");
	    String password = scanner.nextLine();

	    try {
	        // Read the mysocialprofile.txt file line by line
	        Scanner fileScanner = new Scanner(new File("mysocialprofile.txt"));
	        while (fileScanner.hasNextLine()) {
	            String line = fileScanner.nextLine();
	            // Split the line into fields separated by commas
	            String[] fields = line.split(";");

	            /**
	             * Note sure if this is right, but I needed to make this work with my code so I just did stuff that worked:
	             * I also made a ; the separator idk how that works tho
	             * Field 0 = name
	             * Field 1 = email
	             * Field 2 = password
	             * Field 3 = class year
	             * Field 4 = events
	             * Field 5 = timeline posts
	             * Field 6 = friends list
	             */ 

	            if (fields[1].equals(email) && fields[2].equals(password)) {
	                // If the username and password match an existing profile, load the user's profile and display the details to the user

	                // profile = new MySocialProfile(fields[0], fields[1], fields[2]);
	            	
	            	//temporary way to store existing profile info in the object we're currently using
	            	profile.setName(field[0]);
	            	profile.setEmail(field[1]);
	            	profile.setPassword(field[2]);
	            	profile.setYear((int)field[3]);


	                System.out.println("Welcome, " + profile.name() + "!");
	                System.out.println("Your profile details: ");
	                // System.out.println("Username: " + profile.username());
	                System.out.println("Password: " + profile.password());
	                System.out.println("Email: " + profile.email());
	                System.out.println("Class year: " + profile.classyear());
	                return;
	            }
	        }
	        // If the username and password do not match an existing profile, print an error message and return to the main menu
	        System.out.println("Username and password do not match an existing profile. Please try again.");
	        mainMenu();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	}//close loadProfile


	public boolean homeScreen(){
		System.out.println("\n*next event to take place*");
		profile.printTimeline();
		System.out.println("*list of all events*");

		Scanner scn = new Scanner(System.in);
		System.out.println("\nPlease choose an option.");
		System.out.println("1. Post to timeline " +
			"\n2. Add an event." +
			"\n3. View your list of friends " +
			"\n4. Add/remove a friend. " +
			"\n5. Logout. ");
		int choose = scn.nextInt();

		switch(choose){

			//post to timeline
			case 1:
				scn = new Scanner(System.in);
				System.out.println("Please type your new timeline post: ");
				String post = scn.nextLine();
				profile.postTimeline(post);
				return true;
				break;

			//add an event
			case 2:
				return true;
				break;

			//view list of friends	
			case 3:
				return true;
				break;

			//add/remove friend
			case 4:
				return true;
				break;

			//logout
			case 5:
				return false;
				break;

			default:
				return true;
				break;

			}//closes switch

	}//closes homeScreen



	// }//closes public static void
}//close Main