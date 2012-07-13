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

/**
 * A Boggle player must support the methods of this interface. Each player has
 * an associated view. Players add words, e.g., human players might enter a
 * string of text and computer players might find words on a board passed to a
 * computer player method. (See <code>AutoPlayer</code>).
 * <P>
 * 
 * @author Owen Astrachan
 * @date March 16, 2005
 */

public interface Player extends Iterable<String> {

  /**
   * Initialize a player with a Lexicon which could serve this player for more
   * than one game
   * 
   * @param lex
   *          is the Lexicon for this player
   */
  public void initialize(LexiconInterface lex);

  /**
   * Specify a view for this player, the view is potentially used to show
   * errors, highlight cubes for a word, and so on.
   * 
   * @param view
   */
  public void setView(PlayerViewInterface view);

  /**
   * Get the score for this player, see <code>BoggleScore</code> for how scores
   * are calculated.
   * 
   * @return current score for this player
   */
  public int getScore();

  /**
   * Add a word for the player. The player may choose to ignore duplicate words,
   * skip words not in the lexicon, or to check these.
   * 
   * @param word
   * @return true if the word was added, false otherwise
   */
  public boolean add(String word);

  /**
   * Return the name of a player.
   * 
   * @return this player's name
   */
  public String getName();

  /**
   * Clear state of a player, e.g., as in a new game.
   */
  public void clear();

  /**
   * Return number of unique words added by this player.
   * 
   * @return number of words
   */
  public int wordCount();

}
