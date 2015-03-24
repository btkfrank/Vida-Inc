/**
 * Copyright (C) 2013 Gimbal, Inc. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Gimbal, Inc.
 * 
 * The following sample code illustrates various aspects of the Gimbal Android SDK.
 * 
 * The sample code herein is provided for your convenience, and has not been tested or designed to
 * work on any
 * particular system configuration. It is provided pursuant to the License Agreement for Gimbal
 * Manager AS IS, and your
 * use of this sample code, whether as provided or with any modification, is at your own risk.
 * Neither Qualcomm Retail
 * Solutions, Inc. nor any affiliate takes any liability nor responsibility with respect to the
 * sample code, and
 * disclaims all warranties, express and implied, including without limitation warranties on
 * merchantability, fitness
 * for a specified purpose, and against infringement.
 */
package com.gimbal.sample;

import org.json.JSONException;
import org.json.JSONObject;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

//import com.example.mvc.Model;
import com.gimbal.logging.GimbalLogConfig;
import com.gimbal.logging.GimbalLogLevel;
import com.gimbal.proximity.Proximity;
import com.gimbal.proximity.ProximityError;
import com.gimbal.proximity.ProximityListener;

public class ProximityActivity extends Activity implements ProximityListener {

//    private static final String PROXIMITY_APP_ID = "274f18f1016e681194f92c796428836aee8e4a5ae2925cca796393290d0c01d1";
//    private static final String PROXIMITY_APP_SECRET = "1c3104e7c1c67ea8906cdf3ad9f57a0d82eeb4107472d80906a5a1174e2879b4";
	
	public static final String IP = "http://192.168.43.79:8000/api/";
	
	private static final String PROXIMITY_APP_ID = "399df86e9cb9f88d9ae92329958c49065d762aa3bea2b009f4c19ca2ea6f56a4";
    private static final String PROXIMITY_APP_SECRET = "b13f6eec245ce28b0de9b43c9c64d59a0dab300125207693b1fb7103fb12a6f4";

    private static final String PROXIMITY_SERVICE_ENABLED_KEY = "proximity.service.enabled";

    private static final String TAG = ProximityActivity.class.getSimpleName();

    private final static int REQUEST_ENABLE_BT = 1;
    private Switch enableProximitySwitch;
    
    
    private Model model;
	private Button button;
	private Button getLightStatusButton;
	private Button getDoorStatusButton;
	private Button getTempStatusButton;
	private Button colorButton;
	private Switch light;
	private Switch lock;
	private SeekBar temperature;
	private ColorPickerDialog colorPicker;
	
	private VisitManagerHandler manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "CODENAME: " + Build.VERSION.CODENAME);
        Log.d(TAG, "INCREMENTAL: " + Build.VERSION.INCREMENTAL);
        Log.d(TAG, "RELEASE: " + Build.VERSION.RELEASE);
        Log.d(TAG, "SDK_INT: " + Build.VERSION.SDK_INT);

        View view = View.inflate(this, R.layout.activity_proximity, null);
        
        setContentView(view);

        initializeProximity();
        
        getLightStatusButton = (Button) findViewById(R.id.LightGetstatus);
		getDoorStatusButton = (Button) findViewById(R.id.DoorGetStatus);
		getTempStatusButton = (Button) findViewById(R.id.TemperatureGetStatus);
		colorButton = (Button) findViewById(R.id.color);
		light = (Switch) findViewById(R.id.light);
		lock = (Switch) findViewById(R.id.lock);		
		temperature = (SeekBar) findViewById(R.id.temp);
		temperature.setMax(15);		
		
		final TextView t1 = (TextView) findViewById(R.id.textView3);        
        
        String proximityServiceEnabled = getUserPreference(PROXIMITY_SERVICE_ENABLED_KEY);
