///////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Lec002 Spring 2017 
// PROJECT:          ScoreList and Score
// FILE:             ScoreList.java
//
// Author: Udhbhav Gupta
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Implements the ScoreListADt and defines the container object ScoreList as 
 * an array of Score objects. Provides user with methods to determine 
 * the size of the list, add a Score to the list, 
 * remove a Score from a particular index position and
 * access a Score at a particular index position.
 *
 * <p>Bugs: None
 *
 * @author Udhbhav Gupta
 */
public class ScoreList implements ScoreListADT{
	
	private int numScores; //number of scores stored in the list
	private Score[] scores; //array of Score objects
	
	/**
	 * Constructor for a new ScoreList object which defines a new array
	 * of Score objects and sets number of scores to 0.
	 *
	 */
	public ScoreList(){
		numScores = 0;
		scores = new Score[10];
	}
	
	/** 
	 * Returns the number of Scores in the list or zero
	 * @return the number of scores in this list
	 */
	public int size(){
		return numScores;
	}
	
	/** 
	 * Adds the score to the end of this list, expanding the list if full,
	 * and increments numScores.
	 * 
	 * @param s a non-null Score to place as the last item in the list.
	 * @throws IllegalArgumentException
	 */
	public void add(Score s){
		if (s == null)
			throw new IllegalArgumentException();
		//expanding list if full
		if (numScores == scores.length)
			expandList();
		//adding s to the array and then increasing numScores by 1
		scores[numScores++] = s;
	}
	
	/**
	 * Removes and returns the item at index position i.
	 * If i is less than zero or greater than size()-1,
	 * will throw an IndexOutOfBoundsException.
	 * @param i must be greater than or equal to zero and less than size()
	 * @return the item at index i
	 * @throws IndexOutOfBoundsException
	 */
	public Score remove(int i){
		if (i<0 || i>=numScores)
			throw new IndexOutOfBoundsException();
		
		Score removedScore = scores[i]; //score needed to be removed
		
		//shifting scores to the right of removedScore, 1 position to the left
		for (int j=i; j<numScores; j++)
			scores[j] = scores[j+1];
		numScores--;
		return removedScore;
	}
	
	/**
	 * Returns (without removing) the item at index position i.
	 * If i is less than zero or greater than size()-1,
	 * will throw an IndexOutOfBoundsException.
	 * @param i must be greater than or equal to zero and less than size()
	 * @return the item at index i
	 * @throws IndexOutOfBoundsException
	 */
	public Score get(int i){
		if (i<0 || i>=numScores)
			throw new IndexOutOfBoundsException();
		
		return scores[i];
	}
	
	/** 
	 * Expands the scores array to twice its size
	 * 
	 */
	private void expandList(){
		//new expanded array for Score objects twice as big as scores
		Score[] expArray = new Score[scores.length*2];
		
		//copying all the elements of scores into expArray
		for (int i=0; i<numScores; i++)
			expArray[i] = scores[i];
		
		//changing reference of scores to the new expanded array
		scores = expArray;
	}
}
