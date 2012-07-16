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

import java.util.Random;

/**
 * Factory for generating boggle boards. The current implementation uses
 * <code>StandardBoardMaker</code> which has standard Boggle and Big Boggle
 * cubes for 4x4 and 5x5 boards, respectively. The board generator/maker can be
 * changed via <code>setMaker</code>.
 * <P>
 * The factory is also the source for a game's random-number generator. This
 * facilitates consistent debugging, e.g., the default random number generator
 * uses a set seed so that the same sequence of games results every time since
 * the source of randomness is specified. This can be altered via
 * <code>setRandom</code>.
 * <P>
 * 
 * @author Owen Astrachan
 * 
 */
public class BoggleBoardFactory {
  
  private static Random ourRandom = new Random();
  private static BoardMaker ourMaker = new BoardMaker();
  
  /**
   * Return a random board of specified size using the factory's BoardMaker.
   * 
   * @param size
   *          specifies one dimension of square board returned
   * @return board of the specified size
   */
  public static BoggleBoard getBoard(int size) {
    return ourMaker.makeBoard(size);
  }
  
  /**
   * Change the board maker used by the factor.
   * 
   * @param maker
   *          is new Boggle board maker
   */
  public static void setMaker(BoardMaker maker) {
    ourMaker = maker;
  }
  
  /**
   * Return the factory's random number generator, this helps ensure consist
   * debugging or consistent play.
   * 
   * @return the factory's Random source
   */
  public static Random getRandom() {
    return ourRandom;
  }
  
  /**
   * Change the source of a game's random number generator.
   * 
   * @param r
   *          is the new source of random values for a game
   */
  public static void setRandom(Random r) {
    ourRandom = r;
    ourMaker = new BoardMaker();
  }
}
