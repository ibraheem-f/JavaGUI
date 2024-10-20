import javax.swing.*;
import java.awt.*;


/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */

public class DVDGUI implements DVDUserInterface {
	 
	 private DVDCollection dvdlist;
	 
	 public DVDGUI(DVDCollection dl)
	 {
		 dvdlist = dl;
	 }
	 
	 public void processCommands()
	 {
		 String fileName = JOptionPane.showInputDialog("Enter file name: ");
		 
		 if (fileName != null && !fileName.isEmpty()) {
		        dvdlist.loadData(fileName);
		    } else {
		        JOptionPane.showMessageDialog(null, "Invalid file name. Exiting.");
		        System.exit(0);
		    }
		 
		 String[] commands = {"Show DVD List!","Add/Modify DVD",
				 	"Remove DVD",
				 	"Get DVDs By Rating",
				 	"Get Total Running Time",
				 	"Exit and Save"};
		 
		 int choice;
		 
		 do {
			 choice = JOptionPane.showOptionDialog(null,
					 "Select a command", 
					 "DVD Collection", 
					 JOptionPane.YES_NO_CANCEL_OPTION, 
					 JOptionPane.QUESTION_MESSAGE, 
					 null, 
					 commands,
					 commands[commands.length - 1]);
		 
			 switch (choice) {
			 	case 0: doShowDVDList(); break;
			 	case 1: doAddOrModifyDVD(); break;
			 	case 2: doRemoveDVD(); break;
			 	case 3: doGetDVDsByRating(); break;
			 	case 4: doGetTotalRunningTime(); break;			  
			 	case 5: doSave(); break;
			 	default:  // do nothing
			 }
			 
		 } while (choice != commands.length-1);
		 System.exit(0);
	 }
	 
	 
	 private void doShowDVDList() {
		    // Get the DVD collection as a string
		    String dvdListString = dvdlist.toString();

		    // Split the string by newline characters to get individual DVDs
		    String[] dvdLines = dvdListString.split("\n");

		    // Create a new JPanel to hold the buttons for DVDs
		    JPanel dvdListPanel = new JPanel();
		    dvdListPanel.setLayout(new BoxLayout(dvdListPanel, BoxLayout.Y_AXIS)); // Arrange buttons vertically

		    // Define the generic placeholder image path
		    String placeholderIconPath = "/Users/ibraheemfawal/Desktop/pics/DVDICON.jpg"; // Replace with actual path to the placeholder

		    // Iterate over the DVD list entries and create buttons for each DVD title
		    for (String dvdEntry : dvdLines) {
		        // Assuming the format is "dvdArray[i] = Title/Rating/RunningTime"
		        String[] dvdDetails = dvdEntry.split("/");  // Split by '/' to separate title, rating, and running time
		        if (dvdDetails.length < 3) continue;  // Skip if the format is incorrect

		        // Extract the title (assuming "dvdArray[i] = Title")
		        String titlePart = dvdDetails[0];  // The first part contains "dvdArray[i] = Title"
		        String[] titleSplit = titlePart.split("=");  // Split by '=' to isolate the title
		        if (titleSplit.length < 2) continue;  // Skip if the format is incorrect
		        String title = titleSplit[1].trim();  // The actual title is after the "="

		        // Create a JButton for each DVD title
		        JButton titleButton = new JButton(title);

		        // Load the image icon for the DVD title or fallback to the placeholder icon
		        String imagePath = "/Users/ibraheemfawal/Desktop/pics/" + title + ".jpg";  // Adjust path for actual images
		        ImageIcon dvdIcon;
		        try {
		            dvdIcon = new ImageIcon(imagePath); // Attempt to load the individual image
		            if (dvdIcon.getIconWidth() == -1) {
		                throw new Exception();  // Trigger fallback to placeholder
		            }
		        } catch (Exception e) {
		            dvdIcon = new ImageIcon(placeholderIconPath);  // Use placeholder if the image isn't found
		        }

		        titleButton.setIcon(dvdIcon);  // Set the icon for the button

		        // Add an ActionListener to display the DVD details and allow editing when clicked
		        titleButton.addActionListener(e -> {
		            String rating = dvdDetails[1].trim();  // Get the rating
		            String runningTime = dvdDetails[2].trim();  // Get the running time

		            // Show the DVD details in a message dialog
		            JOptionPane.showMessageDialog(null, "Title: " + title + "\n" +
		                                                    "Rating: " + rating + "\n" +
		                                                    "Running Time: " + runningTime, "Movie Info", JOptionPane.INFORMATION_MESSAGE);

		            // Ask the user if they want to edit the movie
		            int choice = JOptionPane.showConfirmDialog(null, "Do you want to edit this movie?", "Edit Movie", JOptionPane.YES_NO_OPTION);
		            if (choice == JOptionPane.YES_OPTION) {
		                // Request the new rating
		                String newRating = JOptionPane.showInputDialog("Enter rating for " + title);
		                if (newRating == null) {
		                    return;  // dialog was cancelled
		                }
		                newRating = newRating.toUpperCase();

		                // Request the running time
		                String newTime = JOptionPane.showInputDialog("Enter running time for " + title);
		                if (newTime == null) {
		                    return;  // dialog was cancelled
		                }

		                // Add or modify the DVD (assuming the rating and time are valid)
		                dvdlist.addOrModifyDVD(title, newRating, newTime);
		            }
		        });

		        // Add the button to the panel
		        dvdListPanel.add(titleButton);
		    }

		    // Scrolling feature
		    JScrollPane scrollPane = new JScrollPane(dvdListPanel);
		    scrollPane.setPreferredSize(new Dimension(500, 400));

		    // Display the list of DVDs in a message dialog
		    JOptionPane.showMessageDialog(null, scrollPane, "Select a DVD!", JOptionPane.PLAIN_MESSAGE);
		}

	 
	private void doAddOrModifyDVD() {

		// Request the title
		String title = JOptionPane.showInputDialog("Enter title");
		if (title == null) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();
		
		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating for " + title);
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		
		// Request the running time
		String time = JOptionPane.showInputDialog("Enter running time for " + title);
		if (time == null) {
		}
		
                // Add or modify the DVD (assuming the rating and time are valid
                dvdlist.addOrModifyDVD(title, rating, time);
                
                // Display current collection to the console for debugging
                //System.out.println("Adding/Modifying: " + title + "," + rating + "," + time);
                //System.out.println(dvdlist);
		
	}
	
	private void doRemoveDVD() {

		// Request the title
		String title = JOptionPane.showInputDialog("Enter title");
		if (title == null) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();
		
                // Remove the matching DVD if found
                dvdlist.removeDVD(title);
                
                // Display current collection to the console for debugging
                //System.out.println("Removing: " + title);
                //System.out.println(dvdlist);

	}
	
	private void doGetDVDsByRating() {

		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating");
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		
                String results = dvdlist.getDVDsByRating(rating);
                //System.out.println("DVDs with rating " + rating);
                //System.out.println(results);

	}

        private void doGetTotalRunningTime() {
                 
                int total = dvdlist.getTotalRunningTime();
                //System.out.println("Total Running Time of DVDs: ");
                //System.out.println(total);
                
        }

	private void doSave() {
		
		dvdlist.save();
		
	}
		
}
