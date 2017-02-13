import java.io.File;
import java.io.FileInputStream;
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
 * GradeEstimator class takes a text document of grade information
 * and prints out a succinct overview of your overall grade,
 * as well as your performance in individual categories.
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Matthew Perry
 */
public class GradeEstimator {
	
	//Boolean to show whether or not args were passed in
	boolean argsExist = true;
	//Create new ScoreList of size 10
	ScoreList scoreList = new ScoreList();
	//Stores letter grades
	String[] letterGrades;
	//Stores cutoffs
	String[] cutOffString;
	//Stores categories
	String[] catNames;
	//Stores weights
	String[] weightString;
	//Stores doubles of cutOffString.
	ArrayList<Double> cutOffs = new ArrayList<Double>();
	//Stores doubles of WeightString.
	ArrayList<Double> weights = new ArrayList<Double>();
	//Stores the created gradeEstimator.
	static GradeEstimator gradeEstimator = new GradeEstimator();
	
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
		
		//Throws exception if not exactly one command line argument is
		//given and assigns variables using Config.java.
		if (args == null || args.length != 1) {
			System.out.println(Config.USAGE_MESSAGE);
			gradeEstimator.argsExist = false;
			try {
				gradeEstimator = emptyReportCreator();
			}
			catch (FileNotFoundException e){
				System.out.println(e);
				System.exit(0);
			}
			catch (GradeFileFormatException e){
				System.out.println(e);
				System.exit(0);
			}

		} else {
			try {
				gradeEstimator = createGradeEstimatorFromFile(args[0]);
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
		
		//If a GradeEstimator was successfully created and, args was not full,
		//produce a grade estimate.
		if (estReport == "") {
			estReport = gradeEstimator.getEstimateReport();
		}
		
		//Print out the report.
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
		
		//File input stream.
		FileInputStream fileByteStream = null;
		//Scanner Object.
		Scanner inFS = null;
		//Holds entire file in long string.
		String fileString = "";
		//Holds each individual line of the file.
		String[] fileLines;
		//Holds Score info from file
		String[] scoreInfo;
		//Holds max points as double.
		Double maxPoints;
		//Holds possible points as double
		Double possiblePoints;
		//Stores the GradeEstimator object to be returned.
		GradeEstimator gradeEstimator = new GradeEstimator();
		
		//Pulls in data from file
		fileByteStream = new FileInputStream(gradeInfo);
		inFS = new Scanner(fileByteStream);
		
		//Puts each new line of data from file in long string, then breaks up
		//that string into an array of lines.
		while (inFS.hasNextLine()) {
			fileString = fileString.concat("\n" + inFS.nextLine());
		}
		fileLines = fileString.split("\n");
		
		for (int i = 1; i < fileLines.length; i++) {
			fileLines[i - 1] = fileLines[i];
			if (i == fileLines.length - 1) {
				fileLines[i] = "";
			}
		}
		
		//Removes the comments
		for (int i = 0; i < fileLines.length; i++) {
			int poundIndex = fileLines[i].indexOf("#");
			if (poundIndex > 0) {
				fileLines[i] = fileLines[i].substring(0, poundIndex);
			}
		}
				
		
		//Stores letter grades, cutoffs, categories, weights in arrays, splits
		//by whitespace.
		gradeEstimator.letterGrades = fileLines[0].split("\\s+");
		gradeEstimator.cutOffString = fileLines[1].split("\\s+");
		gradeEstimator.catNames = fileLines[2].split("\\s+");
		gradeEstimator.weightString = fileLines[3].split("\\s+");
		
		//Parse doubles from the cutOffs and weights
		for (int i = 0; i < gradeEstimator.cutOffString.length; i++) {
			gradeEstimator.cutOffs.add(Double.parseDouble(gradeEstimator.cutOffString[i]));
		}
		for (int i = 0; i < gradeEstimator.weightString.length; i++) {
			gradeEstimator.weights.add(Double.parseDouble(gradeEstimator.weightString[i]));
		}
		
		//Cycles through remaining lines to add new scores to score list
		for (int i = 4; i < fileLines.length - 1; i++) {
			//First break line into name, points earned, points possible.
			scoreInfo = fileLines[i].split(" ");
			
			//Parse that data out, then use it to add score.
			possiblePoints = Double.parseDouble(scoreInfo[1]);
			maxPoints = Double.parseDouble(scoreInfo[2]);
			gradeEstimator.scoreList.add(new Score(scoreInfo[0], possiblePoints, maxPoints));
		}
		
		return gradeEstimator;
	}
	
	/**
	 * getEstimateReport returns a report as a string providing information
	 * on a students grade percentages, assignments, and overall estimated
	 * letter grade.
	 * 
	 *
	 * @return estimate Report     Formatted String representing grade estimate
	 */
	public String getEstimateReport() {
		
		//Stores the weighted percent score.
		double weightPercentScore = 0.0;
		//Stores the letter grade estimate.
		String letterGrade = "";
		//Stores the string for the estimate report.
		String report = "Grade estimate is based on ";
		
		//Adds the number of scores to the string.
		report += scoreList.size() + " scores.\n";
		
		//For each category, add [categoryA weighted average score]% = 
		//[categoryA average score]% * [categoryA weight]% for [categoryA name]
		//to the return string.
		for (int i = 0; i < gradeEstimator.catNames.length; i++) {
			
			//Stores the sum of percentages
			double sumAveScores = 0;
			//Stores average score %.
			double aveScore = 0.0;
			//Counts number of scores
			double numScores = 0;
			//Stores weighted average score.
			double weightAveScore = 0.0;
			//Stores the category name.
			String cat = gradeEstimator.catNames[i];
			//ScoreIterator for the given category.
			ScoreIterator scoreIterator = new ScoreIterator(scoreList, cat);
			
			//Finds the average score for the given category
			for (int k = 0; k < gradeEstimator.scoreList.size(); k++) {
				if (scoreList.get(k).getCategory().equals(cat.substring(0, 1))) {
					sumAveScores += (scoreList.get(k).getPoints() / scoreList.get(k).getMaxPossible());
					numScores++;
				}
			}
			aveScore = sumAveScores / numScores;
			
			weightAveScore = aveScore * gradeEstimator.weights.get(i);
			aveScore = aveScore * 100;
			weightPercentScore += weightAveScore;
			
			//Adds the new information to our return string
			report += (String.format("%7.2f", weightAveScore) + 
					"% = " + String.format("%5.2f", aveScore) + "% of " +
					String.format("%2.0f", gradeEstimator.weights.get(i)) + "% for " + cat + "\n");		
		}
		
		//Cycles through cutoffs to find a letter grade estimate
		for (int i = 0; i < gradeEstimator.cutOffs.size(); i++) {
			
			//Checks to make sure a letter grade hasn't been found
			if (!(letterGrade == "")) {
				break;
			}
			
			//Handles the case where grade is lower than cutoff
			else if (weightPercentScore < gradeEstimator.cutOffs.get(gradeEstimator.cutOffs.size() - 1)) {
				letterGrade = "unable to estimate letter grade for " + weightPercentScore;
			}
			
			//Handles the edge case of i == 0.
			else if (i == 0) {
				if (weightPercentScore < 100 && 
						weightPercentScore > gradeEstimator.cutOffs.get(0)) {
					letterGrade = gradeEstimator.letterGrades[0];
				}
			}
			else if ((weightPercentScore < gradeEstimator.cutOffs.get(i - 1)
					&& (weightPercentScore > gradeEstimator.cutOffs.get(i)))) {
				letterGrade = gradeEstimator.letterGrades[i];
			}
		}
		
		//Adds the footer
		report = report + ("--------------------------------\n" + 
				String.format("%7.2f", weightPercentScore) + " weighted"
						+ " percent \nLetter Grade Estimate: " + letterGrade);
		return report;
	}
	
	/**
	 * emptyReportCreator
	 * 
	 * This method is responsible for creating a GradeEstimator 
	 * by using default values 
	 *
	 * Throws checked exception: exception must be declared in method header
	 * using throws clause or using try/catch block.
	 * 
	 * POSTCONDITIONS: newly created instance of GradeEstimator is returned
	 * 
	 * @return gradeEstimator   Reference to GradeEstimator object that holds 
	 * 			    all of the data needed to estimate a letter grade
	 */
	public static GradeEstimator emptyReportCreator() 
	throws FileNotFoundException, GradeFileFormatException  {
		
		//Holds max points as double.
		Double maxPoints = 0.0;
		//Holds possible points as double
		Double possiblePoints = 0.0;
		//Stores the GradeEstimator object to be returned.
		GradeEstimator gradeEstimator = new GradeEstimator();
		
		//Stores letter grades, cutoffs, categories, weights in arrays, splits
		//by whitespace.
		gradeEstimator.letterGrades = Config.GRADE_LETTER;
		gradeEstimator.catNames = Config.CATEGORY_KEY;
		
		//Stores the doubles in arrayLists
		for (int i = 0; i < Config.GRADE_THRESHOLD.length; i++) {
			gradeEstimator.cutOffs.add(Config.GRADE_THRESHOLD[i]);
		}
		for (int i = 0; i < Config.CATEGORY_WEIGHT.length; i++) {
			gradeEstimator.weights.add(Config.CATEGORY_WEIGHT[i]);
		}    
		
		
		//Cycles through remaining lines to add new scores to score list
		for (int i = 0; i < gradeEstimator.scoreList.size(); i++) {
			gradeEstimator.scoreList.add(new Score("default", possiblePoints, maxPoints));
		}
		
		return gradeEstimator;
	}
}
