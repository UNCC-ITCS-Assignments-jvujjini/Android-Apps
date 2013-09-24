/*
* 
*a.Assignment # : Home Work 1
*b.File Name. : PartThree.java
*c.Full name of all students in your group : Jagan Mohan Rao Vujjini
*
*/


package com.hw1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PartThree {
	public static void main(String[] args) {
		// Include implementation for Part 3, and create all the required classes.
		
		Collection<String> listCountry;
		HashMap<String,Collection<String>> hashmap=new HashMap<String,Collection<String>>();
			
		Iterator<String> countries = readFile("src/countries-info.txt").iterator();
		 Country country;
		  while(countries.hasNext()){
			 country = new Country(countries.next());
			 listCountry = hashmap.get(country.getcontinent());
			 if(listCountry == null)
			 {
				 listCountry =new ArrayList<String>();
				 hashmap.put(country.getcontinent(), listCountry);
			 }
			 listCountry.add(country.getcountry());
					 
		  }
		  Iterator<Map.Entry<String, Collection<String>>> countryiter = hashmap.entrySet().iterator();
		  
		  ArrayList<String> sortedList;
		  while(countryiter.hasNext()){
			    String key = countryiter.next().getKey();
			    sortedList =(ArrayList<String>)hashmap.get(key);
			    Collections.sort(sortedList);
			    System.out.println(key+":\n"+sortedList);
			}
	}
	
	public static ArrayList<String> readFile(String path) {
		// Lets make sure the file path is not empty or null
		if (path.isEmpty() || path == null) {
			System.out.println("Invalid File Path");
			return null;
		}
		String filePath = System.getProperty("user.dir") + "/" + path;
		BufferedReader inputStream = null;
		// Exception Handling
		try {
			try {
				inputStream = new BufferedReader(new FileReader(filePath));
				String lineContent = null;
				ArrayList<String> words = new ArrayList<String>();
				// Loop will iterate over each line within the file.
				// It will stop when no new lines are found.
				while ((lineContent = inputStream.readLine()) != null) {
					words.add(lineContent);
				}
				return words;
			} finally {
				if (inputStream != null)
				inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				}
			return null;
			}
}