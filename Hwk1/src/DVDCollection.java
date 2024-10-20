//Ibraheem Fawal
//Assignment 1

import java.io.*;
import java.util.Scanner;

public class DVDCollection {

	// Data fields
	
	/** The current number of DVDs in the array */
	private int numdvds;
	
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	
	/** The name of the data file that contains dvd data */
	private String sourceName;
	
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;
	
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	public String toString() {
		// Return a string containing all the DVDs in the
		// order they are stored in the array along with
		// the values for numdvds and the length of the array.
		// See homework instructions for proper format.
		//System.out.println("numdvds = " + numdvds);
		//System.out.println("dvdArray.length = " + dvdArray.length);
		
		String movieTitleRating = "";
		movieTitleRating = movieTitleRating.concat("numdvds = ").concat(Integer.toString(numdvds)).concat("\n");
		movieTitleRating = movieTitleRating.concat("dvdArray.length = ").concat(Integer.toString(dvdArray.length)).concat("\n");
		
		for(int i = 0; i < numdvds; i++) {
			movieTitleRating = movieTitleRating.concat("dvdArray[").concat(Integer.toString(i)).concat("] = ").concat(dvdArray[i].toString().concat("\n"));
		}
		
		return movieTitleRating;
	}

	public void addOrModifyDVD(String title, String rating, String runningTime) {
		// NOTE: Be careful. Running time is a string here
		// since the user might enter non-digits when prompted.
		// If the array is full and a new DVD needs to be added,
		// double the size of the array first.

		//alphabetical order
		//upper case
		
		
		//boolean inArray = false;
		boolean validRating = false;
		
		//title = title.toUpperCase();
		//rating = rating.toUpperCase();
		
		if(rating.equals("G") || rating.equals("PG") || rating.equals("PG-13") || rating.equals("R")) {
			validRating = true;
		}
	    
	    //make sure user inputs are valid
	    if (!validRating || Integer.parseInt(runningTime) <= 0) {
	        System.out.println("Invalid rating or running time.");
	        return;
	    }
	    
	    //modify a dvd if its already in the collection
	    for (int i = 0; i < numdvds; i++) {
	        if (dvdArray[i].getTitle().equals(title)) {
	        	
	        	
	            dvdArray[i].setRating(rating);
	            dvdArray[i].setRunningTime(Integer.parseInt(runningTime));
	            return;
	        }
	    }
	    
	    //double the array if its full
	    if (numdvds == dvdArray.length) {
	        DVD[] newDvdArray = new DVD[dvdArray.length * 2];
	        for (int i = 0; i < dvdArray.length; i++) {
	            newDvdArray[i] = dvdArray[i];
	        }
	        dvdArray = newDvdArray;  // Set the new array as the current array
	    }
	    
	    
	    DVD addDVD = new DVD(title, rating,Integer.parseInt(runningTime));
	    
	    int indexPos = numdvds;
	 
	    //loop to alphabetize the movies
	    for (int i = 0; i < numdvds; i++) {
	        if(addDVD.getTitle().compareTo(dvdArray[i].getTitle()) < 0) {
	            indexPos = i;
	            break;
	        }
	    }
	    
	    for (int i = numdvds; i > indexPos; i--) {
	        dvdArray[i] = dvdArray[i-1];
	    }

	    dvdArray[indexPos] = addDVD;
	    numdvds++;
	}


	public void removeDVD(String title) {
		
		title = title.toUpperCase();  // Convert title to uppercase for consistency
		
		//search through the array till we find the matching movie title
	    for (int i = 0; i < numdvds; i++) {
	    	
	        if (dvdArray[i].getTitle().equals(title)) {
	    
	            for (int j = i; j < numdvds - 1; j++) {
	                dvdArray[j] = dvdArray[j + 1];
	            }
	            
	            //"delete" the duplicated movie title
	            dvdArray[numdvds - 1] = null;
	            //take away 1 from how many dvds there are
	            numdvds--;
	            return;
	        }
	    }
	}

	
	public String getDVDsByRating(String rating) { //first dvd you find

		String dvdRating = "";
		
	    for (int i = 0; i < numdvds; i++) {
	        if (dvdArray[i].getRating().equals(rating)) {
	        	dvdRating = dvdRating.concat(dvdArray[i].toString().concat("\n"));
	        
	        }
	    }
	    return dvdRating;
	
	}

	public int getTotalRunningTime() { //has to return int, no strings

		int totalRunTime = 0;
	    for (int i = 0; i < numdvds; i++) {
	    	totalRunTime = totalRunTime + dvdArray[i].getRunningTime();
	    }
	    return totalRunTime;

	}

	
	public void loadData(String filename) {
		sourceName = filename;
		
		try {
	        File readFile = new File(filename);
	        Scanner readTxt = new Scanner(readFile);

	      
	        while (readTxt.hasNextLine()) {
	        	String[] dvdData = new String[3];
	            int beginning = 0, end = 0, dataPos = 0;
	            String readLine = readTxt.nextLine();
	            
	            //take the data from between the commas
	            for (int i = 0; i < 2; i++) {
	                end = readLine.indexOf(",", beginning);
	                dvdData[dataPos++] = readLine.substring(beginning, end);
	                beginning = end + 1;
	            }

	            dvdData[dataPos] = readLine.substring(beginning);
	            
	            //take the data from the dvdData array and initialize the movie values to them
	            String title = dvdData[0];
	            String rating = dvdData[1];
	            int runningTime = Integer.parseInt(dvdData[2]);

	            
	            addOrModifyDVD(title, rating, Integer.toString(runningTime));
	        }
	        readTxt.close(); //avoid memory leaks
	        
	    } catch (FileNotFoundException e1) {
	        System.out.println(e1);
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}
	
	public void save() {
		
		try {
	        FileWriter writeToSource = new FileWriter(sourceName);

	        for (int i = 0; i < numdvds; i++) {
	            writeToSource.write(dvdArray[i].getTitle() + "," + dvdArray[i].getRating() + "," + dvdArray[i].getRunningTime() + "\n");
	        }
	        writeToSource.close();
	        
	    } catch (IOException e2) {
	        System.out.println(e2);
	    }
	}
}









