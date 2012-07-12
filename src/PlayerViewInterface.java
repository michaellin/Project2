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

/**
 * The view for a player, allows a player to show an error, or to highlight
 * cubes, e.g., as on a board for a word found by a player.
 * 
 * @author Owen Astrachan
 * 
 */
public interface PlayerViewInterface {
  /**
   * A view should support some way to visualize a player's word found on a
   * board at some set of board locations.
   * 
   * @param word
   *          is word visualized
   * @param list
   *          of BoardCells the word is found on (on some board)
   * @param player
   *          is the player finding a word
   */
  public void showWord(String word, List<BoardCell> list, Player player);
  
  /**
   * Show an error during the game.
   * 
   * @param word
   *          is cause of error, e.g., duplicate, not in lexicon
   * @param message
   *          is displayed with the word
   */
  public void showError(String word, String message);
}
