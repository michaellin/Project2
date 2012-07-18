package src;
import java.util.List;

import junit.framework.TestCase;

public class WordOnBoardFinderTest extends TestCase {
  /* TODO: Write extensive test cases for EVERY non-trivial piece of code.
   * Private helper methods should have associated public static tester methods
   * in WordOnBoardFinder.java. */
  private BoardMaker myMaker;
  private WordOnBoardFinder myFinder;

  /**
   * Code that is run before each test method (automatically)
   */
  public void setUp() {
    myMaker = new BoardMaker();
    myFinder = new WordOnBoardFinder();
  }

  /**
   * Given a board and list of board cells, return a string of the concatenated
   * cell characters.
   * 
   * @param board
   *          The board to look at
   * @param list
   *          The list of cells to combine
   * @return A string comibining the cells
   */
  private String getWord(BoggleBoard board, List<BoardCell> list) {
    String word = "";
    for (BoardCell cell : list) {
      word += board.getFace(cell.row, cell.col);
    }
    return word;
  }

  /**
   * This tests that we walk along the corners successfully. It is a very basic
   * test- if you pass it, you should not assume that your code will work in all
   * cases.
   */
  public void testGoodCorners() {
    // This is how you can create a test board:
    String[] boardContents = { "atruq", "seana", "niotc", "bdenk", "armin" };
    BoggleBoard board = myMaker.makeBoard(boardContents);
    for (int i = 0 ; i<board.size() ; i++) {
    	for (int e = 0 ; e<board.size() ; e++) {
    		System.out.print(board.getFace(i,e));
    	}
    	System.out.print("\n");
    }

    String[] cornerWords = { "ate", "noted", "net", "urn", "bind", "aside",
                            "noise", "quack", "armin", "kin", "den", "act", 
                            "arm", "bin", "eat", "mink" };
    for (String s : cornerWords) {
      List<BoardCell> list = myFinder.cellsForWord(board, s);
      String word = getWord(board, list);
      System.out.println("s is " + s);
      System.out.println("word is " + word);
      assertEquals("fail for " + s, s, word);
    }
  }
  
  public void testMultiCase() {
	  // Mainly to be able to find a word with a specific suffix appended, "peaceful", given the fact it's possible to make 
	  // the word, "peace", many ways
	  String[] boardContents = { "abkxb", "apeua", "yeafb", "lrcey", "ufetr" };
	  BoggleBoard board = myMaker.makeBoard(boardContents);
	  for (int i = 0 ; i<board.size() ; i++) {
		  for (int e = 0 ; e<board.size() ; e++) {
			  System.out.print(board.getFace(i,e));
		  }
		  System.out.print("\n");
	  }

	  String[] words = { "peace", "peaceful", "baby", "fear", "rye", 
			  			"fry", "beef", "beer", "creep", "perfect", "free", 
			  			"peer"};
	  for (String s : words) {
		  List<BoardCell> list = myFinder.cellsForWord(board, s);
		  String word = getWord(board, list);
	      System.out.println("s is " + s);
	      System.out.println("word is " + word);
		  assertEquals("fail for " + s, s, word);
	  }
  }
  
  public void testOneQuack() {
	  // There should really only be one word that can be made on this board, "quack", and there are many ways to almost get it,
	  // so this tests to see if the wordfinder can find the one and only quack correctly (qu is a special case, making it special)
	  String[] boardContents = { "qacxx", "xxcaq", "qqxxx", "acxck", "caxaq" };
	  BoggleBoard board = myMaker.makeBoard(boardContents);
	  for (int i = 0 ; i<board.size() ; i++) {
		  for (int e = 0 ; e<board.size() ; e++) {
			  System.out.print(board.getFace(i,e));
		  }
		  System.out.print("\n");
	  }
	  String[] words = { "quack" };
	  for (String s : words) {
		  List<BoardCell> list = myFinder.cellsForWord(board, s);
		  String word = getWord(board, list);
		  assertEquals("fail for " + s, s, word);
	  }
  }
  
  public void testFindWonderful() {
	  // There are a number of words that can be made from only the repeating letters "w", "o", "n", "d", "e", "r", "f", "u", "l",
	  // but on the board made below there is only one way to make "wonderful" (and MANY ways to ALMOST make it), so this is also
	  // a crucial test case
	  String[] boardContents = { "owonu", "owufu", "nolrf", "dnder", "dderr" };
	  BoggleBoard board = myMaker.makeBoard(boardContents);
	  for (int i = 0 ; i<board.size() ; i++) {
		  for (int e = 0 ; e<board.size() ; e++) {
			  System.out.print(board.getFace(i,e));
		  }
		  System.out.print("\n");
	  }
	  String[] words = { "fun", "won", "free", "deer", "red", "nod", "loner",
			  			"lone", "now", "reed", "need", "leer", "wonderful" };
	  for (String s : words) {
		  List<BoardCell> list = myFinder.cellsForWord(board, s);
		  String word = getWord(board, list);
		  assertEquals("fail for " + s, s, word);
	  }
  }
  
  public void testHelpersWork() {
	 //Test private helper methods cellsForWordHelper and numberOfQu
	  assertTrue(WordOnBoardFinder.helpersWork());
  }
}
