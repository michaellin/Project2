package src;

import java.util.Scanner;
import java.io.File;

class SpeedTestLexiconTrie {
    public static void main(String[] args) {
        LexiconTrie LexTrie = new LexiconTrie();
        LexiconArrayList LexArr = new LexiconArrayList();
        File wordList = new File("bogwords.txt");
        Timer t = new Timer();
        Scanner sc = null;
        try {
        	sc = new Scanner(wordList);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        LexTrie.load(sc);
        LexArr.load(sc);

        /* Start test for LexiconTrie */
        int tries = 1000;
        t.start();
        while (tries > 0) {
        	LexTrie.contains("administratrix");
        	tries --;
        }
        LexTrie.contains("gorilla");
        LexTrie.contains("every");
        LexTrie.contains("ever"); 
        LexTrie.contains("armistice");
        LexTrie.contains("arm"); 
        LexTrie.contains("err");
        LexTrie.contains("even");
        LexTrie.contains("civil");
        LexTrie.contains("agitaet");
        LexTrie.contains("eventf");
        LexTrie.contains("armin");
        LexTrie.contains("aloze"); 
        LexTrie.contains("outu"); 
        LexTrie.contains("everg");
        long time1 = t.stop();
        System.out.println(time1 + " miliseconds elapsed");
        /* End test for Lexicon Trie */

        /* Start test for Lexicon ArrayList */
        tries = 1000;
        t.start();
        while (tries > 0) {
        	LexArr.contains("administratrix");
        	tries --;
        }
        LexArr.contains("gorilla");
        LexArr.contains("every");
        LexArr.contains("ever"); 
        LexArr.contains("armistice");
        LexArr.contains("arm"); 
        LexArr.contains("err");
        LexArr.contains("even");
        LexArr.contains("civil");
        LexArr.contains("agitaet");
        LexArr.contains("eventf");
        LexArr.contains("armin");
        LexArr.contains("aloze"); 
        LexArr.contains("outu"); 
        LexArr.contains("everg");
        long time2 = t.stop();
        System.out.println(time2 + " miliseconds elapsed");
        /* End test for Lexicon ArrayList */
    }
}
