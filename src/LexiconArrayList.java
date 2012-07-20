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
import java.util.ArrayList;

public class LexiconArrayList implements LexiconInterface {
	/**
	 * Load the words from an input source and store them in this lexicon.
	 * 
	 * @param input
	 *          A scanner that will provide the entire dictionary.
	 */
	private ArrayList<String> LexiconArrayList = new ArrayList<String>();

	public void load(Scanner input) {
		while (input.hasNext()) {
			String nextStr = input.next();
			if (LexiconArrayList.isEmpty()) {
				LexiconArrayList.add(nextStr);
			}
			else {
				LexiconArrayList = sortedAdd(LexiconArrayList, nextStr, 0, (LexiconArrayList.size()/2), LexiconArrayList.size()-1);
			}
		}
	}

	/**
	 * Recursive helper add method which sorts the list while adding new elements using the same algorithm as
	 * binary search. 
	 * 
	 * @param currentList
	 *          The array list before the new element is added to it
	 *          
	 * @param nextStr
	 * 			The string to be added to the lexicon
	 *          
	 * @param indexLow
	 * 			The lowerbound of strings to look through in the lexicon
	 * 
	 * @param indexMid
	 * 			The middle index of strings to look through in the lexicon
	 * 
	 * @param indexHigh
	 * 			The upperbound of strings to look through in the lexicon
	 * 
	 * @return True if the lexicon contains s.
	 */
	private ArrayList<String> sortedAdd(ArrayList<String> currentList, String nextStr, int indexLow, int indexMid, int indexHigh) {
		if (nextStr.compareTo(currentList.get(indexLow)) < 0) {
			currentList.add(indexLow, nextStr);
		}
		else if (nextStr.compareTo(currentList.get(indexHigh)) > 0) {
			currentList.add(indexHigh+1,nextStr);
		}
		else if (nextStr.compareTo(currentList.get(indexLow)) > 0 && nextStr.compareTo(currentList.get(indexMid)) < 0) {
			indexLow++;
			indexMid--;
			if (indexLow > indexMid) {
				currentList.add(indexLow, nextStr);
			}
			else {
				currentList = sortedAdd(currentList, nextStr, indexLow, ((indexLow+indexMid)/2), indexMid);
			}
		}
		else if (nextStr.compareTo(currentList.get(indexMid)) > 0 && nextStr.compareTo(currentList.get(indexHigh)) < 0) {
			indexMid++;
			indexHigh--;
			if (indexMid > indexHigh) {
				currentList.add(indexMid, nextStr);
			}
			else {
				currentList = sortedAdd(currentList, nextStr, indexMid, ((indexMid+indexHigh)/2), indexHigh);
			}
		}
		return currentList;
	}

	/**
	 * If the prefix is in the lexicon, returns true.
	 * 
	 * @param s
	 *          The word to search for.
	 * @return True if the lexicon contains s.
	 */
	public boolean containsPrefix(String s) {
		/*if (!LexiconArrayList.isEmpty() && s.length() >= 3) {
			return containsPrefixHelper(s, 1, LexiconArrayList.size()/2, LexiconArrayList.size()-2);
		}
		return false;*/
		
		/*binary search then check the next largest one to see if it contains prefix, otherwise return false
		 * 
		 */
		containsPrefixHelper(s, 0, LexiconArrayList.size()-1);
	}
	
