package src;

import java.util.Scanner;
import java.io.File;

public class SpeedTestLexiconArrayList {
	
    public static void main(String[] args) {
        LexiconArrayList test = new LexiconArrayList();
        File wordList = new File("bogwords.txt");
        Timer t = new Timer();
        Scanner sc = null;
        try {
        	sc = new Scanner(wordList);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        test.load(sc);
    
        t.start();
        for(int i = 0; i< 1000; i++){
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
        	test.contains("hoohi");
        	test.contains("randomawtb");
        	test.contains("reallist");
        }
        long time = t.stop();
        System.out.println(time + " miliseconds elapsed");
    }
}
