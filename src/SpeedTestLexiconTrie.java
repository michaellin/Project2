package src;

import java.util.Scanner;
import java.io.File;

class SpeedTestLexiconTrie {
    public static void main(String[] args) {
        LexiconTrie test = new LexiconTrie();
        File wordList = new File("bogwords.txt");
        Timer t = new Timer();
        Scanner sc = null;
        try {
        	sc = new Scanner(wordList);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        test.load(sc);
        int tries = 1000;
        t.start();
        while (tries > 0) {
        	test.contains("administratrix");
        	tries --;
        }
        test.contains("gorilla");
        test.contains("every");
        test.contains("ever"); 
        test.contains("armistice");
        test.contains("arm"); 
        test.contains("err");
        test.contains("even");
        test.contains("civil");
        test.contains("agitaet");
        test.contains("eventf");
        test.contains("armin");
        test.contains("aloze"); 
        test.contains("outu"); 
        test.contains("everg");
        long time = t.stop();
        System.out.println(time + " miliseconds elapsed");
    }
}
