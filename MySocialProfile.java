import java.util.Scanner;
import java.io.*; 
import java.nio.file.*;
import java.util.*; 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.nio.file.StandardOpenOption.*; 

/** 
* Class that stores various variables that represent a user's profile, as on a social media site.
* Used by User Class so that user input can be stored as a MySocialProfile object.
* Used by Main Class so that various methods/operations can be completed. 
* @author Annika Hoag, Matthew Volpi, and Michael Volpi
* @since 12/7/2022
*/
public class MySocialProfile{
	
	//instance variables that define MySocialProfile object 
	String name, email, password, year;

	String [] timelinePosts = new String[3]; //array to store queue of timeline posts
	int numTimeline	= 0; 	//count of number of timeline posts
	int front = 0; 			//index of front of timeline posts queue, index of oldest timeline post

	/**
	 * Stores a priority queue of events in order of their dates
	 * Uses ArrayPriorityQueue class that is a modified version of Professor Tarimo's 
	 */ 
	ArrayPriorityQueue events = new ArrayPriorityQueue(100);

	String [] friends = new String[50]; //array to store list of friends
	int numFriends = 0; //size of friends array
	int mid; //stores index of middle value in binary search, used throughout class which is why it's an instance variable


	//Default constructor 
	public MySocialProfile(){ }

