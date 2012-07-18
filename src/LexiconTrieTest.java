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
  private Scanner words;

  /**
   * Open smalltestwords.txt and give to the words instance variable. This is
   * automatically run before each test.
   */
  public void setUp() {
    try {
      words = new Scanner(new File("smalltestwords.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      assertTrue(false);
    }
  }

  /**
   * Closes smalltestwords.txt after each test.
   */
  public void tearDown() {
    words.close();
  }

  /**
   * A basic test for the lexicon. You should not assume that passing this test
   * means that your code works.
   */
  public void testLexicon() {
    LexiconTrie l = new LexiconTrie();
    l.load(words);
    assertTrue(l.contains("gorilla"));
    assertFalse(l.contains("armin"));
  }
}
