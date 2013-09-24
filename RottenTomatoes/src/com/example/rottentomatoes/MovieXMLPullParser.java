/*
*a. Assignment # : MidTerm
*
*b. File Name : MovieXMLPullParser.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.rottentomatoes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class MovieXMLPullParser {
	
	static boolean counter;
	static String isFavorite;

	public static ArrayList<String> parseIds(InputStream in) throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		
		counter = true;
		
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		ArrayList<String> ids = new ArrayList<String>();
		parser.setInput(in, "UTF-8");
		
		int event = parser.getEventType();			
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_TAG:	
					if(parser.getName().equals("favorites")){
						counter = false;
					} else if(parser.getName().equals("id") && !counter){
						ids.add(parser.nextText().trim());
					}
				break;
			default:
				break;
			}
			event = parser.next();
		}
		return ids;
	}

	public static String status(InputStream in) throws XmlPullParserException, IOException {
		
		counter = true;
		
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		String status = null;
		parser.setInput(in, "UTF-8");
		
		int event = parser.getEventType();			
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_TAG:	
					if(parser.getName().equals("error")){
						counter = false;
					} else if(parser.getName().equals("id") && !counter){
						if (parser.nextText().trim().equals("0")) {
							status = "true";
						} else {
							status = "false";
						}
					}
				break;
			default:
				break;
			}
			event = parser.next();
		}
		return status;
		
	}
	
	public static String isFavorite(InputStream in) throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		
		isFavorite = "false";
		
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(in, "UTF-8");
		
		int event = parser.getEventType();			
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_TAG:
					if(parser.getName().equalsIgnoreCase("isFavorite")){
						isFavorite = parser.nextText().trim();
					}
				break;
			default:
				break;
			}
			event = parser.next();
		}
		return isFavorite;
	}
}
