///////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Lec002 Spring 2017 
// PROJECT:          Program 1
// FILE:             ScoreIterator.java
//
// Author: Yuchen Bai, Udhbhav Gupta
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implements ScoreIteratorADT and defines the indirect access iterator 
 * for a ScoreList. Provides methods to traverse ScoreList with next and 
 * hasNext methods.
 *
 * <p>Bugs: None
 *
 * @author Yuchen Bai, Udhbhav Gupta
 */
public class ScoreIterator implements ScoreIteratorADT <Score> {
	private ScoreList myList; //the list of scores
	private int currPos; //current position in the list
	private String category; //given score category
	private ArrayList<Score> categoryList = new ArrayList<Score>();



	/**
	 * Constructor for the Score object which sets values for the fields
	 */
	public ScoreIterator (ScoreList myList, String category){
		this.myList = myList;
		this.currPos = 0;
		this.category = category;
		
		for (int i=0; i<myList.size(); i++)
			if (myList.get(i).getCategory().equals(category))
				categoryList.add(myList.get(i));
	}
	
	/**
	 * Checks if there are any more scores of the given category in the list.
	 * 
	 * @return false if no more scores of matching category exist in the list,
	 * 		true otherwise
	 */
	public boolean hasNext() {
		
		return currPos < categoryList.size();
	}

	/**
	 * Returns the next score of the given category in the list
	 * 
	 * @return next Score of matching category
	 * @throws NoSuchElementException if no scores of matching category exist
	 */
	public Score next() {
		if (!hasNext()) throw new NoSuchElementException();
		
		return categoryList.get(currPos++);
	}

}
