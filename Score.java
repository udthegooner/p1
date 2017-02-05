///////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Lec002 Spring 2017 
// PROJECT:          ScoreList and Score
// FILE:             Score.java
//
// Author: Udhbhav Gupta
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Defines the Score object which stores and provides access to 
 * the assignment name, the points earned, the maximum points possible 
 * for that assignment in addition to calculating percentage and subcategory.
 *
 * <p>Bugs: None
 *
 * @author Udhbhav Gupta
 */
public class Score{
	
	private String name; //name of the assignment
	private double earnedPts; //points earned on the assignment
	private double maxPts; //maximum possible points for the assignment
	
	/**
	 * Constructor for the Score object which sets values for the fields
	 *
	 * name must be non null
	 * maxPts should be a positive number
	 * earnedPts should be a non negative number less than maxPts 
	 *
	 * @param name a String which is the name of assignment
	 * @param earnedPts points earned on the assignment
	 * @param maxPts maximum possible points for the assignment
	 * @throws IllegalArgumentException
	 */
	public Score(String name, double earnedPts, double maxPts){
		
		if (name == null || maxPts <= 0 || earnedPts > maxPts)
			throw new IllegalArgumentException();
		
		//setting the values for the instance fields
		this.name = name;
		this.earnedPts = earnedPts;
		this.maxPts = maxPts;
	}
	
	/**
	 * Accessor method to get the name of the assignment of the score
	 * 
	 * @return name of the assignment of the score
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Accessor method to get the points earned on the assignment
	 * 
	 * @return points earned
	 */
	public double getPoints(){
		return earnedPts;
	}
	
	/**
	 * Accessor method to get the maximum possible points for the assignment
	 * 
	 * @return maximum possible points
	 */
	public double getMaxPossible(){
		return maxPts;
	}
	
	/**
	 * Accessor method to get the category of the assignment which is the first
	 * character of the name of the assignment
	 *
	 * @return first character of the name
	 */
	public String getCategory(){
		//selecting the first character of the name as a String
		return name.substring(0, 1);
	}
	
	/**
	 * Calculates and returns the percentage score for the assignment
	 *
	 * @return percentage score for the assignment
	 */
	public double getPercent(){
		double percentPts = (earnedPts/maxPts)*100; //percentage score
		return percentPts;
	}
}
