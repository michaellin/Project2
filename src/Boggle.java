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

import java.io.InputStream;

/**
 * Play a game of Boggle. To play code must instantiate the BoggleGui gui object
 * with a working IWordOnBoardFinder and a working Lexicon, as well as a working
 * AutoPlayer.
 */

public class Boggle {
  
  public static void main(String[] args) {
    LexiconInterface lexicon = new LexiconTrie();
    WordOnBoardFinder finder = new WordOnBoardFinder();
    InputStream is = lexicon.getClass().getResourceAsStream("/bogwords.txt");

    AutoPlayerBoardFirst compPlayer = new AutoPlayerBoardFirst(lexicon);
    BoggleGUI bgui = new BoggleGUI(lexicon, finder, is, compPlayer);
  }
  
}
