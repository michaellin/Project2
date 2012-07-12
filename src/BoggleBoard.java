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
 * Represent a Boggle Board to play a game of Boggle.
 * 
 * @author Owen Astrachan
 * @date March, 2008
 */
public class BoggleBoard {
  
  private final String[] myFaces;
  private final int mySize;
  
  /**
   * Create a square boggle board from an array of Strings/Cube faces. The array
   * should contain sizexsize entries, e.g., 16 for a 4x4 Boggle game.
   * 
   * @param faces
   *          is row-major order of cubes used in the board for Boggle
   */
  public BoggleBoard(String[] faces) {
    myFaces = faces;
    mySize = (int)Math.sqrt(faces.length);
  }
  
  /**
   * Returns dimension of a square board, e.g., 4 or 5 for Boggle or Big Boggle,
   * respectively.
   * 
   * @return size of board
   */
  public int size() {
    return mySize;
  }
  
  /**
   * Return the cube face at specified location, this will be a one-character
   * string, presumably, or "Qu" for a Q-cube.
   * 
   * @param row
   *          is row of cube whose face is returned
   * @param col
   *          is column of cube whose face is returned
   * @return String/face of specified cube
   * @throws ArrayIndexOutOfBoundsException
   *           if cube location isn't valid
   */
  public String getFace(int row, int col) {
    return myFaces[row * mySize + col];
  }
  
  /**
   * Checks if the point is within the bounds of this board.
   * 
   * @param r
   *          is the row to check
   * @param c
   *          is the column to check
   * @return true If in bounds
   */
  public boolean isInBounds(int r, int c) {
    return r >= 0 && r < mySize && c >= 0 && c < mySize;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int r = 0; r < mySize; r++) {
      for (int c = 0; c < mySize; c++) {
        sb.append(String.format("%3s", getFace(r, c)));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