	private boolean containsPrefixHelper(String s, int indexLow, int indexHigh){
		int indexMid = (indexLow + indexHigh)/2;
		if(indexLow == indexHigh - 1){
			return LexiconArrayList.get(indexHigh).contains(s);
		}else if(s.compareTo(LexiconArrayList.get(indexLow)) < 0 || s.compareTo(LexiconArrayList.get(indexHigh))>0){
			return false;
		}else if(s.compareTo(LexiconArrayList.get(indexMid))> 0 && s.compareTo(LexiconArrayList.get(indexHigh)) < 0){
			return containsPrefixHelper(s, indexMid, indexHigh);
		}else if(s.compareTo(LexiconArrayList.get(indexMid))< 0 && s.compareTo(LexiconArrayList.get(indexLow)) > 0){
			return containsPrefixHelper(s, indexLow, indexMid);
		}else if(s.compareTo(LexiconArrayList.get(indexMid))== 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Recursive helper method which executes a modified binary search in order to see if the potential prefix is within the lexicon.
	 * In order to qualify as an actual prefix, this potential prefix must be a prefix to at least two words already in the lexicon.
	 * 
	 * @param s
	 *          The potential prefix to search for.
	 *          
	 * @param indexLow
	 * 			The lowerbound of strings to look through in the lexicon
	 * 
	 * @param indexMid
	 * 			The middle index of strings to look through in the lexicon
	 * 
	 * @param indexHigh
	 * 			The upperbound of strings to look through in the lexicon
	 * 
	 * @return True if the lexicon contains s.
	 */
	/*private boolean containsPrefixHelper(String s, int indexLow, int indexMid, int indexHigh) {
		String toCheckLowL = LexiconArrayList.get(indexLow-1);
		String toCheckLow = LexiconArrayList.get(indexLow);
		String toCheckLowH= LexiconArrayList.get(indexLow+1);
		String toCheckMidL = LexiconArrayList.get(indexMid-1);
		String toCheckMid = LexiconArrayList.get(indexMid);
		String toCheckMidH = LexiconArrayList.get(indexMid+1);
		String toCheckHighL = LexiconArrayList.get(indexHigh-1);
		String toCheckHigh = LexiconArrayList.get(indexHigh);
		String toCheckHighH = LexiconArrayList.get(indexHigh+1);
		if ((s.compareTo(toCheckLowL) < 0) || (s.compareTo(toCheckHighH)) > 0) {
			return false;
		}
		else if (((toCheckLowL.startsWith(s)) && (toCheckLow.startsWith(s)))
				|| ((toCheckLow.startsWith(s)) && (toCheckLowH.startsWith(s)))
				|| ((toCheckMidL.startsWith(s)) && (toCheckMid.startsWith(s)))
				|| ((toCheckMid.startsWith(s)) && (toCheckMidH.startsWith(s)))
				|| ((toCheckHighL.startsWith(s)) && (toCheckHigh.startsWith(s)))
				|| ((toCheckHigh.startsWith(s)) && (toCheckHighH.startsWith(s))))
		{
			return true;
		}
		else if ((s.compareTo(toCheckLow) > 0) && (s.compareTo(toCheckMid) < 0)) {
			indexLow++;
			indexMid--;
			return containsPrefixHelper(s, indexLow, ((indexLow+indexMid)/2), indexMid);
		}
		else if ((s.compareTo(toCheckMid) > 0) && (s.compareTo(toCheckHigh) < 0)) {
			indexMid++;
			indexHigh--;
			return containsPrefixHelper(s, indexMid, ((indexMid+indexHigh)/2), indexHigh);
		}
		return false;
	}*/

	/**
	 * If the word is in the lexicon, returns true.
	 * 
	 * @param s
	 *          The word to search for.
	 * @return True if the lexicon contains s.
	 */
	public boolean contains(String s) {
		if (!LexiconArrayList.isEmpty()) {//note: took out size requirement
			return containsHelper(s, 0, LexiconArrayList.size()/2, LexiconArrayList.size()-1);
		}
		return false;
	}

	/**
	 * Recursive helper method which executes a modified binary search in order to see if the word is within the lexicon.
	 * 
	 * @param s
	 *          The word to search for.
	 *          
	 * @param indexLow
	 * 			The lowerbound of strings to look through in the lexicon
	 * 
	 * @param indexMid
	 * 			The middle index of strings to look through in the lexicon
	 * 
	 * @param indexHigh
	 * 			The upperbound of strings to look through in the lexicon
	 * 
	 * @return True if the lexicon contains s.
	 */
	private boolean containsHelper (String s, int indexLow, int indexMid, int indexHigh) {
		if ((s.compareTo(LexiconArrayList.get(indexLow)) < 0) || (s.compareTo(LexiconArrayList.get(indexHigh)) > 0)) { //If the word cannot be found after running contains, it will be tested next as potentially a prefix
			return containsPrefix(s);
		}
		else if ((s.compareTo(LexiconArrayList.get(indexLow)) == 0) || (s.compareTo(LexiconArrayList.get(indexMid)) == 0) || 
				(s.compareTo(LexiconArrayList.get(indexHigh)) == 0)) {
			return true;
		}
		else if ((s.compareTo(LexiconArrayList.get(indexLow)) > 0) && (s.compareTo(LexiconArrayList.get(indexMid)) < 0)) {
			indexLow++;
			indexMid--;
			return containsHelper(s, indexLow, ((indexLow+indexMid)/2), indexMid);
		}
		else if ((s.compareTo(LexiconArrayList.get(indexMid)) > 0) && (s.compareTo(LexiconArrayList.get(indexHigh)) < 0)) {
			indexMid++;
			indexHigh--;
			return containsHelper(s, indexMid, ((indexMid+indexHigh)/2), indexHigh);
		}
		return false;
	}

	/**
	 * Method for getting the size of the lexicon.
	 */ 
	public int size() {
		return LexiconArrayList.size();
	}
	
	/**
	 * Method for finding an element inside the lexicon.
	 */
	public String get(int n) {
		return LexiconArrayList.get(n);
	}
}