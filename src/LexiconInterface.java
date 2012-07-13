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

import java.util.Scanner;

public interface LexiconInterface {
  /**
   * Load the words from an input source and store them in this lexicon.
   * 
   * @param input
   *          A scanner that will provide the entire dictionary.
   */
  public void load(Scanner input);

  /**
   * If the word is in the lexicon, returns true.
   * 
   * @param s
   *          The word to search for.
   * @return True if the lexicon contains s.
   */
  public boolean contains(String s);

  /**
   * If the prefix is in the lexicon, returns true. In other words, if there is
   * a word in the lexicon that begins with s, returns true.
   * 
   * @param s
   *          The word to search for.
   * @return True if the lexicon contains s.
   */
  public boolean containsPrefix(String s);
}
