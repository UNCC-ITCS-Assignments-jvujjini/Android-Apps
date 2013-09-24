/*
* 
*a.Assignment # : Home Work 1
*b.File Name. : Country.java
*c.Full name of all students in your group : Jagan Mohan Rao Vujjini
*
*/



package com.hw1;

public class Country {
	private String country,continent;
	 public Country(String line){
	  parseCountry(line);
	 }
	 
	 //getter methods for instance variables 
	 public String getcountry()
	 {
		return this.country; 
	 }
	 
	 public String getcontinent()
	 {
	 return this.continent;
	 }
	 
	 //code to parse the details with delimiter ,
	 private void parseCountry(String line){
		  //should parse the user by splitting the line string (comma separated)
		  String[] countryDetails=line.split(",");
		  this.continent=countryDetails[0].trim();
		  this.country=countryDetails[1].trim();
		 
		 }
}
