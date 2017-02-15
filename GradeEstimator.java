import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GradeEstimator {

	private static ArrayList<String> letterGrades = new ArrayList<String>();
	private static ArrayList<Double> gradeThreshold = new ArrayList<Double>();
	private static ArrayList<String> categories = new ArrayList<String>();
	private static ArrayList<Double> categoryWeights = new ArrayList<Double>();
	private static ScoreList scoreList = new ScoreList();
	
	public static GradeEstimator createGradeEstimatorFromFile(String gradeInfo)
			throws FileNotFoundException, GradeFileFormatException {

			File gradeInfoFile = new File (gradeInfo);
			Scanner fileScnr = new Scanner (gradeInfoFile);
			GradeEstimator grdEstimator = new GradeEstimator();
			
			//parsing the letter grades from the file
			String gradeLine = fileScnr.nextLine();
			String[] gradeArray = gradeLine.split(" ");
			int numGrades = 0;
			
			for (int i=0; i<gradeArray.length; i++){
				String arrayItem = gradeArray[i];
				if (!arrayItem.equals("")){
					if (arrayItem.charAt(0) >= 'A' && arrayItem.charAt(0) <= 'Z'){
					grdEstimator.letterGrades.add(gradeArray[i]);
					numGrades++;
					}
					if (gradeArray[i].charAt(0) == '#')
						break;
				}
			}
			if (numGrades == 0)
				throw new GradeFileFormatException();
			
			
			//parsing grade thresholds from the file
			String thresholdLine = fileScnr.nextLine();
			String[] thresholdArray = thresholdLine.split(" ");
			int numThresholds = 0;
			
			for (int i=0; i<thresholdArray.length; i++){
				if (!thresholdArray[i].equals("")){
					if (thresholdArray[i].charAt(0) == '#')
						break;
					try{
						grdEstimator.gradeThreshold.add(Double.valueOf(thresholdArray[i]));
						numThresholds++;
					} catch(NumberFormatException e){
						throw new GradeFileFormatException();
					}
				}
			}
			if (numGrades != numThresholds)
				throw new GradeFileFormatException();
			
			
			//parsing the category names from the file
			String categoryLine = fileScnr.nextLine();
			String[] categoryArray = categoryLine.split(" ");
			int numCategories = 0;
			
			for (int i=0; i<categoryArray.length; i++){
				if (!categoryArray[i].equals("")){
					if (categoryArray[i].charAt(0) == '#')
						break;
					grdEstimator.categories.add(categoryArray[i]);
					numCategories++;
				}
			}
			if (numCategories == 0)
				throw new GradeFileFormatException();
			
			
			//parsing category weights from the file
			String weightLine = fileScnr.nextLine();
			String[] weightArray = weightLine.split(" ");
			int numWeights = 0;
			int sumWeights = 0;
			
			for (int i=0; i<weightArray.length; i++){
				if (!weightArray[i].equals("")){
					if (weightArray[i].charAt(0) == '#')
						break;
					try{
						Double weight = Double.valueOf(weightArray[i]);
						grdEstimator.categoryWeights.add(weight);
						numWeights++;
						sumWeights += weight;
					} catch(NumberFormatException e){
						throw new GradeFileFormatException();
					}
				}
			}
			if (numWeights != numCategories || sumWeights != 100)
				throw new GradeFileFormatException();
			
			
			//parsing scores from the file
			while (fileScnr.hasNextLine()){
				String scoreLine = fileScnr.nextLine();
				String[] scoreArray = scoreLine.split(" ");
				String name = null;
				double points = 0;
				double maxPts = 0;
				for (int i=0; i<scoreArray.length; i++){
					if (!scoreArray[i].equals("")){
						if (scoreArray[i].charAt(0) == '#')
							break;
						else if (i == 0)
							name = scoreArray[i];
						else if (i == 1)
							points = Double.parseDouble(scoreArray[i]);
						else if (i == 2)
							maxPts = Double.parseDouble(scoreArray[i]);
					}
				}
				
				try {
					Score newScore = new Score(name, points, maxPts);
					grdEstimator.scoreList.add(newScore);
				} catch (IllegalArgumentException e){
					throw new GradeFileFormatException();
				} 
			}
			
			if (grdEstimator.scoreList.size() == 0)
				throw new GradeFileFormatException();
			
			return grdEstimator;
	}
	
	public static String getEstimateReport(){
		
    	String output = "";
    	
    	for (int i=0; i<scoreList.size(); i++){
    		Score currScore = scoreList.get(i);
    		output += currScore.getName() + String.format(" %7.2f",currScore.getPercent()) +"\n";
    	}
    	output += "Grade estimate is based on " + scoreList.size() + " scores.\n";
    	
		double weightedScore = 0;
		
		//loop that collects weighted score for each category
		for (int i=0; i<categories.size(); i++){
			ScoreIterator itr = new ScoreIterator(scoreList, categories.get(i).substring(0,1));
			double sumScores = 0;
			double numScores = 0;
			
			while (itr.hasNext()){
				sumScores += itr.next().getPercent();
				numScores++;
			}
			
			double avgScore = (sumScores/numScores);
			double weightedAvg = avgScore * categoryWeights.get(i) / 100;
			weightedScore += weightedAvg;
			
			output += String.format("%7.2f", weightedAvg) + "% = " +
						String.format("%5.2f", avgScore) + "% of " +
						String.format("%2.0f", categoryWeights.get(i)) + 
						"% for " + categories.get(i) + "\n";	
		}
		output += "--------------------------------\n";
		output += String.format("%7.2f", weightedScore) + "% weighted percent\n";
		output += "Letter Grade Estimate:\n";
		String grade = null;
		
		for (int i=0; i<gradeThreshold.size(); i++)
			if (weightedScore >= gradeThreshold.get(i)){
				grade = letterGrades.get(i);
				break;
			}
		
		if (grade == null)
			output += "unable to estimate letter grade for " + weightedScore;
		
		output += grade;
		return output;
	}
	
	public static void main(String[] args) {
		String estReport; //Estimate Report
		
		//default case if args is invalid
		if (args == null || args.length != 1){
			System.out.println(Config.USAGE_MESSAGE);
			
			new GradeEstimator();
			
			//setting values for grade letters from config
			for (int i=0; i<Config.GRADE_LETTER.length; i++)
				letterGrades.add(Config.GRADE_LETTER[i]);
			
			//setting values for grade threshold from config
			for (int i=0; i<Config.GRADE_THRESHOLD.length; i++)
				gradeThreshold.add(Config.GRADE_THRESHOLD[i]);
			
			//setting values for categories from config
			for (int i=0; i<Config.CATEGORY_KEY.length; i++)
				categories.add(Config.CATEGORY_KEY[i]);
			
			//setting values for category weights from config
			for (int i=0; i<Config.CATEGORY_WEIGHT.length; i++)
				categoryWeights.add(Config.CATEGORY_WEIGHT[i]);
			
			Scanner scnr = new Scanner(Config.GRADE_INFO_FILE_FORMAT_EXAMPLE);
			
			//skipping first 4 lines of text
			for (int i=0; i<4; i++)
				scnr.nextLine();
			
			//parsing scores from the file
			while (scnr.hasNextLine()){
				
				String scoreLine = scnr.nextLine();
				String[] scoreArray = scoreLine.split(" ");
				String name = null;
				double points = 0;
				double maxPts = 0;
				
				//loop that reads all scores and their corresponding data from scoreArray
				for (int i=0; i<scoreArray.length; i++){
					
					if (!scoreArray[i].equals("")){ //empty spaces
						
						if (scoreArray[i].charAt(0) == '#') //comment symbol which breaks the loop
							break; 
						
						else if (i == 0) //name of score
							name = scoreArray[i];
						else if (i == 1) //points obtained
							points = Double.parseDouble(scoreArray[i]);
						else if (i == 2) //maximum pts
							maxPts = Double.parseDouble(scoreArray[i]);
					}
				}
				Score newScore = new Score(name, points, maxPts);
				scoreList.add(newScore);
				
			}
			estReport = getEstimateReport();
			System.out.println(estReport);
			
		} 
		else {
			try {
				createGradeEstimatorFromFile(args[0]);
				estReport = getEstimateReport();
				System.out.println(estReport);
			}
			catch (FileNotFoundException e){
				System.out.println(e);
			}
			catch (GradeFileFormatException e){
				System.out.println(e);
			}
		}
		
	}

}
