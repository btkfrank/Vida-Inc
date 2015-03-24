//package com.gimbal.sample;
//
//import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Observable;
//import java.util.Observer;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.StatusLine;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.*;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.LinearLayout;
//import android.widget.SeekBar;
//import android.widget.SeekBar.OnSeekBarChangeListener;
//import android.widget.Switch;
//import android.widget.TextView;
//
//
//class FetchTask extends AsyncTask<String, String, String>{	
//
//    @Override
//    protected String doInBackground(String... uri) {
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpResponse response;
//        String responseString = null;
//        try {
//            response = httpclient.execute(new HttpGet(uri[0]));
//            StatusLine statusLine = response.getStatusLine();
//            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                response.getEntity().writeTo(out);
//                out.close();
//                responseString = out.toString();
////                System.out.println(responseString);
//            } else{
//                //Closes the connection.
//                response.getEntity().getContent().close();
//                throw new IOException(statusLine.getReasonPhrase());
//            }
//        } catch (ClientProtocolException e) {
//            //TODO Handle problems..
//        } catch (IOException e) {
//            //TODO Handle problems..
//        }
//        return responseString;
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        //Do anything with response..
//        System.out.println(result);
//    }
//}
//
////class GetTask{
////	public static String connect(String url)
////	{
////
////	    HttpClient httpclient = new DefaultHttpClient();
////
////	    // Prepare a request object
////	    HttpGet httpget = new HttpGet(url); 
////
////	    // Execute the request
////	    HttpResponse response;
////	    try {
////	        response = httpclient.execute(httpget);
////	        // Examine the response status
////	        Log.i("Praeda",response.getStatusLine().toString());
////
////	        // Get hold of the response entity
////	        HttpEntity entity = response.getEntity();
////	        // If the response does not enclose an entity, there is no need
////	        // to worry about connection release
////
////	        if (entity != null) {
////
////	            // A Simple JSON Response Read
////	            InputStream instream = entity.getContent();
////	            String result= convertStreamToString(instream);	            	            
////	            // now you have the string representation of the HTML request
////	            instream.close();
////	            return result;
////	        }
////	        return "fail";
////
////	    } catch (Exception e) {
////	    	return "fail";
////	    }
////	}
////
////	    private static String convertStreamToString(InputStream is) {
////	    /*
////	     * To convert the InputStream to String we use the BufferedReader.readLine()
////	     * method. We iterate until the BufferedReader return null which means
////	     * there's no more data to read. Each line will appended to a StringBuilder
////	     * and returned as String.
////	     */
////	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
////	    StringBuilder sb = new StringBuilder();
////
////	    String line = null;
////	    try {
////	        while ((line = reader.readLine()) != null) {
////	            sb.append(line + "\n");
////	        }
////	    } catch (IOException e) {
////	        e.printStackTrace();
////	    } finally {
////	        try {
////	            is.close();
////	        } catch (IOException e) {
////	            e.printStackTrace();
////	        }
////	    }
////	    return sb.toString();
////	}
////}
//
//class PostTask extends AsyncTask<String, String, String>{
//	
//	private JSONObject jsonObject;
//	
//    @Override
//    protected String doInBackground(String... uri) {
//    	HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(uri[0]);
//        HttpResponse response = null;
//        String json = "";
//        try {
//            // Add your data
//        	json = jsonObject.toString();
//        	StringEntity se = new StringEntity(json);
//        	
//            httppost.setEntity(se);
//            httppost.setHeader("Accept", "application/json");
//            httppost.setHeader("Content-type", "application/json");
//            // Execute HTTP Post Request
//            response = httpclient.execute(httppost);
//            
//            System.out.println(inputStreamToString(response.getEntity().getContent()));
//            
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//        }
//        if(response != null)
//        	return response.toString();
//        else
//        	return "error:[" + json + "]";
//    }
//    
//    public void generateJsonObj(JSONObject jo){
//    	this.jsonObject = jo;
//    	
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        //Do anything with response..
//        System.out.println(result);
//    }
//    
// // Fast Implementation
//    private StringBuilder inputStreamToString(InputStream is) {
//        String line = "";
//        StringBuilder total = new StringBuilder();
//        
//        // Wrap a BufferedReader around the InputStream
//        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//
//        // Read response until the end
//        try {
//			while ((line = rd.readLine()) != null) { 
//			    total.append(line); 
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//        // Return full string
//        return total;
//    }
//}
//
//
//public class View1 extends LinearLayout implements Observer {
//	
//	public static final String IP = "http://192.168.43.79:8000/api/";
//	
//	private Model model;
//	private Button button;
//	private Button getLightStatusButton;
//	private Button getDoorStatusButton;
//	private Button getTempStatusButton;
//	private Button colorButton;
//	private Switch light;
//	private Switch lock;
//	private SeekBar temperature;
//	private ColorPickerDialog colorPicker;
//
//	public View1(Context context, Model m) {
//		super(context);
//		
//	    Log.d("DEMO", "View1 constructor");
//		
//		// get the xml description of the view and "inflate" it
//		// into the display (kind of like rendering it)
//		View.inflate(context, R.layout.view1, this);
//
//		// save the model reference
//		model = m;
//		// add this view to model's list of observers
//		model.addObserver(this);
//
//		// get a reference to widgets to manipulate on update
//		getLightStatusButton = (Button) findViewById(R.id.LightGetstatus);
//		getDoorStatusButton = (Button) findViewById(R.id.DoorGetStatus);
//		getTempStatusButton = (Button) findViewById(R.id.TemperatureGetStatus);
//		colorButton = (Button) findViewById(R.id.color);
//		light = (Switch) findViewById(R.id.light);
//		lock = (Switch) findViewById(R.id.lock);		
//		temperature = (SeekBar) findViewById(R.id.temp);
//		temperature.setMax(15);		
//		
//		final TextView t1 = (TextView) findViewById(R.id.textView3); 
//
//		//		 create a controller for the button
//		getLightStatusButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				 										
//	        	System.out.println(GetTask.connect(IP + "light/1"));	
//				
//				
//			}
//		});
//		
//		getDoorStatusButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				 										
//	        	System.out.println(GetTask.connect(IP + "door/1"));	
//				
//				
//			}
//		});
//		
//		getTempStatusButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				 										
//	        	System.out.println(GetTask.connect( IP + "ac/1"));	
//				
//				
//			}
//		});
//		
//		light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		        if (isChecked) {
//		            // The toggle is enabled
//		        	PostTask pt = new PostTask();
//		        	
//		        	JSONObject jsonObject = new JSONObject();
//		        	
//		            try {
//						jsonObject.accumulate("ON", true);					
////						jsonObject.accumulate("color", "#ffffff");
//						
//		            } catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		            
//		        	
//					pt.generateJsonObj(jsonObject); 										
//					pt.execute(IP + "light/1");		        	
//		        } else {
//		        	PostTask pt = new PostTask();
//		        	
//		        	JSONObject jsonObject = new JSONObject();
//		        	
//		            try {
//						jsonObject.accumulate("ON", false);					
////						jsonObject.accumulate("color", "#ffffff");
//						
//		            } catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		            
//		        	
//					pt.generateJsonObj(jsonObject); 										
//					pt.execute( IP + "light/1");		   
//		        }
//		    }
//		});
//				
//		lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		        if (isChecked) {
//		        	// The toggle is enabled
//		        	PostTask pt = new PostTask();
//		        	
//		        	JSONObject jsonObject = new JSONObject();
//		        	
//		            try {
//						jsonObject.accumulate("ON", true);											
//						
//		            } catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}		            
//		        	
//					pt.generateJsonObj(jsonObject); 										
//					pt.execute( IP + "/door/1");			        	
//		        } else {
//		        	PostTask pt = new PostTask();
//		        	
//		        	JSONObject jsonObject = new JSONObject();
//		        	
//		            try {
//						jsonObject.accumulate("ON", false);					
//						
//						
//		            } catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		            
//		        	
//					pt.generateJsonObj(jsonObject); 										
//					pt.execute(IP + "door/1");		   
//		        }
//		    }
//		});
//		
//		temperature.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {       
//
//		    @Override       
//		    public void onStopTrackingTouch(SeekBar seekBar) {      
//		        // TODO Auto-generated method stub		    	
//				String value = t1.getText().toString();				 
//
//				// The toggle is enabled
//	        	PostTask pt = new PostTask();
//	        	
//	        	JSONObject jsonObject = new JSONObject();
//	        	
//	            try {
//					jsonObject.accumulate("ON", true);					
//					jsonObject.accumulate("Temperature", value);
//					
//	            } catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	            
//	        	
//				pt.generateJsonObj(jsonObject); 										
//				pt.execute( IP + "ac/1");
//				
//		    }       
//
//		    @Override       
//		    public void onStartTrackingTouch(SeekBar seekBar) {     
//		        // TODO Auto-generated method stub		    	
//		    	
//		    }       
//
//		    @Override       
//		    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { 
//	    	    // TODO Auto-generated method stub 
//		    	t1.setText(String.valueOf(progress+15)); 
//		    } 
//		});
//					
//	}
//
//	// the model call this to update the view
//	@Override
//	public void update(Observable observable, Object data) {
//	    Log.d("DEMO", "update View1");
//
//		// update button text with click count
//	    // (convert to string, or else Android uses int as resource id!)
//		button.setText(String.valueOf(model.getCounterValue()));
//	}
//}
