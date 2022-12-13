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
		try{
			Path path = Paths.get(filename.toString());
			OutputStream output = new BufferedOutputStream(Files.newOutputStream(path, APPEND));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
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

	public static void main(String[]args){
		new UserAccount();
	}
}

}  




