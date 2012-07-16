/**
 * Copyright (C) 2002 Michael Green <mtgreen@cs.ucsd.edu>
 * 
 * Copyright (C) 2002 Paul Kube <kube@cs.ucsd.edu>
 * 
 * Copyright (C) 2005 Owen Astrachan <ola@cs.duke.edu>
 * 
 * Copyright (C) 2011 Hoa Long Tam <hoalong.tam@berkeley.edu> and Armin Samii
 * <samii@berkeley.edu>
 * 
 * This file is part of CS Boggle.
 * 
 * CS Boggle is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * CS Boggle is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * CS boggle. If not, see <http://www.gnu.org/licenses/>.
 */
package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WordOnBoardFinder {

  /**
   * Return a list of cells on the given board such that the i-th element of the
   * list corresponds to the i-th character of the string as found on the board.
   * Returns an empty list if the word cannot be found on the board.
   * 
   * @param board
   *          is searched for the given word
   * @param word
   *          is being searched for on the board
   * @return list of cells on the supplied board that correspond to the word, an
   *         empty list should be returned if the word cannot be found on the
   *         board
   */
  public List<BoardCell> cellsForWord(BoggleBoard board, String word) {
	List<BoardCell> list = new ArrayList<BoardCell>();
    for (int r = 0; r < board.size(); r++) {
      for (int c = 0; c < board.size(); c++) {
		list = new ArrayList<BoardCell>();
		 findWord(r, c, board, word, new boolean[board.size()][board.size()], 
		 					list);
		 int goal;
		 if (word.matches("qu")) {
			 goal = word.length() - 1;
		 } else {
			 goal = word.length();
		 }
		 if (list.size() == goal) {
			 return list;
		 }
      }
    }
    return new ArrayList<BoardCell>();
  }

	/**
	 * Populates the ArrayList with the matching words found.
	 */
 private static void findWord(int row, int column, BoggleBoard board, 
 								String word, boolean[][] used, List<BoardCell> list) {
		if (word != "") {
			String firstLetter = word.substring(0, 1);
			if (board.getFace(row, column).equals(firstLetter)) {
				used[row][column] = true;
				list.add(new BoardCell(row, column));
				String next;
				if (firstLetter.equalsIgnoreCase("q")) {
					next = word.substring(2);	
				} else {
					next = word.substring(1);
				}
				if (row > 0 && !used[row - 1][column]) {
					findWord(row - 1, column, board, next, used, list);
				}
				if (row < board.size() && !used[row + 1][column]) {
					findWord(row + 1, column, board, next, used, list);
				}
				if (column > 0 && !used[row][column - 1]) {
					findWord(row, column - 1, board, next, used, list);
				}
				if (column < board.size() && !used[row][column + 1]) {
					findWord(row, column + 1, board, next, used, list);
				}
				if (column > 0 && row > 0 && !used[row - 1][column - 1]) {
					findWord(row - 1, column - 1, board, next, used, list);
				}
				if (column < board.size() && row > 0 && !used[row - 1][column + 1]) {
					findWord(row - 1, column + 1, board, next, used, list);
				}
				if (column > 0 && row < board.size() && !used[row + 1][column - 1])	{
					findWord(row + 1, column - 1, board, next, used, list);
				}
				if (column < board.size() && row < board.size()
									&& !used[row + 1][column + 1]) {
					findWord(row + 1, column + 1, board, next, used, list);
				}
			}
		}
		if (list.size() != word.length()-numberOfQu(word)) {
			list = new ArrayList<BoardCell>(); //if complete word cannot be found, an empty ArrayList will be returned
		}
		return list;
	}
  
  /**
   * Helper function that determines the number of Qu instances within a word so as to correct for discrepancies between
   * the cellsForWord size and length of the word.
   * 
   * @param word 
   * 		is being searched for instances of Qu
   * 
   * @return count
   * 		the number of instances of Qu within the word
   */
  private int numberOfQu (String word) {
	  int count = 0;
	  for (int n = 0; n < word.length(); n++) {
		  if (Character.toString(word.charAt(n)).equals("q") || Character.toString(word.charAt(n)).equals("Q")) {
			  count++; //assuming every instance of q comes with an instance of u, as the game expects us to assume
		  }
	  }
	  return count;
  }
  
  private static boolean numberOfQuWorks() {
	  WordOnBoardFinder Test = new WordOnBoardFinder();
	  return 2 == Test.numberOfQu("quickly") + Test.numberOfQu("quotient");
  }

  /**
   * Helper function that uses recursion to see if a list of BoardCells can be made for a given word by checking if the
   * neighbor BoardCell of the most recently added BoardCell corresponds to the next letter of the word being searched
   * on the board. It searches only as long as the list is not of the correct length yet; when it is, it stops searching
   * and awaits the final return of the list.
   * 
   * @param row
   * 		current row of the last match found to a letter within the word being searched
   * 
   * @param col
   * 		current column of the last match found to a letter within the word being searched
   * 
   * @param board
   * 		board being searched
   * 
   * @param word 
   * 		word being searched on the board
   * 
   * @param toReturn
   * 		the most updated list so far
   * 
   * @param index
   * 		the index along the letters of the word being searched (first letter is index 0, second letter is index 1, etc.)
   * 
   * @param alreadyUsed
   * 		the most updated array that keeps track of whether a board cell on the board has already been used for the word
   * 		currently being searched for
   * 
   * @return toReturn
   * 		the empty or full list to be returned by this helper method
   */
  private List<BoardCell> cellsForWordHelper(int row, int col, BoggleBoard board, String word, List<BoardCell> toReturn, int index, boolean [][] alreadyUsed) {
	  alreadyUsed[row][col] = true; 
	  toReturn.add(new BoardCell(row, col));
	  if (index == word.length()) { 
		  return toReturn; //if finished list is of correct length, return it here and now
	  }
	  for (int r = 0; r < board.size(); r++) { //these two for-loops look through the entire board for the letters of the word top-to-bottom, left-to-right
		  for (int c = 0; c < board.size(); c++) {
			  BoardCell cellToCheck = new BoardCell(r, c);
			  String faceToCheck = board.getFace(r, c);
			  if (faceToCheck.equals("Qu") || faceToCheck.equals("qu")) {
				  if (toReturn.size() < (word.length()-numberOfQu(word))) { //to prevent this helper method from continuing to check after the list is already made and done and the word is waiting for return
					  if (board.isInBounds(r, c) && toReturn.get(toReturn.size()-1).isNeighbor(cellToCheck) && faceToCheck.equals(Character.toString(word.charAt(index))) && !alreadyUsed[r][c]) {
						  index = index + 2;
						  toReturn = cellsForWordHelper(r, c, board, word, toReturn, index, alreadyUsed);
						  if (toReturn.size() < word.length()-numberOfQu(word)) { //keep searching to make sure that there was not more than just the one checked route for finding the word
							  List<BoardCell> temp = new ArrayList<BoardCell>();
							  index = index - 2;
							  for (int x = 0; x < toReturn.size()-1; x++) { //get back to the state of conditions before this action on the stack was performed and start again
								  temp.add(toReturn.get(x));
							  }
							  alreadyUsed[r][c] = false;
							  toReturn = temp; 
							  continue;
						  }
					  }
				  }
			  }
			  else {
				  if (toReturn.size() < (word.length()-numberOfQu(word))) { //to prevent this helper method from continuing to check after the list is already made and done and the word is waiting for return
					  if (board.isInBounds(r, c) && toReturn.get(toReturn.size()-1).isNeighbor(cellToCheck) && faceToCheck.equals(Character.toString(word.charAt(index))) && !alreadyUsed[r][c]) {
						  index++;
						  toReturn = cellsForWordHelper(r, c, board, word, toReturn, index, alreadyUsed);
						  if (toReturn.size() < word.length()-numberOfQu(word)) { //keep searching to make sure that there was not more than just the one checked route for finding the word
							  List<BoardCell> temp = new ArrayList<BoardCell>();
							  index = index - 1;
							  for (int x = 0; x < toReturn.size()-1; x++) { //get back to the state of conditions before this action on the stack was performed and start again
								  temp.add(toReturn.get(x));
							  }
							  alreadyUsed[r][c] = false;
							  toReturn = temp; 
							  continue;
						  }
					  }
				  }
			  }
		  }
	  }
	  return toReturn;
  }
  
  private static boolean cellsForWordHelperWorks() {
	// This specifically tests the helper method called within WordOnBoardFinder, using the same board as the last JUnit Test.
	  // We will start with the case in which the correct first letter "w" has been found and the helper method must now be able
	  // to find the word wonderful on its own without the cellsForWord method being called again. Note: no q's in this test.
	  BoardMaker myMaker = new BoardMaker();
	  WordOnBoardFinder myFinder = new WordOnBoardFinder();
	  String[] boardContents = { "owonu", "owufu", "nolrf", "dnder", "dderr" };
	  BoggleBoard board = myMaker.makeBoard(boardContents);
	  
	  List<BoardCell> toReturn = new ArrayList<BoardCell>();
	  boolean [][]  alreadyUsed = new boolean [board.size()][board.size()];
		for (int row = 0; row < board.size(); row++) {
			for (int col = 0; col < board.size(); col++) {
				alreadyUsed [row][col] = false;
			}
		}
  
	List<BoardCell> list = myFinder.cellsForWordHelper(1, 1, board, "wonderful", toReturn, 1, alreadyUsed);
	String toCheck = "";
	 for (int n = 0; n < list.size(); n++) {
		 toCheck += board.getFace(list.get(n).row, list.get(n).col);
	 }
	 return toCheck.equals("wonderful");
  }
  
  public static boolean helpersWork()
	{
		if (!WordOnBoardFinder.cellsForWordHelperWorks())
		{
			return false;
		}
		if (!WordOnBoardFinder.numberOfQuWorks())
		{
			return false;
		}

		return true;
	}
}
