/*
*a. Assignment # : MidTerm
*
*b. File Name : DeleteAllFavorites.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.rottentomatoes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

public class DeleteAllFavorites extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost request = new HttpPost(params[0]);
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("uid",Config.getUid()));
	    try {
	    	UrlEncodedFormEntity formParams = new UrlEncodedFormEntity(nameValuePairs);
	    	request.setEntity(formParams);
	    	HttpResponse response = httpclient.execute(request);
	    	if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	    		
				InputStream in = response.getEntity().getContent();
				String status = MovieXMLPullParser.status(in);
				return status;
				
	    	}
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	

}
