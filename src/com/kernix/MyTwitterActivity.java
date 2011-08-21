package com.kernix;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kernix.R;

public class MyTwitterActivity extends ListActivity {
	
	private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	private String idCompte;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idCompte = getIntent().getExtras().getString("idCompte");
        if(idCompte != null){
        	new MyTask().execute(); 
        }
    }
    
	private class TweetListAdaptor extends ArrayAdapter<Tweet> {  
	    private ArrayList<Tweet> tweets;  
	    public TweetListAdaptor(Context context,  
	                                int textViewResourceId,  
	                                ArrayList<Tweet> items) {  
	             super(context, textViewResourceId, items);  
	             this.tweets = items;  
	    }  
	    @Override  
	    public View getView(int position, View convertView, ViewGroup parent) {  
            View v = convertView;  
            if (v == null) {  
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
                v = vi.inflate(R.layout.list_items, null);  
            }  
            Tweet o = tweets.get(position);  
            TextView tt = (TextView) v.findViewById(R.id.toptext);  
            tt.setText(o.content);  
            return v;  
	    }  
	} 
	
	private class MyTask extends AsyncTask<Void, Void, Void> {  
        private ProgressDialog progressDialog;  
        protected void onPreExecute() {  
                progressDialog = ProgressDialog.show(MyTwitterActivity.this,  
                                  "", "Loading. Please wait...", true);  
        }  
        @Override  
        protected Void doInBackground(Void... arg0) {  
            try {  
                HttpClient hc = new DefaultHttpClient();  
                HttpGet get = new  
                HttpGet("http://search.twitter.com/search.json?callback=?&rpp=5&q=from:"+idCompte);
                HttpResponse rp = hc.execute(get);  
                if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
                {  
                    String result = EntityUtils.toString(rp.getEntity());  
                    JSONObject root = new JSONObject(result);  
                    JSONArray sessions = root.getJSONArray("results");  
                    for (int i = 0; i < sessions.length(); i++) {  
                            JSONObject session = sessions.getJSONObject(i);  
                    Tweet tweet = new Tweet();  
                             tweet.content = session.getString("text");  
                             tweets.add(tweet);  
                    }  
               }  
           } catch (Exception e) {  
                   Log.e("TwitterFeedActivity", "Error loading JSON", e);  
           }  
           return null;  
        }
        
	  @Override  
	  protected void onPostExecute(Void result) {  
	          progressDialog.dismiss();  
	          setListAdapter(new TweetListAdaptor(  
	                          MyTwitterActivity.this, R.layout.list_items, tweets));  
	   }  
	}
}