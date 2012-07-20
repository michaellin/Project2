package src;

import java.util.Scanner;
import java.io.File;

class Main {
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
        t.start();
        test.contains("civil");
        System.out.println(t.stop());
    }
}
