import java.util.Scanner;

public class MySocialProfile{
	
}//close MySocialProfile

class Event{

}//close Event

class Main{
	public static void main(String[]args){

		//Main menu
		boolean run=true;
		Scanner scn = new Scanner(System.in);
		int choose;

		while(run){

			//Note: make this look better later
			System.out.println("Welcome to the Main Menu! Please click: " +
				"\n1 to create a new account." +
				"\n2 to load an existing profile." +
				"\n3 to exit program.")
			choose = scn.nextInt();

			switch(choose){

			//create a new account
			case 1:

			//load existing
			case 2:

			//exit
			case 3:

			case 4:
				
			default:
			}

		}//close while

	}
}//close Main