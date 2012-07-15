package src;
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
				if (board.getFace(r, c).equals(Character.toString(word.charAt(0))) && !Character.toString(word.charAt(0)).equals("q")){ //if a first letter match is found for a word that does not begin with Qu
					int index = 1; //placekeeper for index along the word; index at 0 was just checked so we start at 1
					boolean [][]  alreadyUsed = new boolean [board.size()][board.size()];
					for (int row = 0; row < board.size(); row++) {
						for (int col = 0; col < board.size(); col++) {
							alreadyUsed [row][col] = false; //array to keep track of which cells have already been used for; new one for each time a new search begins
						}
					}
					list = cellsForWordHelper(r, c, board, word, list, index, alreadyUsed);
					if (list.size() == (word.length()-numberOfQu(word))) {
						System.out.println("Is this actually printing as early as it was supposed to?");
						return list; //if finished list is of correct length, return it here and now so the next iteration cannot throw it off
					}
					list = new ArrayList<BoardCell>(); //if on this first run through complete word cannot be found, list is reset
				}
				else if (board.getFace(r, c).equals(Character.toString(word.charAt(0))+Character.toString(word.charAt(1))) && Character.toString(word.charAt(0)).equals("q")){ //if a first letter match is found for a word beginning with Qu
					int index = 2; //placekeeper for index along the word; index at 0 was just checked so we start at 1
					boolean [][]  alreadyUsed = new boolean [board.size()][board.size()];
					for (int row = 0; row < board.size(); row++) {
						for (int col = 0; col < board.size(); col++) {
							alreadyUsed [row][col] = false; //array to keep track of which cells have already been used for; new one for each time a new search begins
						}
					}
					list = cellsForWordHelper(r, c, board, word, list, index, alreadyUsed);
					if (list.size() == (word.length()-numberOfQu(word))) {
						System.out.println("Is this actually printing as early as it was supposed to?");
						return list; //if finished list is of correct length, return it here and now so the next iteration cannot throw it off
					}
					list = new ArrayList<BoardCell>(); //if on this first run through complete word cannot be found, list is reset
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

  private List<BoardCell> cellsForWordHelper(int row, int col, BoggleBoard board, String word, List<BoardCell> toReturn, int index, boolean [][] alreadyUsed) {
	  alreadyUsed[row][col] = true; 
	  toReturn.add(new BoardCell(row, col));
	  System.out.println("Found " + board.getFace(row, col));
	  if (index == word.length()) { 
		  System.out.println("The complete word has been returned.");
		  System.out.println("Size of this list is " + toReturn.size());
		  System.out.println("The length of this word - instances of Qu is " + (word.length()-numberOfQu(word)));
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
							  for (int x = 0; x <= index; x++) { //get back to the state of conditions before this action on the stack was performed and start again
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
							  for (int x = 0; x <= index; x++) { //get back to the state of conditions before this action on the stack was performed and start again
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
}
