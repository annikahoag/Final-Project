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

	SinglyLinkedList friends = new SinglyLinkedList();

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
		friends.display();
	} 

	/**
	 * Adds a friend's email to the SinglyLinkedList titled friends
	 * Uses addFirst and addLast method from the SinglyLinkedList class provided by Prof. Tarimo
	 * @param friendEmail -> email ID of the friend to be added the list
	 * @author Annika Hoag using methods by Prof. Tarimo
	 * @since 12/13/2022
	 */ 
	public void addFriend(String friendEmail){

		if (friends.isEmpty()){
			friends.addFirst(friendEmail);
		}else{
			friends.addLast(friendEmail);
		}
	}

	//method to remove friend
	public void removeFriend(String friendEmail){

		boolean found = this.find(friendEmail);

		if (!found){
			System.out.println("Friend could not be found in the list.");
		}else{

		}

	}

	//helper method to remove friend
	private boolean find(String friendEmail){

		//may need to do !.equals instead of !=
		while (friends.getNext() != null){

			if (friends.getElement().equals(friendEmail)){
				//figure out how to remove intermediate value
				return true;
			}else{
				continue;
			}

		}//closes while loop

		return false;
	}//closes fined


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
	String filename = "e:\\mysocialprofile.txt"; 
	MySocialProfile profile = new MySocialProfile();
	
	public UserAccount(){ 	} 
	

	/**
	 * Creates a Main Menu where the user can: 
	 * 1. Sign into their account 
	 * 2. Load into an existing profile 
	 * 3. Exit the program 
	 * These will all be options the user can choose from. 
	 * @return option -> option that the user chose, does this so that the program knows how to proceed
	 * 	1 for create new account, 2 for loading an existing profile, 3 for exiting, -1 if it gets to outside the switch statement
	 * @author Michael Volpi, Matthew Volpi, and Annika Hoag
	 * @since 12/9/2022
	 */

   
    public class UserAccount{
	Scanner s = new Scanner(System.in); 
	String filename = "e:\\account.txt"; 
	public UserAccount(){ 
		try{ 
			System.out.println("-------------------------------");
			System.out.println("1. Create New Account");
			System.out.println("2. Login with existing account");
			System.out.println("-------------------------------");
			System.out.print("Enter Your Choice: ");
			String choice = s.nextLine();
			if(choice.equals("1")){
				createaccount();
			}
		}catch(Exception ex){ 

		}
	} 
	
	public void createaccount(){ 

	public int mainMenu(){
		// System.out.println("I'm in mainMenu");
		s = new Scanner(System.in);
		System.out.println("\n-------------------------------");
		System.out.println("1. Create New Account");
		System.out.println("2. Login with existing account");
		System.out.println("3. Exit program.");
		System.out.println("-------------------------------");
		System.out.print("Enter Your Choice: ");
		int choice = s.nextInt();
		// if(choice == 1){
		// 	// createaccount();
		// }
		return choice;
			
	}

	public void createAccount(){ 
  try{
			Path path = Paths.get(filename.toString());
			OutputStream output = new BufferedOutputStream(Files.newOutputStream(path, APPEND));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

			System.out.print("Enter your username: ");
			String username = s.nextLine();
			System.out.print("Enter password: ");
			String password = s.nextLine();

			writer.write(username + "," + password);

			
			System.out.print("Please enter your full name: ");
			String name = s.nextLine();
			System.out.print("Please enter your password: ");
			String password = s.nextLine();

			//I added these so that we don't forget to include them
			System.out.println("Please enter your email: ");
			String email = s.nextLine();

			System.out.println("Please enter your class year: ");
			int year = s.nextInt();

			//this is me trying to include the other variables in the text file
			//I also changed it to a semicolon since the timeline posts, events, and friend lists will need to be separated by commas
			writer.write(name + ";" + email + ";" + password + ";" + year);
			writer.newLine();
			System.out.println("Account has been successfully created!");
			writer.close();
			output.close();


			new UserAccount();
		}catch(Exception ex){
			System.out.print(ex.getMessage());
		}
	} 

	public static void main(String[]args){
		new UserAccount();
	}
}

}  

			profile = new MySocialProfile();
			// new UserAccount();
		}catch(Exception ex){
			// System.out.println("I am in catch of try catch statement.");
			System.out.print(ex.getMessage());
		}
	}//closes create account

	public MySocialProfile getProfile(){
		return profile;
	} 

}//close UserAccount

class Main{
	
	public static void main(String[]args){
		UserAccount user = new UserAccount();
		MySocialProfile profile = new MySocialProfile();
		Scanner scn = new Scanner(System.in);

		boolean run1=true;
		boolean run2 = true;
		int mainMenuChoice;

		// System.out.println("Before while loop");

		while(run1){

			//starts by calling Main Menu method 
			mainMenuChoice = user.mainMenu();

			// System.out.println(mainMenuChoice + " = mainMenuChoice");

			//create account
			if (mainMenuChoice == 1){
				run2=true;
				user.createAccount();
				profile = user.getProfile();
			
			//login to existing account
			}else if (mainMenuChoice == 2){
				run2=true;

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
						run2 =  true;
						break;

					//add/remove friend
					case 4:
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




