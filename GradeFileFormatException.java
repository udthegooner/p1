/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p1
// FILE:             GradeFileFormatException.java
//
// Authors: Collin Lacy, clacy@wisc.edu, Lecture 001
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class is responsible for handling a custom, checked exception. 
 * To be thrown when the input file to
 * the GradeEstimator is improperly formatted.
 *
 *
 * @author Lacy
 */
public class GradeFileFormatException extends Exception {
	
	/**
	 * 
	 * If the constructor does not explicitly call super() with
	 * or without some arguments, java will automatically call the 
	 * default constructor of the superclass. 
	 * 
	 * This is just a reminder that the base class objects
	 * must be built as part of the sub class object.
	 *
	 */
	public GradeFileFormatException() {
		super();
	}
	
	/**
         * Uses super to initialize the Exception's error message.
	 * By passing the message into the base class constructor, the base
	 * class will take care of the work of setting up the message correctly.
	 * 
	 *
	 * @param message String that describes the error condition for this particular exception
	 * 
	 */
	public GradeFileFormatException(String message) {
		super(message);
	}
	
}
