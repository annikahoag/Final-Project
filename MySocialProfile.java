import java.util.Scanner;

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

	//method to view list of friends

	//method to add friend

	//method to remove friend



}//close MySocialProfile


class Event{

//Methods: display next event, display all events, add an event

}//close Event


class Main{
	public static void main(String[]args){

		MySocialProfile profile = new MySocialProfile();
		Scanner scn = new Scanner(System.in);

		boolean run=true;
		boolean run2=true;
		int choose, choose2;

		String name, email, password, post;
		int year;

		//while loop to run Main Menu Screen
		while(run){

			//Note: make this look better later
			System.out.println("Welcome to the Main Menu! Please click: " +
				"\n1 to create a new account." +
				"\n2 to load an existing profile." +
				"\n3 to exit program.");
			choose = scn.nextInt();

			switch(choose){

			//create a new account
			case 1:
				System.out.println("\nYou chose to create a new account.");

				scn = new Scanner(System.in);
				System.out.println("Please enter your full name: ");
				name = scn.nextLine();

				scn = new Scanner(System.in);
				System.out.println("Please enter your email address: ");
				email = scn.nextLine();

				scn = new Scanner(System.in);
				System.out.println("Please enter your password: ");
				password = scn.nextLine();

				scn = new Scanner(System.in);
				System.out.println("Please enter your class year: ");
				year = scn.nextInt();

				profile = new MySocialProfile(name, email, password, year);

				run=false;
				run2=true;
				break;

			//load existing
			case 2:
				System.out.println("\nYou chose to load an exisiting profile.");

				scn = new Scanner(System.in);
				System.out.println("Please enter your email address.");
				email = scn.nextLine();

				scn = new Scanner(System.in);
				System.out.println("Please enter your password.");
				password = scn.nextLine();

				//Note for Everyone: read in info., take that user's info out of text file or report that there's no account with that
				//Note for Annika: need a way to have MySocialProfile object connect to homescreen in some way, have to set "profile" to whatever you read in

				run=false;
				run2=true;
				break;

			//exit
			case 3:
				scn = new Scanner(System.in);
				String end;
				System.out.println("You chose to exit the program. Are you sure you want to do that? Please type Y or N.");
				end = scn.nextLine();
				if (end.equals("Y") || end.equals("y") ){
					System.out.println("Exiting program.");
					run=false;
					run2=false;
				}else{
					continue;
				}
				break;
				
			default:
				break;
			}

		}//close while


		//home screen 
		while (run2){

			System.out.println("\n*next event to take place*");
			//Left off: all printing null b/c no events
			profile.printTimeline();
			System.out.println("*list of all events*");

			scn = new Scanner(System.in);
			System.out.println("\nPlease choose an option.");
			System.out.println("1. Post to timeline " +
				"\n2. Add an event." +
				"\n3. View your list of friends " +
				"\n4. Add/remove a friend. " +
				"\n5. Logout. ");
			choose2 = scn.nextInt();

			switch(choose2){

			//post to timeline
			case 1:
				scn = new Scanner(System.in);
				System.out.println("Please type your new timeline post: ");
				post = scn.nextLine();
				profile.postTimeline(post);

				break;

			//add an event
			case 2:
				break;

			//view list of friends	
			case 3:
				break;

			//add/remove friend
			case 4:
				break;

			//logout
			case 5:
				run2=false;
				break;

			default:
				break;

			}//closes switch

		}//close 2nd while

	}
}//close Main