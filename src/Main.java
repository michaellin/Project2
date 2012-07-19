package src;

import java.util.Scanner;
import java.io.File;

class Main {
    public static void main(String[] args) {
        LexiconTrie test = new LexiconTrie();
        File wordList = new File("smalltestwords.txt");
        Scanner sc = null;
        try {
        	sc = new Scanner(wordList);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        test.load(sc);
        System.out.print("for gorilla ");
        System.out.println(test.contains("gorilla"));
    }
}
