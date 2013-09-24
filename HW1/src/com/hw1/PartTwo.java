/*
* 
*a.Assignment # : Home Work 1
*b.File Name. : PartTwo.java
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
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class PartTwo {

	public static void main(String[] args) {
			// Include implementation for Part 2, and create all the required classes.
			
			readFiles("src/userList1.txt","src/userList2.txt");
		    
		}


		public static void readFiles(String path1, String path2) {
			BufferedReader reader1 = null;
			BufferedReader reader2 = null;
			Set<String> set1 = new HashSet<String>();
		    Set<String> set2 = new HashSet<String>();
			//Integer count = null;
			 
			try {
				
				reader1 = new BufferedReader(new FileReader(path1));
				reader2 = new BufferedReader(new FileReader(path2));
		
				String line1 = null;
				String line2 = null;
				while ((line1 = reader1.readLine()) != null) {
						set1.add(line1);
			           }
				while ((line2 = reader2.readLine()) != null) {
						set2.add(line2);
		           		}
				set1.retainAll(set2);
				
				ArrayList<String> setList=new ArrayList<String>(set1);
				Collections.sort(setList, new Comparator<String>() {

					@Override
					public int compare(String comp1, String comp2) {
						String[] splitEntry1 = comp1.split(",");
						String[] splitEntry2 = comp2.split(",");
						return splitEntry1[3].compareTo(splitEntry2[3]);
					}
				});
				
				System.out.println("Number of Duplicates Found:" +setList.size());
				Iterator<String> iter = setList.iterator();
				while (iter.hasNext()) {
					System.out.println(iter.next());
				}
				
		} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader1 != null)reader1.close();
					if (reader2 != null)reader2.close();
				} catch (IOException excep) {
					excep.printStackTrace();
				}
			}
		}
	}
