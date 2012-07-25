package src;
/**
 * Copyright (C) 2002 Michael Green <mtgreen@cs.ucsd.edu>
 * 
 * Copyright (C) 2002 Paul Kube <kube@cs.ucsd.edu>
 * 
 * Copyright (C) 2005 Owen Astrachan <ola@cs.duke.edu>
 * 
 * Copyright (C) 2011 Hoa Long Tam <hoalong.tam@berkeley.edu> and Armin Samii
 * <samii@berkeley.edu>
 * 
 * This file is part of CS Boggle.
 * 
 * CS Boggle is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * CS Boggle is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * CS boggle. If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

public class LexiconTrie implements LexiconInterface {
	
	/**
	 * The HashMap that contains all the Mapping of prefixes
	 * to more HashMaps. The non-prefix words are placed at the
	 * leaves of this Trie
	 */
	private TrieNode myRoot;
	
	/**
	 * Constructor that initializes the root HashMap.
	 */
	public LexiconTrie() {
		myRoot = new TrieNode();
	}
	
	public String toString() {
		return myRoot.toString();
	}

	/**
	 * Add the word and all its prefixes in different levels of
	 * the trie, with the word at the lowest level.
	 */
	public void add (String word) {
        if (!word.equals("")) {
            addHelper(word, this.myRoot, 1);
        } else {
            throw new IllegalArgumentException("Can\'t store empty string");
        }
	}

	/**
	 * Helper function for add. Recurses through the trie until
	 * the word is stored at its corresponding level.
	 */
	private static void addHelper (String word, TrieNode node, int level) {
		String prefix = word.substring(0, level); 
		if (!word.equals(prefix)) {
			if (!node.containsPrefix(prefix)) {
				node.put(prefix);
			}
			addHelper(word, node.get(prefix), level + 1);
		} else {
			node.put(prefix);
            node.putInDic(word);
		}
	}

    private static boolean addHelperWorks() {
        TrieNode test = new TrieNode();
        addHelper("hello", test, 1);
        boolean first = containsPrefixHelper("he", test, 1);
        boolean second = containsHelper("hello", test, 1);
        return first && second;
    }


    /**
     * If the prefix is in the lexicon, returns true.
     * 
     * @param s The word to search for.
     * @return True if the lexicon contains s.
     */
    public boolean containsPrefix (String s) {
    	return containsPrefixHelper(s, this.myRoot, 1);
    }
    
    /**
     * Helper method for containsPrefix. Recurses down into the trie to check if the
     * prefix is valid.
     * @return boolean
     */
    private static boolean containsPrefixHelper (String s, TrieNode node, int level) {
    	String prefix = s.substring(0, level);
    	if (!s.equals(prefix)) {
    		if (node.containsPrefix(prefix)) {
    			return containsPrefixHelper(s, node.get(prefix), level + 1);
    		} else {
    			return false;
    		}
    	} else {
    		return node.containsPrefix(s);
    	}
    }

    private static boolean containsPrefixHelperWorks() {
        TrieNode test = new TrieNode();
        addHelper("hello", test, 1);
        addHelper("world", test, 1);
        boolean first = containsPrefixHelper("he", test, 1);
        boolean second = containsPrefixHelper("hello", test, 1);
        boolean third = containsPrefixHelper("wor", test, 1);
        boolean fourth = containsPrefixHelper("worlds", test, 1);
        return first && second && third && !fourth;
    }
    
    /**
     * If the word is in the lexicon, returns true.
     * 
     * @param s The word to search for.
     * @return True if the lexicon contains s.
     */
    public boolean contains (String s) {
    	return containsHelper(s, this.myRoot, 1);
    }

    /**
     * Helper method for contains(). Recurses down into the trie and check if the
     * word is contained inDic at the prefix node or leaf.
     * @return boolean
     */
    private static boolean containsHelper (String s, TrieNode node, int level) {
		String prefix = s.substring(0, level); 
		if (!s.equals(prefix)) {
			if (node.containsPrefix(prefix)) {
                return containsHelper(s, node.get(prefix), level + 1);
			} else {
                return false;
            }
		} else {
            return node.containsWord(s);
		}
	}

    private static boolean containsHelperWorks() {
        TrieNode test = new TrieNode();
        addHelper("hello", test, 1);
        addHelper("cat", test, 1);
        addHelper("world", test, 1);
        boolean first = containsHelper("cat", test, 1);
        boolean second = containsHelper("hello", test, 1);
        boolean third = containsHelper("wor", test, 1);
        boolean fourth = containsHelper("world", test, 1);
        return first && second && !third && fourth;
    }

    public TrieIterator iterator() {
        return new TrieIterator();
    }
    
    private static boolean iteratorWorks() {
    	LexiconTrie lex = new LexiconTrie();
    	Scanner mySmallDictionary = null;
        try {
            mySmallDictionary = new Scanner(new File("smalltestwords.txt"));
          } catch (FileNotFoundException e) {
            System.out.println(e);
          }
    	lex.load(mySmallDictionary);
    	Iterator itr = lex.iterator();
    	ArrayList<String> check = new ArrayList<String> ();
    	while (itr.hasNext()) {
    		check.add((String) itr.next());
    	}
    	System.out.println(check.size());
    	if (check.size() == 10) {
    		return true;
    	} else {
    		return false;
    	}
    }

	/**
	 * Load the words from an input source and store them in this lexicon.
	 * 
	 * @param input A scanner that will provide the entire dictionary.
	 */
    public void load(Scanner input) {
        while (input.hasNext()) {
            String nextStr = input.next();
            add(nextStr);

      /* TODO: Store the dictionary efficiently. You must implement a trie here.
       * 
       * Use good programming practices here:
       * - Keep instance variables private
       * - Use methods to break up your code into logical segments.
       * - Write tests for EVERY non-trivial method.
       */
        }
    }
    
    public static boolean helpersWork() {
        if (!addHelperWorks()) {
            return false;
        }
        if (!containsPrefixHelperWorks()) { 
            return false;
        }
        if (!containsHelperWorks()) {
            return false;
        }
        if (!iteratorWorks()) {
        	return false;
        }
        return true;
    }

    /**
     * A TrieNode that stores words as prefixes in
     * different Trie levels.
     * inDic determines if the prefix is an existing word.
     * subTrie contains the existing word. HashSet is used for fast lookup.
     * itrDic and itr Prefix are used for an easier iteration over
     * the words or prefixes.
     * 
     * Note: The TrieNodes themselves don\'t contain their identities (their words).
     * 		 That is contained in the subTrie of their parent TrieNode.
     * 
     */
    private static class TrieNode {
        private HashSet<String> inDic;
        private HashMap<String, TrieNode> subTrie;
        private ArrayList<String> itrDic;
        private ArrayList<String> itrPrefix;
        private int itrIndex;
        private boolean flag;

        /**
         * Constructor for TrieNode
         */
        public TrieNode() {
            inDic = new HashSet<String> ();
            subTrie = new HashMap<String, TrieNode> (); 
            itrDic = new ArrayList<String> ();
            itrPrefix = new ArrayList<String> ();
            itrIndex = 0;
            flag = false;
        }
        
        /**
         * A recursive toString through the Trie.
         * @return String
         */
        public String toString() {
        	return subTrie.toString();
        }

        /**
         * A fast lookup for if the queried prefix is 
         * an existing word.
         * @return boolean
         */
        public boolean containsWord (String word) {
            return inDic.contains(word);
        }

        /**
		 * A fast lookup for if the queried prefix exists.
		 * @return boolean
         */
        public boolean containsPrefix(String prefix) {
            return subTrie.containsKey(prefix);
        }

        /**
         * Adds the new Prefix into the itrPrefix
         * and also hashes it with a new Child TrieNode
         * in subTrie.
         * 
         */
        public void put (String childWord) {
            itrPrefix.add(childWord);
            subTrie.put(childWord, new TrieNode());
        }

        /**
         * Returns the child of the current Node.
         * @return TrieNode
         */
        public TrieNode get (String word) {
            return subTrie.get(word);
        }
        
        /**
         * Returns a collection of all the prefix children 
         * contained in a TrieNode.
         * @return ArrayList
         */
        public ArrayList<String> getAllPrefixes () {
            return itrPrefix;
        }

        public void putInDic(String word) {
            flag = true;
            inDic.add(word);
            itrDic.add(word);
        }

        /**
         * This convenience method is used to iterate over each
         * TrieNode's children. The flag is only true after the
         * first child has been added and it turns false only when
         * itrIndex has reached the last item in itrDic.
         * @return
         */
        public boolean hasNext() {
            return flag;
        }
        
        /**
         * Returns the prefix child at the itrIndex position.
         * itrIndex is the index of the child about to be returned.
         * @return
         */
        public String next() {
            String toRtn = itrDic.get(itrIndex);
            itrIndex++;
            if (itrIndex > itrDic.size() - 1) {
                flag = false;
            }
            return toRtn;
        }
    }

    /**
     * LexiconTrie's iterator.
     * @author MichaelLin
     *
     */
    public class TrieIterator implements Iterator {
        private LinkedList<TrieNode> nodeQueue;
        private TrieNode currentNode;

        /**
         * Iterator's constructor.
         * Uses a queue to do a breath first search.
         * 
         */
        public TrieIterator() {
            nodeQueue = new LinkedList<TrieNode> ();
            currentNode = myRoot;
            nodeQueue.add(currentNode);
        }

        
        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        }

        /**
         * Iterates over a TrieNode. If the TrieNode doesn't have
         * a next it will add all its child and change the current
         * visited node to the next node in the queue. 
         */
        public String next() {
            if (currentNode.hasNext()) {
                return currentNode.next();
            } else {
            	while (!currentNode.hasNext()) {
            		ArrayList<String> allPrefixes = currentNode.getAllPrefixes();
            		for (String str : allPrefixes) {
            			nodeQueue.add(currentNode.get(str));
            		}
            		if (nodeQueue.size() == 0) {
            			return "";
            		} else {
            			currentNode = nodeQueue.removeFirst();
            		}
            	}
                return currentNode.next();
            }
        }

        public void remove() {
        	throw new UnsupportedOperationException("Remove method not supported");
        }
    }
}
