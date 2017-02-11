import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          p1
// FILE:             GradeEstimator.java
//
// Authors: Lacy, clacy@wisc.edu, Lecture 001
// Author1: Matthew Perry, mperry3@wisc.edu, Lecture 001
// Author2: (name2,email2,netID2,lecture number2)
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: avoid web searches to solve your problems, but if you do 
// search, be sure to include Web URLs and description of 
// of any information you find. 
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * (Write a succinct description of this class here. You should avoid
 * wordiness and redundancy. If necessary, additional paragraphs should
 * be preceded by <p>, the html tag for a new paragraph.)
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author (your name)
 */
public class GradeEstimator {
	/**
	 * Main method
         *
	 * PRECONDITIONS: (i.e. the incoming list is assumed to be non-null)
	 * 
	 * POSTCONDITIONS: (i.e. the incoming list has been reordered)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 * @throws GradeFileFormatException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {
		
		//Estimate Report String
		String estReport = "";
		
		//Throws exception if not exactly one command line argument is given. 
		if (args.length != 1) {
			System.out.println(Config.USAGE_MESSAGE);
		}
		
		//If a command line argument is given, create a GradeEstimator,
		//catching any errors that may arise
		else {
			try {
				createGradeEstimatorFromFile(args[0]);
			}
			catch (FileNotFoundException e){
				System.out.println(e);
				System.exit(0);
			}
			catch (GradeFileFormatException e){
				System.out.println(e);
				System.exit(0);
			}
		}
		
		//If a GradeEstimator was successfully created, produce a grade
		//estimate.
		estReport = getEstimateReport();
		System.out.println(estReport);
	}
	
	
	
	/**
	 * createGradeEstimatorFromFile
	 * 
	 * This method is responsible for creating a GradeEstimator 
	 * by reading an input from a file. 
	 *
	 * Throws checked exception: exception must be declared in method header
	 * using throws clause or using try/catch block.
	 *
	 *
	 * PRECONDITIONS: non-null value for gradeInfo
	 * 
	 * POSTCONDITIONS: newly created instance of GradeEstimator is returned
	 *
	 * @param gradeInfo         String name of input file
	 * @return gradeEstimator   Reference to GradeEstimator object that holds 
	 * 			    all of the data needed to estimate a letter grade
	 */
	public static GradeEstimator createGradeEstimatorFromFile(String gradeInfo) 
	throws FileNotFoundException, GradeFileFormatException  {
		
        //Create new ScoreList of size 10
		ScoreList scoreList = new ScoreList();
		
		try {
			File file = new File(gradeInfo);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			//Create new Scanner object to read input from file
			Scanner scnr = new Scanner(file);
			
			while (scnr.hasNextLine()) {
                             //To be done: need to get Score to add
				scoreList.add();
			}
		} catch (FileNotFoundException e) {
			
		}
	
		//create a new instance of the GradeEstimator class
		GradeEstimator gradeEstimator = new GradeEstimator();
		
		//Return a reference to the newly constructed instance.
		return gradeEstimator;
	}
	
	/**
	 * getEstimateReport
     *
	 * PRECONDITIONS: (i.e. the incoming list is assumed to be non-null)
	 * 
	 * POSTCONDITIONS: (i.e. the incoming list has been reordered)
	 *
	 * @return estimate Report     Formatted String representing grade estimate
	 */
	public String getEstimateReport() {
                //String.format("%7.2f", weightedAvgScores);
                //String.format("%5.2f", unweightedAvgScores);
		return estimateReport;
	}
}
