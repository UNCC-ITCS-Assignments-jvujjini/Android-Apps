/*
*a. Assignment # : In Class 8
*
*b. File Name : PreviewActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass8;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PreviewActivity extends Activity {
	
	DataManager dm;
	private String link;
	News n;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey(NewsActivity.SEND_LINK)) {
				link = (String) getIntent().getExtras().get((NewsActivity.SEND_LINK));
				WebView web=(WebView)findViewById(R.id.webView1);
				web.getSettings().setJavaScriptEnabled(true);
				
				web.setWebViewClient(new WebViewClient(){
					
				 public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		                Toast.makeText(PreviewActivity.this, description, Toast.LENGTH_SHORT).show();
		            }
					
				});

				web.loadUrl(link);
			
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//return super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {
        case R.id.item1:
            dm = new DataManager(this);
            n = new News();
            n.setTitle(link);
            n.setUrl(link);
            dm.saveNote(n);
            return true;
        default:
            return super.onOptionsItemSelected(item);
	}
		
	}

	
	
}