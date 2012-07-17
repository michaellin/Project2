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
		 findWord(r, c, board, word, word.length(), new boolean[board.size()][board.size()], 
		 					list);
		 int goal;
		 if (word.matches("(.*?)[Qq]u.*")) {
			 goal = word.length() - 1;
		 } else {
			 goal = word.length();
		 }
		 /*
		 System.out.println(list.size());
		 String test = "";
		 for (BoardCell cell : list) {
			 test += board.getFace(cell.row, cell.col);
		 }
		 System.out.println(test);
		 */
		 if (list.size() == goal) {
			 return list;
		 }
      }
    }
    return new ArrayList<BoardCell>();
  }

	/**
	 * Populates the ArrayList with the matching letters found.
	 */
 private static void findWord(int row, int column, BoggleBoard board, 
 								String word, int goalLength, boolean[][] used, List<BoardCell> list) {
		if (!word.equals("") && list.size() != goalLength) {
			String firstLetter = word.substring(0, 1);
			if (firstLetter.equalsIgnoreCase("q")) {
				firstLetter += word.substring(1, 2);
			}
			if (board.getFace(row, column).equals(firstLetter)) {
				used[row][column] = true;
				list.add(new BoardCell(row, column));
				String next;
				if (firstLetter.equalsIgnoreCase("qu")) {
					next = word.substring(2);	
				} else {
					next = word.substring(1);
				}
				if (row > 0 && !used[row - 1][column]) {
					findWord(row - 1, column, board, next, goalLength, used, list);
				}
				if (row < board.size() - 1 && !used[row + 1][column]) {
					findWord(row + 1, column, board, next, goalLength, used, list);
				}
				if (column > 0 && !used[row][column - 1]) {
					findWord(row, column - 1, board, next, goalLength, used, list);
				}
				if (column < board.size() - 1 && !used[row][column + 1]) {
					findWord(row, column + 1, board, next, goalLength, used, list);
				}
				if (column > 0 && row > 0 && !used[row - 1][column - 1]) {
					findWord(row - 1, column - 1, board, next, goalLength, used, list);
				}
				if (column < board.size() - 1 && row > 0 && !used[row - 1][column + 1]) {
					findWord(row - 1, column + 1, board, next, goalLength, used, list);
				}
				if (column > 0 && row < board.size() - 1 && !used[row + 1][column - 1])	{
					findWord(row + 1, column - 1, board, next, goalLength, used, list);
				}
				if (column < board.size() - 1 && row < board.size() - 1
									&& !used[row + 1][column + 1]) {
					findWord(row + 1, column + 1, board, next, goalLength, used, list);
				}
			}
		}
	 }
}
