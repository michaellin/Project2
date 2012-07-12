package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import junit.framework.TestCase;

public class AutoPlayerTest extends TestCase {
  /* TODO: Write extensive test cases for EVERY non-trivial piece of code.
   * Private helper methods should have associated public static tester methods
   * in WordOnBoardFinder.java. */
  private BoardMaker myMaker;
  private LexiconInterface myLexicon;
  private AutoPlayerLexiconFirst myPlayer;
  private Scanner mySmallDictionary;
  
  /**
   * Code that is run before each test method (automatically)
   */
  public void setUp() {
    // Open the dictionary
    try {
      mySmallDictionary = new Scanner(new File("smalltestwords.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      assertTrue(false);
    }
    
    // Set up the instance variables
    myLexicon = new LexiconTrie();
    myLexicon.load(mySmallDictionary);
    myMaker = new BoardMaker();
    myPlayer = new AutoPlayerLexiconFirst(myLexicon);
  }
  
  /**
   * Closes smalltestwords.txt after each test.
   */
  public void tearDown() {
    mySmallDictionary.close();
  }
  
  /**
   * This tests that we walk along the corners successfully. It is a very basic
   * test- if you pass it, you should not assume that your code will work in all
   * cases.
   */
  public void testFindAllWords() {
    // This is how you can create a test board:
    String[] boardContents = { "qqqq", "qqqq", "qqqq", "duck", };
    BoggleBoard board = myMaker.makeBoard(boardContents);
    myPlayer.findAllValidWords(board, myLexicon, 4);
    
    assertEquals(myPlayer.wordCount(), 1);
    Iterator<String> i = myPlayer.iterator();
    assertEquals(i.next(), "duck");
    assertFalse(i.hasNext());
  }
}