	/**
	 * Constructs a new MySocialProfile object with given name, email, password, and class year
	 * @param name -> full name of user
	 * @param email -> user's email/ID
	 * @param password -> user's password
	 * @param year -> user's class year
	 * @author Annika Hoag
	 * @since 12/7/2022
	 */ 
	public MySocialProfile(String name, String email, String password, String year){
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
	
		//only print posts if the array is not empty
		if (numTimeline>0){

			/**
			 * arrIndex is the index that we use to go through the array
			 * printCount is the number of posts that have already been printed
			 * This allows us to loop through the array starting at the front of the queue,
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

	}//closes printTimeline


	/**
	 * Adds a new post to the queue of posts, represented by an array
	 * Chose to use a queue b/c we only need to display 3 posts at a time,
	 * 	this means we have no use for the oldest post once a new one is added.
	 * Dequeues if array is at capacity of size 3, and then enqueues new post each time.
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
	 * Chose to not have this method not return anything b/c we have no need to remember old timeline posts 
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
		int rearIndex = (front+numTimeline)%timelinePosts.length;	//Use modular arithmetic to find rearIndex
		timelinePosts[rearIndex] = post;		//store new post at rearIndex
		numTimeline++;        					//increase number of posts
	}


	/**
	 * Displays the user's list of friends
	 * @author Annika Hoag
	 * @since 12/13/2022
	 */  
	public void displayFriends (){
		System.out.println();

		//if friends[] isn't empty..
		if (numFriends != 0){

			//print all friends, friends[] is already sorted
			for (int i=0; i < numFriends; i++){
				System.out.print(friends[i] + ", ");
			}

		//if empty, print message to inform user
		}else{
			System.out.println("You have not added any friends yet, click 4 to add a friend!");
		}
		System.out.println();
	} 


	/**
	 * Decides whether to add or remove the given friend's email ID
	 * Uses instance variable mid to determine this
	 * Also mid as needed to indicate where email belongs 
	 * @param friendEmail -> email ID inputted by user that will  either be added or removed
	 * @author Annika Hoag
	 * @since 12/13/2022
	 */ 
	public void addOrRemoveFriend(String friendEmail){
	
		//first check if friends[] is empty
		if(numFriends != 0){

			/** 
			 * Check if friendEmail is already in friends[] using helper method
			 * If found=true, remove that email from friends[],
			 * 	else add that email to friends[]
			 * Uses mid variable calculated in find() to determine where email is or should go
			 */ 
			boolean found = this.find(friendEmail, 0, numFriends-1);


			//if email isn't found, add to friends[]
			if (!found){
				/**
				 * Mid will need to be increased by 1 unless mid=0 or is less than the current email at index 0, 
				 * 	b/c if mid=0 then the new email should only go at 0 if it's less than the current email at index 0
				 */ 
				if(mid!=0 || friendEmail.compareTo(friends[mid])>0){
					mid++;
				}

				//use helper method to add friend
				this.addFriend(friendEmail);

			//otherwise remove email	
			}else{
				//no need to increase mid under any conditions b/c we know that friendEmail is stored there if found
				this.removeFriend(friendEmail);
			}


		//if empty no need to see if email is already in friends[], just add 
		}else{
			this.addFriend(friendEmail);
		}

	}//closes addOrRemove


	/**
	 * Adds friend's email to friends[]
	 * Creates a sorted array from A-Z, in order for it to be possible to do binary search
	 * @param friendEmail -> email ID of the friend to be added to the list
	 * @author Annika Hoag
	 * @since 12/13/2022
	 */ 
	public void addFriend(String friendEmail){

		//If the array is at capacity, we must resize it
		if (numFriends >= friends.length){

			/**
			 * Creates a temp array to store values of original friends[] array
			 * Copies values over
			 */ 
			String [] friendsTemp = new String[friends.length];
			for (int i=0; i<friendsTemp.length; i++){
				friendsTemp[i] = friends[i];
			}

			/**
			 * Re-instantiate friends[] w/ double the orig. length
			 * Copy values in temp. array to new friends[] array
			 */ 
			friends = new String[friends.length * 2];
			for (int i=0; i<friends.length; i++){
				friends[i] = friendsTemp[i];
			}

		}//closes if 


		/**
		 * Shift all values in friends[] from numFriends to mid
		 * 	 unless there's no other values in friends[] or the value goes at the end
 	 	 * Using mid as the stopping point allows a new value to be inserted into friends[] following alphabetical order 
		 */ 
		if (numFriends>=1 && mid != numFriends){
			for (int i = numFriends; i>mid; i--){
				friends[i] = friends[i-1];
				
			} 
		}

		//Store friend's email at mid, increase numFriends
		friends[mid] = friendEmail;
		numFriends++;
	
	}//closes addFriend



	/**
	 * Removes friend's email from friends[]
	 * @param friendEmail -> email ID of the friend to be removed from the list
	 * @author Annika Hoag
	 * @since 12/14/2022
	 */ 
	private void removeFriend(String friendEmail){

		//Shift all values in friends[] from mid to numFriends so that friendEmail will get replaced
		for (int i = mid; i <= numFriends; i++){
			friends[i] = friends [i+1];
		}
		
		/** 
		 * Decreasing numFriends makes it unnecessary to make the last value in friends[] (which is now in 2 spots) null,
		 * 	since no displays will ever make it to that point and it will get replaced if necessary
		 */ 
		numFriends--;
	}


	/**
	 * Helper method to find given friendEmail using recursive binary search
	 * @param friendEmail -> email ID to be searched for 
	 * @param low -> lower boundary of the part of the array we're currently searching
	 * @param high -> upper boundary of the part of the array we're currently searching
	 * @return -> true if friendEmail was found in friends[], false otherwise
	 * @author Annika Hoag
	 * @since 12/13/2022
	 */ 
	private boolean find(String friendEmail, int low, int high){

		//calculate middle index of the part of the array we're currently searching
		mid = (low + high) / 2;

		//Base case -> friendEmail is found at mid
		if (friendEmail.equals(friends[mid]) ){
			return true;

		//Recursive case(s)
		}else{

			//if low>high that means friend is not in the list
			if (low>high){
				return false;
			
			/**
			 * Check if friendEmail is less than friends[mid]
			 * If true, that means friendEmail comes before friends[mid] in the alphabet
			 * Re-call find with upper bound being 1 less than mid
			 */ 
			}else if (friendEmail.compareTo(friends[mid]) < 0){
				System.out.println(friendEmail.compareTo(friends[mid]));
				return find(friendEmail, low, mid-1);
			
			/**
			 * Otherwise friendEmail comes after friends[mid] in the alphabet
			 * Re-call find with lower bound being 1 more than mid
			 */ 
			}else{
				return find(friendEmail, mid+1, high);
			}

		}	

 	}//closes find


 	/**
	 * Adds a new event to the priority queue of events 
	 * @param event -> the event to be added
	 * @author Matthew Volpi and Annika Hoag
	 * @since 12/17/22
	 */ 
	public void addEvent(Event event) {
		/**
		 * first test if the entered date has passed using helper method
		 * if it hasn't passed we add the Even to the priority queue
		 */ 
		if (!this.datePast(event.getDate()) ){
			events.insert(event);

		//otherwise print error message	to inform user date has passed 
		}else{
			System.out.println("Error, this date has already passed");
		}	

	}//closes addEvent


	/**
	 * Prints the events in the priority queue in order of date
	 * @author Matthew Volpi and Annika Hoag
	 * @since 12/17/22
	 */
	public void displayEvents() {
		
		//only print if the priority queue isn't empty
		if (!events.isEmpty()){

			int i=1;

			//loop through priority queue starting at root (index 1) until i>size
			while (i<=events.size()){

				//only print event if the date hasn't passed using helper method
				if (!this.datePast(events.getElement(i)) ){
					System.out.println(events.getElement(i));

				//otherwise delete the min value (if date has passed it must be the newest)	
				}else{
					events.extractMin();
				}
				
				i++;

			}//close while loop
		
		
		//if empty print message to inform user
		}else{
			System.out.println("You don't have any events yet! Click 2 to add an event!");
		}
	
	}//closes displayEvents 


	/**
	 * Prints the next event using helper method to determine if the next event has passed
	 * Next event is represented as the min value of priority queue b/c that's the soonest date
	 * @author Annika Hoag
	 * @since 12/17/2022
	 */ 
	public void displayNextEvent(){

		//only print if the priority queue isn't empty and the minimum date hasn't passed
		if (!events.isEmpty() && !this.datePast(events.getMin().getDate()) ){
			System.out.println(events.getMin().toString());


		}else{

			//NOTE: there is a bug here when it comes to evaluating a date that's passed by a minute or hour
			//remove the minimum value as long the date has passed and the priority queue isn't empty
			while(!events.isEmpty() && this.datePast(events.getMin().getDate()) ){
				events.extractMin();
			}

			//if after this the queue is still not empty then print the min
			if (!events.isEmpty()){
				System.out.println(events.getMin().toString());
			}
		}

	}//closes displayNextEvent


	/**
	 * Helper method to figure out if the given date has passed using Calendar objects
	 * @param dateAndTime -> date to be compared to current date
	 * @return true if given date has passed, false is the given date hasn't passed
	 * @author Matthew Volpi and Annika Hoag
	 * @since 12/16/2022
	 */ 
	private boolean datePast(String dateAndTime){
		//create Calendar object to store the date and time 
		Calendar userCal = Calendar.getInstance();

		//store each piece of the give date as a substring 
		int year = Integer.valueOf(dateAndTime.substring(0,4));
		int month = Integer.valueOf(dateAndTime.substring(5,7));
		int day = Integer.valueOf(dateAndTime.substring(8,10));
		int hour = Integer.valueOf(dateAndTime.substring(11,13));
		int min = Integer.valueOf(dateAndTime.substring(14,16));

		//set substrings to parameters of Calendar object
		userCal.set(year, month-1, day, hour, min);

		//use Calendar to store the current date
		Calendar currentDate = Calendar.getInstance();

		/**
		 * If there are more milliseconds since the currentDate that means more time has passed compared to given date
		 * This means that the given date has already happened
		 */  
		if (currentDate.getTimeInMillis() > userCal.getTimeInMillis()){
			return true;
		}else{
			return false;
		}

	}


	/** 
	 * Return name of MySocialProfile object
	 * @return name -> instance variable storing user's name
	 * @author Annika Hoag 
	 * @since 12/17/2022
	 */ 
	public String getName(){
		return name;
	}

	/** 
	 * Return email of MySocialProfile object
	 * @return email -> instance variable storing user's email
	 * @author Annika Hoag 
	 * @since 12/17/2022
	 */ 
	public String getEmail(){
		return email;
	}

	/** 
	 * Return password of MySocialProfile object
	 * @return password -> instance variable storing user's password
	 * @author Annika Hoag 
	 * @since 12/17/2022
	 */ 
	public String getPassword(){
		return password;
	}

	/** 
	 * Return class year of MySocialProfile object
	 * @return year -> instance variable storing user's class year
	 * @author Annika Hoag 
	 * @since 12/17/2022
	 */ 
	public String getYear(){
		return year;
	}

	/** 
	 * Return list of events of MySocialProfile object
	 * @return String representation of Events priority queue
	 * @author Annika Hoag 
	 * @since 12/17/2022
	 */ 
	public String getEvents(){
		return events.toString();
	}

	/** 
	 * Return list of timeline posts of MySocialProfile object
	 * @return String containing all of the timelinePosts separated by a comma
	 * @author Annika Hoag 
	 * @since 12/17/2022
	 */ 
	public String getTimeline(){
		String timeline="";

		for (int i=0; i<numTimeline; i++){
			timeline = timeline + "," + timelinePosts[i];
		}
		return timeline;
	}

	/** 
	 * Return list of friends of MySocialProfile object
	 * @return String containing all of the friends' emails separated by a comma
	 * @author Annika Hoag 
	 * @since 12/17/2022
	 */ 
	public String getFriends(){
		String friendString="";

		for (int i=0; i<numFriends; i++){
			friendString = friendString + "," + friends[i];
		}
		return friendString;
	}

}//close MySocialProfile



/**
 * Class that represents an event, with a name and date/time.
 * Used by MySocialProfile class to hold priority queue of objects of this class.
 * Used by User class so that events can be stored in the text file.
 * Used by Main class so that methods in MySocialProfile can be called with user input.
 * @author Matthew Volpi, Michael Volpi, and Annika Hoag 
 * @since 12/17/22
 */
	class Event{
	
	//Instance variables for Event object
	String name, dateAndTime;
	
	/** 
	 * Constructor for Event object
	 * @param n -> name of event
	 * @param date -> date/time of event
	 * @author Matthew Volpi, Michael Volpi, and Annika Hoag
	 * @since 12/17/22
	 */ 
	public Event(String n, String date){
		name = n;
		dateAndTime = date;
	}
	
	/**
	 * Compares one event to another event based on their dates, similar to String compareTo
	 * @param other -> the other event to compare to
	 * @return a negative integer if this event's date is earlier, 
	 * 	0 if the dates are the same,
	 *  or a positive integer if this event's date is later
	 * @author Matthew Volpi, Michael Volpi 
	 * @since 12/17/22
	 */
	public int compareTo(Event other) {
		return dateAndTime.compareTo(other.getDate());
	}
	
	/**
	 * Converts Event to String
	 * @return String representation of this event
	 * @author Matthew Volpi 
	 * @since 12/17/22
	 */
	public String toString() {
		return dateAndTime + " " + name;
	}

	/**
	 * Return date/time of Event
	 * @return dateAndTime -> instance variable storing date and time of event
	 * @author Annika Hoag
	 * @since 12/17/2022
	 */  
	public String getDate(){
		return dateAndTime;
	}
	

}//close Event class


/**
 * Class that interacts with an outside text file (mysocialprofile.txt) to store profile information
 * Interacts with MySocialProfile to get data that needs to go into text file and/or give data to MySocialProfile from file
 * Main class calls these methods depending on user input
 * @author Matthew Volpi, Michael Volpi, and Annika Hoag 
 * @since 12/17/22
 */
class UserAccount{
	//instance variables
	Scanner s = new Scanner(System.in); 
	Path path;
	MySocialProfile profile = new MySocialProfile();
	String filename = "mysocialprofile.txt"; 
	
	/**
	 * Constructs a new UserAccount object
	 * @author Michael Volpi
	 * @since 12/17/2022
	 */ 
	public UserAccount(){
		path = Paths.get("mysocialprofile.txt");
	} 


	/**
	 * method that creates the Main Menu. The user is greeted with: 
	 * 1) Create New Account -> an option to create a new profile 
	 * 2) Login with existing account -> an option to sign in to an already existing profile 
	 * 3) Quit -> Exit out of the program
	 * 
	 * @author Michael Volpi 
	 * @since 12/13/22
	 */
	public int mainMenu(){
		int choice=-1;

		try{ 
			s = new Scanner(System.in);
			System.out.println("\n-------------------------------");
			System.out.println("1. Create a new account");
			System.out.println("2. Login with existing account");
			System.out.println("3. Exit program");
			System.out.println("-------------------------------");
			System.out.print("Enter Your Choice: ");
			choice = s.nextInt();
		}catch(Exception ex){ 
			choice=-1;
		}

		return choice;
	}


	/**
 	 * Uses the try/catch feature to sign into your account from data on mysocialprofile.txt. 
 	 * If you type in the correct username/password, you will continue to the Main class. 
 	 * If not, you will have to either try again, or make a new account. 
 	 * It also uses MySocialProfile to create a new profile so that all of the data will be stored there. 
 	 * @author Michael Volpi 
 	 * @since 12/17/2022
 	 */ 
	void login(){
		try{
			s = new Scanner(System.in);
			InputStream input = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			System.out.println("\n**Login To Your Account**\n");
			System.out.print("Enter your email address: ");
			String emailID = s.nextLine();
			System.out.print("Enter password: ");
			String password = s.nextLine();
			
			String temp= null;
			String email;
			String pass;
			boolean found = false; 
			// MySocialProfile profile = null;
			while((temp = reader.readLine()) != null){
				String[] account = temp.split(";");
				email = account[1];
				pass = account[2];
				if(email.equals(emailID) && pass.equals(password)){
					found = true; 
					
					profile = new MySocialProfile(account[0], account[1], account[2], account[3]);

					//load in timeline posts
					temp=null;
					while((temp= reader.readLine()) != ";"){
						account = temp.split(",");
					}
					for (int i=0; i<account.length-1; i++){
						profile.postTimeline(account[i]);
					}

					//load in friends
					temp=null;
					while((temp = reader.readLine()) != ";"){
						account = temp.split(",");
					}
					for (int i=0; i<account.length-1; i++){
						profile.addFriend(account[i]);
					}

					// Load the events for this user
					temp=null;
					while((temp = reader.readLine()) !=";"){
						account = temp.split(",");
					}
					for (int i = 0; i < account.length-1; i++) {
						Event event = new Event(account[i], account[i + 1]);
						profile.addEvent(event);
					}
					break;
				}
			}
			if (found == true){
				System.out.println("Welcome!");
			}else{
				System.out.println("Invalid email or password.");
			}
			
		}catch(Exception ex){ 
			System.out.println("\nInvalid email or password.");
			
		}
	}


	/**
 	 * Creates a method using a try/catch feature to create a username and password for your account. 
 	 * Both your username and password will save to the mysocialprofile.txt file
 	 * @author Michael Volpi 
 	 * @since 12/14/2022
 	 */ 
	public void createaccount(){ 
		try{
			s = new Scanner(System.in);
			OutputStream output = new BufferedOutputStream(Files.newOutputStream(path, APPEND));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			System.out.println("\n**Create A New Account**\n");

			System.out.print("Enter your full name: ");
			String name = s.nextLine();
			System.out.print("Enter password: ");
			String password = s.nextLine();
			System.out.print("Enter your class year: ");
			String classyear = s.nextLine();
			System.out.print("Enter your email: ");
			String email = s.nextLine(); 
			System.out.println();

			profile = new MySocialProfile(name, email, password, classyear);
			writer.write(name + ";" + email + ";" + password + ";" + classyear + ";" + profile.getEvents() + ";" + profile.getTimeline() + ";" + profile.getFriends());
			writer.newLine();
			System.out.println("Account has been successfully created!");
			writer.close();
			output.close();

			new UserAccount();
		}catch(IOException e){
			e.printStackTrace();
		}
	} 


	/**
	 * Stores MySocialProfile info. to text file 
	 * @author Annika Hoag
	 * @since 12/17/2022
	 */ 
	public void logOut(){
		try{
			OutputStream output = new BufferedOutputStream(Files.newOutputStream(path, APPEND));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			writer.write(profile.getName() + ";" + profile.getEmail() + ";" + profile.getPassword() + ";" + profile.getYear() + ";" + profile.getEvents() + ";" + 
				profile.getTimeline() + ";" + profile.getFriends());
			writer.close();
			output.close();

		}catch(IOException e){
			e.printStackTrace();
		}
	}


}//closes UserAccount()



/**
 * Class that contains most of the program's user interface
 * Interacts with MySocialProfile to import user input and create a MySocialProfile with given info.
 * Interacts with Event class to create Event objects based off of user inputted name and date/time
 * Interacts with User class by telling it when to read info. from txt file and when to write new info. to the txt. file
 * @author Matthew Volpi, Michael Volpi, and Annika Hoag 
 * @since 12/17/22
 */
class Main{
	
	public static void main(String[]args){
		UserAccount user = new UserAccount();
		MySocialProfile profile = new MySocialProfile();
		Scanner scn = new Scanner(System.in);

		boolean run1=true; //decides whether or not to show Main Menu
		boolean run2=true; //decides whether or not to show Home Screen
		int mainMenuChoice;
		String friendEmail;

		while(run1){

			//starts by calling Main Menu method 
			mainMenuChoice = user.mainMenu();

			//create account
			if (mainMenuChoice == 1){
				run2=true;
				user.createaccount();
			
			//login to existing account
			}else if (mainMenuChoice == 2){
				run2=true;
				user.login();

			//exit program
			}else if (mainMenuChoice == 3){
				run2=false;
				run1=false;
			
			//default
			}else{
				System.out.println("Invalid option, please try again.");
				run2=false;
				run1=true;
			}


			//homescreen
			while(run2){
				System.out.println();
				System.out.println("-------------------------------");
				
				//display next event, most recent timeline posts, and all events
				System.out.println("\nNext Event: ");
				profile.displayNextEvent();
				System.out.println("\n3 Most Recent Timeline Posts: ");
				profile.printTimeline();
				System.out.println("\nEvents: ");
				profile.displayEvents();
				System.out.println();

				//give user options for what to do next
				System.out.println("\nPlease choose an option.");
				System.out.println("1. Post to timeline " +
					"\n2. Add an event." +
					"\n3. View your list of friends " +
					"\n4. Add/remove a friend. " +
					"\n5. Logout. ");
				int choose = scn.nextInt();

				//determine what actions to take based on user input
				switch(choose){
					//post to timeline
					case 1:
						scn = new Scanner(System.in);
						System.out.println("Please type your new timeline post: ");
						String post = scn.nextLine();
						profile.postTimeline(post);
						run2 = true;
						break;

					//add an event
					case 2:
						//prompt the user for the event details 
						scn = new Scanner(System.in);
						System.out.println("\nEnter the name of the event: ");
						String eventName = scn.nextLine();
						System.out.println("\nEnter the date of the event (YYYY-MM-DD-HH-MM):");
						String eventDate = scn.nextLine();

						//Creates a new event object with the user-provided details 
						Event newEvent = new Event(eventName, eventDate);

						//addEvent to priority queue
						profile.addEvent(newEvent);					

						run2 = true;
						break;

					//view list of friends	
					case 3:
						profile.displayFriends();
						run2 =  true;
						break;

					//add/remove friend
					case 4:
						scn = new Scanner(System.in);
						System.out.println("Please enter the email address of the friend you wish to find. " +
							"\nPlease type the email with exact characters (lowercase, uppercase, etc.) and no extra spaces. " +
							"\nIf the email address is found that friend will be removed, otherwise it will be added to your list of friends");
						friendEmail = scn.nextLine();

						profile.addOrRemoveFriend(friendEmail);

						run2 = true;
						break;

					//logout
					case 5:
						user.logOut();
						run2 = false;
						run1 = true;
						break;

					default:
						System.out.println("Invalid option, please try again.");
						run2 = true;
						break;

				}//closes switch


			}//close inner while

		}//close outside while

	}

}
