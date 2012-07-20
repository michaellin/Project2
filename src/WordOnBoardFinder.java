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
		ArrayList<List<BoardCell>> listOfPaths = new ArrayList<List<BoardCell>>();
		int wordLength = word.length();
		if(word.contains("qu")){
			wordLength -=1; 
		}
	    for (int r = 0; r < board.size(); r++) {
	      for (int c = 0; c < board.size(); c++) {
			list = new ArrayList<BoardCell>();
			 findWord(r, c, board, word, wordLength, new boolean[board.size()][board.size()], 
			 					list, listOfPaths);
	      }
	    }
		if(listOfPaths.isEmpty()){
			return new ArrayList<BoardCell>();
		}else{
			for(int i = 0; i < listOfPaths.get(0).size(); i++){
			}
			return listOfPaths.get(0);
		}
	  }

		/**
		 * Populates the ArrayList with the matching letters found.
		 */
	 private static void findWord(int row, int column, BoggleBoard board, 
	 								String word, int goalLength, boolean[][] used,
	 								List<BoardCell> list, ArrayList <List<BoardCell>> listOfPaths) {
		 if(word.equals("")&&list.size()==goalLength){
		 		listOfPaths.add(list);
		 	}
		 	else if (!word.equals("") && list.size() != goalLength) {
				String firstLetter = word.substring(0, 1);
				if (firstLetter.equalsIgnoreCase("q")) {
					firstLetter += word.substring(1, 2);
				}
				if (board.getFace(row, column).equals(firstLetter)) {
					boolean[][] newUsed = (boolean[][]) used.clone();
					newUsed[row][column] = true;
					list.add(new BoardCell(row, column));
					String next;
					if (firstLetter.equalsIgnoreCase("qu")) {
						next = word.substring(2);	
					} else {
						next = word.substring(1);
					}
					if (row > 0 && !used[row - 1][column]) {
						findWord(row - 1, column, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}
					if (column < board.size() - 1 && row > 0 && !used[row - 1][column + 1]) {
						findWord(row - 1, column + 1, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}
					if (column < board.size() - 1 && !used[row][column + 1]) {
						findWord(row, column + 1, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}
					if (column < board.size() - 1 && row < board.size() - 1
					                          		&& !used[row + 1][column + 1]) {
						findWord(row + 1, column + 1, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}	
					if (row < board.size() - 1 && !used[row + 1][column]) {
						findWord(row + 1, column, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}
					if (column > 0 && row < board.size() - 1 && !used[row + 1][column - 1])	{
						findWord(row + 1, column - 1, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}
					if (column > 0 && !used[row][column - 1]) {
						findWord(row, column - 1, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}
					if (column > 0 && row > 0 && !used[row - 1][column - 1]) {
						findWord(row - 1, column - 1, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}else if(next.equals("")){
						findWord(row, column, board, next, goalLength, newUsed, new ArrayList <BoardCell>(list), listOfPaths);
					}
				}
			}
	 	}
  
  private static boolean findWordWorks() {
	// This specifically tests the helper method called within WordOnBoardFinder, using the same board as the last JUnit Test.
	// We will start with the case in which the correct first letter "w" has been found and the helper method must now be able
	// to find the word wonderful on its own without the cellsForWord method being called again. Note: no q's in this test.
	BoardMaker myMaker = new BoardMaker();
	String[] boardContents = { "owonu", "owufu", "nolrf", "dnder", "dderr" };
	BoggleBoard board = myMaker.makeBoard(boardContents);
	List<BoardCell> toReturn = new ArrayList<BoardCell>();
	ArrayList<List<BoardCell>> listOfPaths = new ArrayList<List<BoardCell>> ();
	boolean [][]  alreadyUsed = new boolean [board.size()][board.size()];
	for (int row = 0; row < board.size(); row++) {
		for (int col = 0; col < board.size(); col++) {
			alreadyUsed [row][col] = false;
			findWord(row, col, board, "wonderful", "wonderful".length(), new boolean[board.size()][board.size()], 
					toReturn, listOfPaths);
			}	
		}	
	String toCheck = "";
	 for (int n = 0; n < toReturn.size(); n++) {
		 toCheck += board.getFace(toReturn.get(n).row, toReturn.get(n).col);
	 }
	 return toCheck.equals("wonderful");
  }
  
  
  public static boolean helpersWork() {
		if (!WordOnBoardFinder.findWordWorks())
		{
			return false;
		}

		return true;
	}
}
