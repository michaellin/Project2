package src;

import java.io.File;
import java.util.Scanner;

public class SpeedTestAutoPlayerBoardFirst {
	public static void main(String[] args) {
		BoardMaker myMaker = new BoardMaker();
        LexiconTrie lex = new LexiconTrie();
        File wordList = new File("bogwords.txt");
        Timer t = new Timer();
        Scanner sc = null;
        try {
        	sc = new Scanner(wordList);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        lex.load(sc);
	AutoPlayerBoardFirst playerBF = new AutoPlayerBoardFirst(lex);
	String[] boardContents = { "atruq", "seana", "niotc", "bdenk", "armin" };
	BoggleBoard board = myMaker.makeBoard(boardContents);
	t.start();
	playerBF.findAllValidWords(board, lex, 3);
	long time = t.stop();
	System.out.println("The number of words found is " + playerBF.myWords.size());
	System.out.println("Time elapsed is " + time + " milliseconds");
	}
}
