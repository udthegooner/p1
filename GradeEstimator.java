import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	
	//Create new ScoreList of size 10
	ScoreList scoreList = new ScoreList();
	
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
		
		//Creates an array of size 10 to store the categories in scoreList.
		String[] categories = new String[10];
		//Stores the weighted percent score.
		double weightPercentScore = 0.0;
		//Stores the letter grade estimate.
		String letterGrade = "";
		
		//Stores the string for the estimate report.
		String report = "Grade estimate is based on ";
		
		//Adds the number of scores to the string.
		report = report + scoreList.size() + " scores./n";
		
		//Figure out all the different categories in scoreList.
		for (int i = 0; i < scoreList.size(); i++) {
			
			//Holds the category to check it against others.
			String cat = "";
			
			cat = scoreList.get(i).getCategory();
			//Cycles through the existing categories to check if cat already
			//exists, if it doesn't, it is added to the first empty slot in
			//categories.
			for (int j = 0; j < categories.length; j++) {
				if (categories[j] == cat) {
					break;
				}
				else if (categories[j] == "") {
					categories[j] = cat;
					break;
				}
			}
		}
		
		//For each category, add [categoryA weighted average score]% = 
		//[categoryA average score]% * [categoryA weight]% for [categoryA name]
		//to the return string.
		for (int i = 0; i < categories.length; i++) {
			
			//Stores average score %.
			double aveScore = 0;
			//Stores weighted average score.
			double weightAveScore = 0;
			//Stores weight.
			double weight = 0;
			//Stores the total scores in a category.
			ArrayList<Double> scores = new ArrayList<Double>();
			//Stores the category name.
			String cat = "";
			//ScoreIterator for the given category.
			ScoreIterator scoreIterator = new ScoreIterator(scoreList, cat);
			
			cat = categories[i];
			
			//Adds scores to the array of scores, then finds the AVERAGE SCORE %
			while (scoreIterator.hasNext()) {
				scores.add(scoreIterator.next().getPercent());
			}
			for (int j = 0; j < scores.size(); j++) {
				aveScore = aveScore + (scores.get(j));
			}
			aveScore = (aveScore / scores.size());
			
			//TO DO: Gets the weight
			weight = 1.0;
			
			weightAveScore = aveScore * weight;
			weightPercentScore += weightAveScore;
			
			//Adds the new information to our return string
			report += (String.format("%7.2f", weightAveScore) + 
					"% = " + String.format("%5,2f", aveScore) + "% of " +
					String.format("%2.0f", weight) + "% for " + cat + "/n");		
		}
		
		//TO DO: Figures out the gradeEstimate
		
		
		//Adds the footer
		report = report + "--------------------------------/n" + 
				String.format("%7.2f", weightPercentScore) + " weighted"
						+ " percent /nLetter Grade Estimate: " + gradeEstimate);
		return report;
	}
}
