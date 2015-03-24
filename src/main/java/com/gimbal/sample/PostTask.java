package com.gimbal.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public class PostTask extends AsyncTask<String, String, String>{
	
	private JSONObject jsonObject;
	
    @Override
    protected String doInBackground(String... uri) {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(uri[0]);
        HttpResponse response = null;
        String json = "";
        try {
            // Add your data
        	json = jsonObject.toString();
        	StringEntity se = new StringEntity(json);
        	
            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            // Execute HTTP Post Request
            response = httpclient.execute(httppost);
            
            System.out.println(inputStreamToString(response.getEntity().getContent()));
            
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        if(response != null)
        	return response.toString();
        else
        	return "error:[" + json + "]";
    }
    
    public void generateJsonObj(JSONObject jo){
    	this.jsonObject = jo;
    	
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
        System.out.println(result);
    }
    
 // Fast Implementation
    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        try {
			while ((line = rd.readLine()) != null) { 
			    total.append(line); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Return full string
        return total;
    }
}