//        if (proximityServiceEnabled != null && Boolean.valueOf(proximityServiceEnabled)) {
//            startProximityService();
//        }

        enableProximitySwitch = (Switch) view.findViewById(R.id.enableProximitySwitch);
        enableProximitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveUserPreferrence(PROXIMITY_SERVICE_ENABLED_KEY, String.valueOf(true));
                    startProximityService();
                }
            }
        });
        
		//		 create a controller for the button
		getLightStatusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 										
	        	System.out.println(GetTask.connect(IP + "light/1"));	
				
				
			}
		});
		
		getDoorStatusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 										
	        	System.out.println(GetTask.connect(IP + "door/1"));	
				
				
			}
		});
		
		getTempStatusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 										
	        	System.out.println(GetTask.connect( IP + "ac/1"));	
				
				
			}
		});
		
		light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		            // The toggle is enabled
		        	PostTask pt = new PostTask();
		        	
		        	JSONObject jsonObject = new JSONObject();
		        	
		            try {
						jsonObject.accumulate("ON", true);					
//						jsonObject.accumulate("color", "#ffffff");
						
		            } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		        	
					pt.generateJsonObj(jsonObject); 										
					pt.execute(IP + "light/1");		        	
		        } else {
		        	PostTask pt = new PostTask();
		        	
		        	JSONObject jsonObject = new JSONObject();
		        	
		            try {
						jsonObject.accumulate("ON", false);					
//						jsonObject.accumulate("color", "#ffffff");
						
		            } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		        	
					pt.generateJsonObj(jsonObject); 										
					pt.execute( IP + "light/1");		   
		        }
		    }
		});
				
		lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	// The toggle is enabled
		        	PostTask pt = new PostTask();
		        	
		        	JSONObject jsonObject = new JSONObject();
		        	
		            try {
						jsonObject.accumulate("ON", true);											
						
		            } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		            
		        	
					pt.generateJsonObj(jsonObject); 										
					pt.execute( IP + "/door/1");			        	
		        } else {
		        	PostTask pt = new PostTask();
		        	
		        	JSONObject jsonObject = new JSONObject();
		        	
		            try {
						jsonObject.accumulate("ON", false);					
						
						
		            } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		        	
					pt.generateJsonObj(jsonObject); 										
					pt.execute(IP + "door/1");		   
		        }
		    }
		});
		
		temperature.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {       

		    @Override       
		    public void onStopTrackingTouch(SeekBar seekBar) {      
		        // TODO Auto-generated method stub		    	
				String value = t1.getText().toString();				 

				// The toggle is enabled
	        	PostTask pt = new PostTask();
	        	
	        	JSONObject jsonObject = new JSONObject();
	        	
	            try {
					jsonObject.accumulate("ON", true);					
					jsonObject.accumulate("Temperature", value);
					
	            } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	            
	        	
				pt.generateJsonObj(jsonObject); 										
				pt.execute( IP + "ac/1");
				
		    }       

		    @Override       
		    public void onStartTrackingTouch(SeekBar seekBar) {     
		        // TODO Auto-generated method stub		    	
		    	
		    }       

		    @Override       
		    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { 
	    	    // TODO Auto-generated method stub 
		    	t1.setText(String.valueOf(progress+15)); 
		    } 
		});
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "received callback for activity result with request code " + requestCode
                + " , result code " + resultCode + " , data " + data);

        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "enabled BT. Start oauth session.");
            saveUserPreferrence(PROXIMITY_SERVICE_ENABLED_KEY, String.valueOf(true));
            startProximityService();
        }
        else if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            enableProximitySwitch.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    private void initializeProximity() {
        Log.d(TAG, "initializeProximity");

        GimbalLogConfig.setLogLevel(GimbalLogLevel.INFO);
        GimbalLogConfig.enableFileLogging(this.getApplicationContext());

        Proximity.initialize(this, PROXIMITY_APP_ID, PROXIMITY_APP_SECRET);
        Proximity.optimizeWithApplicationLifecycle(getApplication());
    }

    private void startProximityService() {
        Log.d(ProximityActivity.class.getSimpleName(), "startSession");
        Proximity.startService(this);
    }

    @Override
    public void serviceStarted() {
        Log.d(ProximityActivity.class.getSimpleName(), "serviceStarted");
        if (manager == null) {
            manager = new VisitManagerHandler();
//            manager.init(this);
//            adapter = new TransmitterListAdapter(this, this, manager);
//            list.setAdapter(adapter);
            manager.startScanning();
        }
//        showTransmitters();
    }

    @Override
    public void startServiceFailed(int errorCode, String message) {
        showToastMessage("Service Failed");
        Log.e("ProximityActivity", "serviceFailed because of " + message);
        if (errorCode == ProximityError.PROXIMITY_BLUETOOTH_IS_OFF.getCode()) {
            turnONBluetooth();
        }
    }

    private void turnONBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    private void saveUserPreferrence(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String getUserPreference(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    private void showTransmitters() {
        Log.d(TAG, "session started callback" + ProximityTransmittersActivity.class);

        Intent intent = new Intent(
                "com.example.sampleproximityusingapplication.ProximityTransmittersActivity");
        startActivity(intent);
        finish();
    }

    void showAddTransmitters() {
        Log.d(ProximityActivity.class.getSimpleName(), "session started callback"
                + ProximityTransmittersActivity.class);

        Intent intent = new Intent(
                "com.example.sampleproximityusingapplication.ProximityTransmittersActivity");
        startActivity(intent);
        finish();
    }

    private void showToastMessage(final String message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
