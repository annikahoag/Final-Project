import java.util.Scanner;
import java.io.*; 
import java.nio.file.*;
import java.util.*; 
import static java.nio.file.StandardOpenOption.*; 

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

	String [] friends = new String[50]; //array to store list of friends
	int numFriends = 0; //size of friends array
	int mid; //stores value of mid in binary search

	//constructor 
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
	public MySocialProfile(String name, String email, String password, int year){
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


	/**
	 * Displays the user's list of friends
	 * @author Annika Hoag using a method from the SinglyLinkedList class provided by Prof. Tarimo
	 * @since 12/13/2022
	 */  
	public void displayFriends (){
		System.out.println();

		if (numFriends != 0){

			for (int i=0; i < numFriends; i++){
				System.out.print(friends[i] + ", ");
			}

		}
		System.out.println();
	} 


	/**
	 * Decides whether to add or remove the given friend's email ID
	 * Also changes instance variable mid as needed to indicate where email belongs or already is
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
			 */ 
			boolean found = this.find(friendEmail, 0, numFriends-1);

			/**
			 * Uses instance variable mid to store index where friendEmail was found, or where it belongs
			 * Mid will need to be increased by 1 unless mid=0 or is less than the current email at index 0
			 * If mid=0 then the new email should only go at 0 if it's less than the current email at index 0
			 */ 
			if(mid!=0 || friendEmail.compareTo(friends[mid])>0){
				mid++;
			}


			//if email isn't found, add to friends[]
			if (!found){
				this.addFriend(friendEmail);

			//otherwise removed email	
			}else{
				this.removeFriend(friendEmail, mid);
			}


		//if empty no need to see if email is already in friends[]
		}else{
			this.addFriend(friendEmail);
		}

	}//closes addOrRemove

	/**
	 * Adds friend's email to friends[]
	 * Creates a sorted array from A-Z, in order for it to be possible to do binary search
	 * @param friendEmail -> email ID of the friend to be added the list
	 * @author Annika Hoag
	 * @since 12/13/2022
	 */ 
	private void addFriend(String friendEmail){

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
		 * Use helper method to shift values and make room for new email,
		 * 	unless there's no other values in friends[] or the value goes at the end
		 */ 
		if (numFriends>=1 && mid != numFriends){
			this.shift();
		}

		//Store friend's email at mid, increase numFriends
		friends[mid] = friendEmail;
		numFriends++;
	
	}//closes addFriend


	//method to remove friend
	private void removeFriend(String friendEmail, int foundIndex){

		// int foundIndex = this.find(friendEmail, 0, numFriends+1);

		//NEED TO SHIFT VALUES AND REMOVE ONCE THE VALUE HAS BEEN REMOVED 
		
		// boolean found = this.find(friendEmail);

		// if (!found){
		// 	System.out.println("Friend could not be found in the list.");
		// }else{

		// }

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
 	 * Helper method to shift all values in friends[] from numFriends to mid
 	 * This allows a new value to be inserted into friends[] following alphabetical order
 	 * @author Annika Hoag
 	 * @since 12/14/2022
 	 */ 
 	private void shift(){
		
		for (int i = numFriends; i>mid; i--){
			friends[i] = friends[i-1];
		} 		

 	}



//NOT SURE IF I WILL NEED THESE SO THEYRE JUST COMMENTED OUT FOR NOW
	// public void setName(String n){
	// 	name = n;
	// }

	// public void setPassword(String p){
	// 	password = p;
	// }

	// public void setEmail(String e){
	// 	email = e;
	// }

	// public void setYear(int y){
	// 	year = y;
	// }

	// public void setTimelinePosts(String s1, String s2, String s3){

	// 	this.postTimeline(s1);
	// 	this.postTimeline(s2);
	// 	this.postTimeline(s3);

	// }

}//close MySocialProfile


class Event{

//Methods: display next event, display all events, add an event

}//close Event



class UserAccount{
	Scanner s = new Scanner(System.in); 
	Main runmain = new Main();
	String filename = "mysocialprofile.txt"; 
	
