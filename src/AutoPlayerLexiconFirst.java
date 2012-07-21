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
import java.util.List;


public class AutoPlayerLexiconFirst extends AbstractPlayer {
  
  public AutoPlayerLexiconFirst(LexiconInterface lex) {
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
	  if (lex instanceof LexiconArrayList) {
		  LexiconArrayList lexAL = (LexiconArrayList) lex;
		  WordOnBoardFinder myFinder = new WordOnBoardFinder();
		  for (int n = 0; n < lexAL.size(); n++) {
			  List<BoardCell> boardCellList = myFinder.cellsForWord(board, lexAL.get(n));
			  if (!boardCellList.isEmpty() && lexAL.get(n).length() >= minLength) {
				  this.add(lexAL.get(n));
			  }
		  }
	  }
	 /* else {
		  LexiconTrie LexT = (LexiconTrie) lex;
		  WordOnBoardFinder myFinder = new WordOnBoardFinder();
		  LexT.initIterator();
		  while (LexT.hasNext()) {
			  String currentWord = LexT.next();
			  List<BoardCell> boardCellList = myFinder.cellsForWord(board, currentWord);
			  if (!boardCellList.isEmpty() && currentWord.length() >= minLength) {
				  this.add(currentWord);
			  }
		  }
	  }*/
  }
  
  @Override
  public String getName() {
    return "AutoPlayer";
  }
}
