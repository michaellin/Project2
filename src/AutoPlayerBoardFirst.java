package src;

import java.util.ArrayList;

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

public class AutoPlayerBoardFirst extends AbstractPlayer {
  
  public AutoPlayerBoardFirst(LexiconInterface lex) {
    this.initialize(lex);
  }
  
  /**
   * Find all the valid words automatically, found words should be added via the
   * <code>AbstractPlayer</code>'s <code>add</code> method.
   * 
   * @param board
   *          is the BoggleBoard on which words are found
   * @param lex
   *          is the lexicon in which words are searched/validated
   * @param minLength
   *          of words found by an autoplayer
   */
  public void findAllValidWords(BoggleBoard board, LexiconInterface lex, int minLength) {
	  /* TODO: Given a board and a lexicon, use the add() in AbstractPlayer.java
	   * to add all words that are both in the board and in the lexicon.
	   * 
	   * This method will run after the human player finishes entering words. */
	  for (int r = 0; r < board.size(); r++) {
		  for (int c = 0; c < board.size(); c++) {
			  findAllValidWordsHelper(r,c,board,lex,1,new String(),new boolean[board.size()][board.size()]);
		  }
	  }
  }
   
  private void findAllValidWordsHelper(int row, int column, BoggleBoard board, LexiconInterface lex, int minLength, String soFar, boolean[][] used){
	  soFar += board.getFace(row, column);
	  System.out.println(soFar);
	  boolean[][] newUsed = (boolean[][]) used.clone();
	  newUsed[row][column] = true;
	  if(lex.contains(soFar) && soFar.length() >= minLength){
		  this.add(soFar);
	  }
	  if(lex.containsPrefix(soFar)){
		  System.out.println("got here");
		  if (board.isInBounds(row-1, column) && !newUsed[row-1][column]) {
			  findAllValidWordsHelper(row - 1, column, board, lex, minLength, new String (soFar), newUsed);
		  }
		  if (board.isInBounds(row-1, column+1) && !newUsed[row-1][column+1]) {
			  findAllValidWordsHelper(row - 1, column + 1, board, lex, minLength, new String (soFar), newUsed);
		  }
		  if (board.isInBounds(row, column+1)  && !newUsed[row][column+1]) {
			  findAllValidWordsHelper(row, column + 1, board, lex, minLength, new String (soFar), newUsed);
		  }
		  if (board.isInBounds(row+1, column+1) && !newUsed[row+1][column+1]) {
			  findAllValidWordsHelper(row + 1, column + 1, board, lex, minLength, new String (soFar), newUsed);
		  }	
		  if (board.isInBounds(row+1, column) && !newUsed[row+1][column]) {
			  findAllValidWordsHelper(row + 1, column, board, lex, minLength, new String (soFar), newUsed);
		  }
		  if (board.isInBounds(row+1, column-1) && !newUsed[row+1][column-1])	{
			  findAllValidWordsHelper(row + 1, column-1, board, lex, minLength, new String (soFar), newUsed);
		  }
		  if (board.isInBounds(row, column-1) && !newUsed[row][column-1]) {
			  findAllValidWordsHelper(row, column-1, board, lex, minLength, new String (soFar), newUsed);
		  }
		  if (board.isInBounds(row-1, column-1) && !newUsed[row-1][column-1]) {
			  findAllValidWordsHelper(row - 1, column - 1, board, lex, minLength, new String (soFar), newUsed);
		  }
	  }
  }

  @Override
  public String getName() {
	  return "AutoPlayer";
  }
}