	public UserAccount(){ 
		// try{ 
		// 	System.out.println("-------------------------------");
		// 	System.out.println("1. Create New Account");
		// 	System.out.println("2. Login with existing account");
		// 	System.out.println("3. Quit");
		// 	System.out.println("-------------------------------");
		// 	System.out.print("Enter Your Choice: ");
		// 	String choice = s.nextLine();
		// 	if(choice.equals("1")){
		// 		createaccount();
		// 	}else if(choice.equals("2")){
		// 		login();
		// 	}else if(choice.equals("3")){
		// 		System.exit(0);
		// 	}else{
		// 		System.out.print("Invalid option.\n");
		// 		new UserAccount();
		// 	}
		// }catch(Exception ex){ 

		// }
	} 


	//main menu screen
	public int mainMenu(){
		int choice=-1;

		try{ 
			System.out.println("-------------------------------");
			System.out.println("1. Create New Account");
			System.out.println("2. Login with existing account");
			System.out.println("3. Quit");
			System.out.println("-------------------------------");
			System.out.print("Enter Your Choice: ");
			choice = s.nextInt();
			// if(choice.equals("1")){
			// 	createaccount();
			// }else if(choice.equals("2")){
			// 	login();
			// }else if(choice.equals("3")){
			// 	System.exit(0);
			// }else{
			// 	System.out.print("Invalid option.\n");
			// 	new UserAccount();
			// }
		}catch(Exception ex){ 
			choice=-1;
		}

		return choice;
	}


	/**
 	 * Uses the try/catch feature to sign into your account from data on mysocialprofile.txt. If you type in the correc username/password, you will continue to the Main class. 
 	 * If not, you will have to either try again, or make a new account. 
 	 * @author Michael Volpi 
 	 * @since 12/14/2022
 	 */ 
	void login(){
		try{
			Path path = Paths.get(filename);
			InputStream input = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			System.out.println("\n**Login To Your Account**\n");
			System.out.print("Enter your username: ");
			String username = s.nextLine();
			System.out.print("Enter password: ");
			String password = s.nextLine();
			String temp= null;
			String user;
			String pass;
			boolean found = false; 
			while((temp = reader.readLine()) != null){
				String[] account = temp.split(",");
				user = account[0];
				pass = account[1];
				if(user.equals(username) && pass.equals(password)){
					found = true; 
				}
			}
			if (found == true){
				System.out.println("Welcome!");
			}else{
				System.out.println("Invalid username or password. Try again.");
				new UserAccount();
			}
			System.out.println("Press any key to continue...");
			String proc = s.nextLine();
	
		

		}catch(Exception ex){ 
			System.out.print(ex.getMessage());
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
			Path path = Paths.get(filename);
			OutputStream output = new BufferedOutputStream(Files.newOutputStream(path, APPEND));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			System.out.println("\n**Create A New Account**\n");
			System.out.print("Enter your username: ");
			String username = s.nextLine();
			System.out.print("Enter password: ");
			String password = s.nextLine();

			writer.write(username + "," + password);
			writer.newLine();
			System.out.println("Account has been successfully created!");
			writer.close();
			output.close();

			new UserAccount();
		}catch(Exception ex){
			System.out.print(ex.getMessage());
		}
	} 

	// public static void main(String[]args){
	// 	new UserAccount();
	// }


}//closes UserAccount()



class Main{
	
	public static void main(String[]args){
		UserAccount user = new UserAccount();
		MySocialProfile profile = new MySocialProfile();
		Scanner scn = new Scanner(System.in);

		boolean run1=true;
		boolean run2 = true;
		int mainMenuChoice;
		String friendEmail;

		// System.out.println("Before while loop");

		while(run1){

			//starts by calling Main Menu method 
			mainMenuChoice = user.mainMenu();

			// System.out.println(mainMenuChoice + " = mainMenuChoice");

			//create account
			if (mainMenuChoice == 1){
				run2=true;
				user.createaccount();
				// profile = user.getProfile();
			
			//login to existing account
			}else if (mainMenuChoice == 2){
				run2=true;
				user.login();

			//exit program
			}else if (mainMenuChoice == 3){
				run2=false;
				run1=false;
			
			}else{
				System.out.println("Invalid option, please try again.");
				run2=false;
				run1=true;
			}


			//homescreen
			while(run2){
				System.out.println();
				System.out.println("\n*next event to take place");
				profile.printTimeline();
				System.out.println("*list of all events ");

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
						run2 = true;
						break;

					//add an event
					case 2:
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
						run2 = false;
						run1 = true;
						break;

					default:
						run2 = true;
						break;

				}//closes switch


			}//close inner while

		}//close outside while

	}

}

  



