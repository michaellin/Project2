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
import java.util.Collections;
import java.util.Random;

/**
 * Supply a random BoggleBoard that's either 4x4 or 5x5 using standard cubes
 * from Boggle and Big Boggle, respectively. Uses the BoggleFactory's random
 * number generator.
 * <P>
 * 
 * @author Owen Astrachan
 * 
 */
public class BoardMaker {
  
  private final ArrayList<Cube> myDice16;
  private final ArrayList<Cube> myDice25;
  
  public BoardMaker() {
    myDice16 = new ArrayList<Cube>();
    myDice25 = new ArrayList<Cube>();
    initDice();
  }
  
  /**
   * Initialize Dice list with official boggle cubes for 4x4 game and 5x5 game
   * (Big Boggle)
   * 
   */
  
  private static String[] bigStrings = { "aafirs", "adennn", "aaafrs",
                                        "aeegmu", "aaeeee", "aeeeem", "afirsy",
                                        "aegmnn", "bjkqxz", "ceipst", "ceiilt",
                                        "ccnstw", "ceilpt", "ddlonr", "dhlnor",
                                        "dhhlor", "dhhnot", "ensssu", "emottt",
                                        "eiiitt", "fiprsy", "gorrvw", "hiprry",
                                        "nootuw", "ooottu" };
  
  private static String[] dieStrings = { "aaeegn", "abbjoo", "achops",
                                        "affkps", "aoottw", "cimotu", "deilrx",
                                        "delrvy", "distty", "eeghnw", "eeinsu",
                                        "ehrtvw", "eiosst", "elrtty", "himnqu",
                                        "hlnnrz" };
  
  private void initDice() {
    for (String s : dieStrings) {
      myDice16.add(new Cube(s));
    }
    for (String s : bigStrings) {
      myDice25.add(new Cube(s));
    }
  }
  
  /**
   * Returns random boggle board of specified number of rows, boards are square.
   * 
   * @param rows
   *          is number of rows (and columns) in returned board.
   */
  public BoggleBoard makeBoard(int rows) {
    return new BoggleBoard(getRandomBoard(rows * rows));
  }
  
  /**
   * Returns a board based on the given string array. The number of rows must
   * equal the number of columns, which must equal either 4 or 5. Each letter
   * must be a single character. To have a "qu", only write "q".
   * 
   * For example, a valid input would look like: { "duck" "qack" // This is how
   * you would write "quack" "lemo" "nade" }
   * 
   * @param letterList
   *          A 1D square array of either 4 or 5 elements, with each string
   *          being equal to exactly length() characters. All "qu" board squares
   *          should be written as just "q".
   * @return A BoggleBoard based on the given letter list.
   */
  public BoggleBoard makeBoard(String[] letterList) {
    int size = letterList.length;
    
    // Iterate through board contents, checking for validity and converting
    // to a 1D array.
    String[] boardContents = new String[size * size];
    for (int i = 0; i < size; ++i) {
      if (size != letterList[i].length()) {
        throw new IllegalArgumentException("Must be a square board!");
      }
      
      for (int j = 0; j < size; ++j) {
        String character = "" + letterList[i].charAt(j);
        if (character.equals("q")) {
          character += "u";
        }
        if (!isValidBoardEntry(character)) {
          throw new IllegalArgumentException("Must be a single character at each index.");
        }
        boardContents[i * size + j] = character;
      }
    }
    return new BoggleBoard(boardContents);
  }
  
  /**
   * Ensures s can be used as a boggle board entry (only a letter or Qu)
   * 
   * @param s
   *          The string to check
   * @return True if it is valid
   */
  private boolean isValidBoardEntry(String s) {
    if (s.length() == 2) {
      if (s.toLowerCase().equals("qu")) {
        return true;
      } else {
        return false;
      }
    } else if (s.length() != 1) {
      return false;
    } else if (!Character.isLetter(s.charAt(0))) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Return an array of Strings showing the sequence of faces on a randomly
   * generated board.
   */
  
  private String[] getRandomBoard(int totalSquares) {
    
    ArrayList<Cube> dice = myDice16;
    if (totalSquares > 16) {
      dice = myDice25;
    }
    String[] letterList = new String[totalSquares];
    Random rand = BoggleBoardFactory.getRandom();
    Collections.shuffle(dice, rand);
    
    for (int i = 0; i < totalSquares; i++) {
      Cube d = dice.get(i % dice.size());
      letterList[i] = d.getRandomFace();
    }
    return letterList;
  }
  
}
