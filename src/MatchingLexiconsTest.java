package src;

import static org.junit.Assert.*;
import java.util.Scanner;
import java.io.*;
import java.util.*;

import org.junit.Test;

import junit.framework.TestCase;

public class MatchingLexiconsTest extends TestCase{
	private Scanner words;
	//private Scanner sameWords;
	/**
	 * 
	 */
	public void setUp() {
	    try {
	      words = new Scanner(new File("bogwords.txt"));
	    } catch (FileNotFoundException e) {
	      System.out.println(e);
	      assertTrue(false);
	    }
	  }

	
	/*public void setUp2(){
		try{
			sameWords = new Scanner(new File("bogwords.txt"));
		}catch(FileNotFoundException e){
			System.out.println(e);
				assertTrue(false);
		}
	}*/
	
	public void testMatchingLexicons() {
		BoardMaker myMaker = new BoardMaker();
		LexiconInterface lexAL = new LexiconArrayList();
		LexiconInterface lexTrie = new LexiconTrie();
		lexAL.load(words);
		lexTrie.load(words);
		String[] boardContents = { "owonu", "owufu", "nolrf", "dnder", "dderr" };
		BoggleBoard board = myMaker.makeBoard(boardContents);
		AutoPlayerLexiconFirst lexie = new AutoPlayerLexiconFirst(lexAL);
		AutoPlayerLexiconFirst lexor = new AutoPlayerLexiconFirst(lexTrie);
		AutoPlayerBoardFirst boardie = new AutoPlayerBoardFirst(lexAL);
		AutoPlayerBoardFirst boarder = new AutoPlayerBoardFirst(lexTrie);
		lexie.findAllValidWords(board, lexAL, 3);
		lexor.findAllValidWords(board, lexTrie, 3);
		boardie.findAllValidWords(board, lexAL, 3);
		boarder.findAllValidWords(board, lexTrie, 3);
		System.out.println("The size of lexie's words is: " + lexie.myWords.size());
		System.out.println("The size of lexor's words is: " + lexor.myWords.size());
		System.out.println("The size of boardie's words is: " + boardie.myWords.size());
		System.out.println("The size of boarder's words is: " + boarder.myWords.size());
		assertTrue(lexie.myWords.size() == lexor.myWords.size() 
				/*&& lexor.myWords.size() == boardie.myWords.size()
				&& boardie.myWords.size() == boarder.myWords.size()*/);
	}
	
}
