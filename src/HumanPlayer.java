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
 * Boggle player that interacts with a human player. Duplicate words generate an
 * error, words not in lexicon generate an error. The errors are shown via an
 * associated view.
 * 
 * @author Owen Astrachan
 * 
 */
public class HumanPlayer extends AbstractPlayer {
  
  private final String myName;
  
  public HumanPlayer(LexiconInterface lex, String name) {
    initialize(lex);
    myName = name;
  }
  
  /**
   * Add word if the word is in the lexicon and isn't a duplicate. Update score
   * appropriately via call to AbstractPlayer (super). Show word found on the
   * associated View of this player.
   */
  @Override
  public boolean add(String word) {
    if (word.length() == 0) {
      myView.showError("", "empty word, ignored");
      return false;
    } else if (myWords.contains(word)) {
      myView.showError(word, "already guessed");
      return false;
    } else if (!myLexicon.contains(word)) {
      myView.showError(word, "not in dictionary");
      return false;
    } else {
      super.add(word);
      return true;
    }
  }
  
  /**
   * Returns name of this player.
   * 
   * @return name of this human player
   */
  @Override
  public String getName() {
    return myName;
  }
}
