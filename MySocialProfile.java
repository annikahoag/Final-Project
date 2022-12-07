import java.util.Scanner;

public class MySocialProfile{
	
	//instance variables
	String name, email, password;
	int year;
	//add events
	String [] timelinePosts;

	//constructor 
	public MySocialProfile(String name, String email, String password, int year){
		name = name;
		email = email;
		password = password;
		year = year;
	}


}//close MySocialProfile

class Event{

}//close Event

class Main{
	public static void main(String[]args){

		//Main menu
		boolean run=true;
		boolean run2=true;
		Scanner scn = new Scanner(System.in);
		int choose, choose2;

		String name, email, password;
		int year;

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

				MySocialProfile profile = new MySocialProfile(name, email, password, year);

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
				//Note for Annika: need a way to have MySocialProfile object connect to homescreen in some way

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
			System.out.println("*3 most recent timeline posts*");
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
				break;

			default:
				break;

			}//closes switch

		}//close 2nd while

	}
}//close Main