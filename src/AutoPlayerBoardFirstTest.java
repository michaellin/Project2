package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import junit.framework.TestCase;

public class AutoPlayerBoardFirstTest extends TestCase{
	
	private Scanner words;
	
	private String getWord(BoggleBoard board, List<BoardCell> list) {
	    String word = "";
	    for (BoardCell cell : list) {
	      word += board.getFace(cell.row, cell.col);
	    }
	    return word;
	  }
	
	public void setUp() {
	    try {
	      words = new Scanner(new File("bogwords.txt"));
	    } catch (FileNotFoundException e) {
	      System.out.println(e);
	      assertTrue(false);
	    }
	  }

	public void testBoardFirstAutoPlayer1(){
		BoardMaker myMaker = new BoardMaker();
		WordOnBoardFinder finder = new WordOnBoardFinder();
		LexiconInterface lexicon = new LexiconTrie();
		lexicon.load(words);
		AutoPlayerBoardFirst compPlayer = new AutoPlayerBoardFirst(lexicon);
		String[] boardContents = { "owonu", "owufu", "nolrf", "dnder", "dderr" };
		BoggleBoard board = myMaker.makeBoard(boardContents);
		compPlayer.findAllValidWords(board, lexicon, 3);
		String[] words = { "fun", "won", "free", "deer", "red", "nod", "loner",
				"lone", "now", "reed", "need", "leer", "wonderful" };
		ArrayList<String> wordList = new ArrayList<String>();
		for (String s : words) {
			List<BoardCell> list = finder.cellsForWord(board, s);
			String word = getWord(board, list);
			wordList.add(word);
		}
		assertTrue(wordList.contains("wonderful") && compPlayer.myWords.contains("wonderful"));
		assertTrue(wordList.contains("need") && compPlayer.myWords.contains("need"));
		assertTrue(wordList.contains("deer") && compPlayer.myWords.contains("deer"));
		assertTrue(wordList.contains("free") && compPlayer.myWords.contains("free"));
	}
	
	public void testBoardFirstAutoPlayer2(){
		BoardMaker myMaker = new BoardMaker();
		WordOnBoardFinder finder = new WordOnBoardFinder();
		LexiconInterface lexicon = new LexiconTrie();
		lexicon.load(words);
		AutoPlayerBoardFirst compPlayer = new AutoPlayerBoardFirst(lexicon);
		String[] boardContents = { "qacxx", "xxcaq", "qqxxx", "acxck", "caxaq" };
		BoggleBoard board = myMaker.makeBoard(boardContents);
		compPlayer.findAllValidWords(board, lexicon, 3);
		String[] words = { "quack" };
		ArrayList<String> wordList = new ArrayList<String>();
		for (String s : words) {
			List<BoardCell> list = finder.cellsForWord(board, s);
			String word = getWord(board, list);
			wordList.add(word);
		}
		assertTrue(wordList.contains("quack") && compPlayer.myWords.contains("quack"));
	}
	
	public void testBoardFirstAutoPlayer3(){
		BoardMaker myMaker = new BoardMaker();
		WordOnBoardFinder finder = new WordOnBoardFinder();
		LexiconInterface lexicon = new LexiconTrie();
		lexicon.load(words);
		AutoPlayerBoardFirst compPlayer = new AutoPlayerBoardFirst(lexicon);
		String[] boardContents = { "atruq", "seana", "niotc", "bdenk", "armin" };
		BoggleBoard board = myMaker.makeBoard(boardContents);
		compPlayer.findAllValidWords(board, lexicon, 3);
		String[] words = { "ate", "note", "net", "urn", "bind", "aside",
                "noise", "quack", "kin", "den", "act", "arm", "bin", "eat", "mink" };
		ArrayList<String> wordList = new ArrayList<String>();
		for (String s : words) {
			List<BoardCell> list = finder.cellsForWord(board, s);
			String word = getWord(board, list);
			wordList.add(word);
		}
		assertTrue(wordList.contains("note") && compPlayer.myWords.contains("note"));
		assertTrue(wordList.contains("aside") && compPlayer.myWords.contains("aside"));
		assertTrue(wordList.contains("noise") && compPlayer.myWords.contains("noise"));
		assertTrue(wordList.contains("mink") && compPlayer.myWords.contains("mink"));
	}

}
