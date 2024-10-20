import java.util.*;

/**
 * 	Program to display and modify a simple DVD collection
 */

public class DVDManager {

	public static void main(String[] args) {
		DVDCollection dl = new DVDCollection();
		DVDUserInterface dlInterface = new DVDGUI(dl);
		dlInterface.processCommands();		

		
		//dl.loadData(fileName);
		//dlInterface = new DVDGUI(dl);
		

//		System.out.println("Enter name of data file to load:");
//		String filename = scan.nextLine();			
		

//		System.out.println("Input interface type: C=Console, G=GUI");
//		String interfaceType = scan.nextLine();
//		if (interfaceType.equals("C")) {
//			dlInterface = new DVDConsoleUI(dl);
//			dlInterface.processCommands();
//		} else if (interfaceType.equals("G")) {
//			dlInterface = new DVDGUI(dl);
//			dlInterface.processCommands();
//		} else {
//			System.out.println("Unrecognized interface type. Program exiting.");
//			System.exit(0);
//		}
	}
}
