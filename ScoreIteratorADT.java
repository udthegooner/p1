/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          GradeEstimator
// FILE:             ScoreIteratorADT.java
//
// Author1: Udhbhav Gupta
// Author2: Matt Perry, 
// Author3: Yuchen Bai
// Author4: Collin Lacy, clacy@wisc.edu, clacy, Lecture 001
// Author5: Quinn Nicholson, 
// Author5: Daniel Jones
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: avoid web searches to solve your problems, but if you do 
// search, be sure to include Web URLs and description of 
// of any information you find. 
//////////////////////////// 80 columns wide //////////////////////////////////

public interface ScoreIteratorADT <E> {
	public boolean hasNext();
	public E next();
}
