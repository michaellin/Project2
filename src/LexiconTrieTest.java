package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import junit.framework.TestCase;

public class LexiconTrieTest extends TestCase {
  /* TODO: Write extensive test cases for EVERY non-trivial piece of code.
   * Private helper methods should have associated public static tester methods
   * in WordOnBoardFinder.java. */

  /**
   * Your test dictionary, containing words in smalltestwords.txt
   */
  private Scanner smallWords;
  private Scanner bigWords;
  LexiconTrie l;

  /**
   * Open smalltestwords.txt and give to the words instance variable. This is
   * automatically run before each test.
   */
  public void setUp() {
    try {
      bigWords = new Scanner(new File("bogwords.txt"));
      smallWords = new Scanner(new File("smalltestwords.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      assertTrue(false);
    }
    l = new LexiconTrie();
    l.load(bigWords);
  }

  /**
   * Closes smalltestwords.txt after each test.
   */
  public void tearDownSmall() {
    smallWords.close();
  }

  /**
   * Closes bogwords.txt after each test.
   */
  public void tearDownBig() {
    bigWords.close();
  }

  /**
   * A basic test for the lexicon. You should not assume that passing this test
   * means that your code works.
   */
  public void testSmallLexicon() {
    LexiconTrie l = new LexiconTrie();
    l.load(smallWords);
    assertTrue(l.contains("gorilla"));
    assertFalse(l.contains("armin"));
  }

  @Deprecated
  /**
   * A big data set test for the lexicon. 
   */
  //public void testBigLexicon() {
    //l.load(bigWords);
  //}
  
  public void testBigLexiconSpeed() {
  	assertTrue(l.contains("civil"));
  	assertFalse(l.contains("agitatet"));
  }
  
}
