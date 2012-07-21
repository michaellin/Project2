package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import junit.framework.TestCase;

public class LexiconArrayListTest extends TestCase {
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
      words = new Scanner(new File("bogwords.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
      assertTrue(false);
    }
  }

  /**
   * Closes smalltestwords.txt or bogwords.txt after each test.
   */
  public void tearDown() {
    words.close();
  }

  /**
   * A basic test for the lexicon. You should not assume that passing this test
   * means that your code works.
   */
  public void testLexicon() {
    LexiconArrayList l = new LexiconArrayList();
    l.load(words);
    assertTrue(l.contains("gorilla"));
    assertTrue(l.contains("every"));
    assertTrue(l.contains("armistice"));
    assertTrue(l.contains("arm")); 
    assertTrue(l.contains("err"));
    assertTrue(l.contains("even"));
    assertTrue(l.contains("civil"));
    assertTrue(l.contains("evergreen"));
    assertFalse(l.contains("agitaet"));
    assertFalse(l.contains("eventf"));
    assertFalse(l.contains("armin"));
    assertFalse(l.contains("aloze")); 
    assertFalse(l.contains("outu"));
    assertFalse(l.contains("wonderf"));
    assertTrue(l.containsPrefix("a"));
    assertTrue(l.containsPrefix("ab"));
    assertTrue(l.containsPrefix("e"));
    assertTrue(l.containsPrefix("ev"));
    assertTrue(l.containsPrefix("eve"));
    assertTrue(l.containsPrefix("ever"));
    assertTrue(l.containsPrefix("everg")); 
    assertTrue(l.containsPrefix("evergr"));
    assertTrue(l.containsPrefix("evergre"));
    assertTrue(l.containsPrefix("evergree"));
    assertTrue(l.containsPrefix("evergreen")); //tested for word "evergreen" by containsPrefix
  }
}
