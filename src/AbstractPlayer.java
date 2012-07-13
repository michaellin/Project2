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

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Supplies default implementation and state for a player. Allows a player to a
 * view, a scorekeeper, a lexicon and to store words found in a game. This
 * implementation uses a set to store strings/words and updates score for new
 * words added to the player. Words are not checked for being in a lexicon or on
 * a board int the <code>add</code> method.
 * <P>
 * 
 * @author Owen Astrachan
 * 
 */
public abstract class AbstractPlayer implements Player {
  
  protected LexiconInterface myLexicon;
  protected PlayerViewInterface myView;
  protected TreeSet<String> myWords;
  protected BoggleScore myScoreKeeper;
  
  protected int myScore;
  
  /**
   * Make this player have an associated lexicon and initialize other state
   * appropriately.
   * 
   * @param lex
   *          is the lexicon for this player
   */
  @Override
  public void initialize(LexiconInterface lex) {
    myLexicon = lex;
    myWords = new TreeSet<String>();
    myScoreKeeper = new BoggleScore();
    myScore = 0;
  }
  
  /**
   * Set a veiw for this player, if not called the player will have a null view.
   * 
   * @param view
   *          is this player's view
   */
  @Override
  public void setView(PlayerViewInterface view) {
    myView = view;
  }
  
  /**
   * Return the player's score (as calculated via updates to the
   * <code>add</code> method).
   * 
   * @return this player's score
   */
  @Override
  public int getScore() {
    return myScore;
  }
  
  /**
   * Add a word to this player's list of words, adjust score if word is new to
   * this player. Duplicate words are not added, board and lexicon are <em>not
   * checked</em>.
   * 
   * @param word
   *          is the word added to this player
   */
  @Override
  public boolean add(String word) {
    if (!myWords.contains(word)) {
      myScore += myScoreKeeper.getScore(word, 4);
      myWords.add(word);
      return true;
    }
    return false;
  }
  
  @Override
  public Iterator<String> iterator() {
    return myWords.iterator();
  }
  
  @Override
  public abstract String getName();
  
  @Override
  public void clear() {
    myWords.clear();
    myScore = 0;
  }
  
  @Override
  public int wordCount() {
    return myWords.size();
  }
}
