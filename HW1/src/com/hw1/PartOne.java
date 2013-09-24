/*
* 
*a.Assignment # : Home Work 1
*b.File Name. : PartOne.java
*c.Full name of all students in your group : Jagan Mohan Rao Vujjini
*
*/


package com.hw1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PartOne implements Comparator<Map.Entry<String, Integer>> {
	public static void main(String[] args) {
		
		// Include implementation for Part 1, and create all the required classes.
		
		readFile("src/words.txt");
	    
	}


	public static void readFile(String path) {
		BufferedReader reader = null;
		HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
		Integer count = null;
		 
		try {
	
			String line;
	
			reader = new BufferedReader(new FileReader(path));
	
			while ((line = reader.readLine()) != null) {
				String key = line;
				if ((count = hashmap.get(key)) != null) {
				    //if map contains word
				    hashmap.put(key, count + 1);
				} else {
				    //add new word to Map
				    hashmap.put(key, 1);
				}
			}
			sortMap(hashmap);
			//System.out.println(hashmap);
	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)reader.close();
			} catch (IOException excep) {
				excep.printStackTrace();
			}
		}
	}

	public static void sortMap(HashMap<String, Integer> hash) {
		
		ArrayList<Entry<String, Integer>> sortList = new ArrayList<Entry<String, Integer>>(hash.entrySet());
		Collections.sort(sortList, new PartOne());
		Iterator<Entry<String, Integer>> iter = sortList.iterator();
		// Displaying the top 10 from the HashMap
		for (int j = 0; j < 10 && iter.hasNext(); j++) {
			Map.Entry<String, Integer> topList = (Map.Entry<String, Integer>) iter.next();
			System.out.println(topList);
		}
		
		//System.out.println(hash);
		
	}


	public int compare(Entry<String, Integer> comp1, Entry<String, Integer> comp2) {
		// TODO Auto-generated method stub
		return comp2.getValue().compareTo(comp1.getValue());// for descending order
	}
	
}