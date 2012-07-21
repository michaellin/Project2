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

import java.util.Scanner;
import java.util.HashMap;
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

    /**
     * If the prefix is in the lexicon, returns true.
     * 
     * @param s The word to search for.
     * @return True if the lexicon contains s.
     */
    public boolean containsPrefix (String s) {
    	return containsPrefixHelper(s, this.myRoot, 1);
    }
    
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
    
    /**
     * If the word is in the lexicon, returns true.
     * 
     * @param s The word to search for.
     * @return True if the lexicon contains s.
     */
    public boolean contains (String s) {
    	return containsHelper(s, this.myRoot, 1);
    }

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

    public TrieIterator iterator() {
        return new TrieIterator();
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

    /**
     * A TrieNode that contains the prefixes 
     * and its subTries.
     */
    private static class TrieNode {
        private HashMap<String, Boolean> inDic;
        private HashMap<String, TrieNode> subTrie;
        private ArrayList<String> itrDic;
        private ArrayList<String> itrPrefix;
        private int itrIndex;
        private boolean flag;

        /**
         * Constructor for TrieNode
         */
        public TrieNode() {
            inDic = new HashMap<String, Boolean> ();
            subTrie = new HashMap<String, TrieNode> (); 
            itrDic = new ArrayList<String> ();
            itrPrefix = new ArrayList<String> ();
            itrIndex = 0;
            flag = false;
        }
        
        public String toString() {
        	return subTrie.toString();
        }

        /**
         * This should hash the word and search
         * for that because of the overriden
         * hashCode()
         */
        public boolean containsWord (String word) {
            Boolean toRtn = inDic.get(word);
            return (toRtn != null)? toRtn : false;
        }

        public boolean containsPrefix(String prefix) {
            return subTrie.containsKey(prefix);
        }

        public void put (String childWord) {
            itrPrefix.add(childWord);
            subTrie.put(childWord, new TrieNode());
        }

        /**
         * Returns the Children of the current Node
         */
        public TrieNode get (String word) {
            return subTrie.get(word);
        }
        
        public ArrayList<String> getAllPrefixes () {
            return itrPrefix;
        }

        public void putInDic(String word) {
            flag = true;
            inDic.put(word, true);
            itrDic.add(word);
        }

        /* TODO */
        public boolean hasNext() {
            return flag; //I'm using a flag because I dont want to 
                         //remove from the arrayList.
        }
        
        public String next() {
            //System.out.println(itrIndex);
            //System.out.println(flag);
            String toRtn = itrDic.get(itrIndex);
            itrIndex++;
            if (itrIndex > itrDic.size() - 1) {
                flag = false;
            }
            return toRtn;
        }
    }

    public class TrieIterator implements Iterator {
        private LinkedList<TrieNode> nodeQueue;
        private TrieNode currentNode;

        public TrieIterator() {
            nodeQueue = new LinkedList<TrieNode> ();
            currentNode = myRoot;
            nodeQueue.add(currentNode);
        }

        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        }

        public String next() {
            if (currentNode.hasNext()) {
                return currentNode.next();
            } else {
            	while (!currentNode.hasNext()) {
            		ArrayList<String> allPrefixes = currentNode.getAllPrefixes();
            		for (String str : allPrefixes) {  //Getting all the possibilities
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
        }
    }
}
