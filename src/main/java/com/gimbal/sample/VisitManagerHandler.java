/**
 * Copyright (C) 2013 Gimbal, Inc. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Qualcomm Retail Solutions, Inc.
 * 
 * The following sample code illustrates various aspects of the Gimbal Android SDK.
 * 
 * The sample code herein is provided for your convenience, and has not been tested or designed to work on any
 * particular system configuration. It is provided pursuant to the License Agreement for Gimbal Manager AS IS, and your
 * use of this sample code, whether as provided or with any modification, is at your own risk. Neither Gimbal, Inc. nor
 * any affiliate takes any liability nor responsibility with respect to the sample code, and disclaims all warranties,
 * express and implied, including without limitation warranties on merchantability, fitness for a specified purpose, and
 * against infringement.
 */
package com.gimbal.sample;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

//import com.example.mvc.PostTask;
//import com.example.mvc.TransmitterAttributes;
import com.gimbal.proximity.ProximityFactory;
import com.gimbal.proximity.ProximityOptions;
import com.gimbal.proximity.Visit;
import com.gimbal.proximity.VisitListener;
import com.gimbal.proximity.VisitManager;

public class VisitManagerHandler implements VisitListener {

    private static final String TAG = "VisitManagerHandler";
    
    public static final String IP = "http://172.20.10.7:8000/api/";

    private final LinkedHashMap<String, TransmitterAttributes> transmitters = new LinkedHashMap<String, TransmitterAttributes>();
    private final VisitManager visitManager = ProximityFactory.getInstance().createVisitManager();
    private ProximityTransmittersActivity activity;

    public void init(ProximityTransmittersActivity activity) {
        this.activity = activity;
    }

    public void stopScanning() {
        visitManager.stop();
    }

    public void startScanning() {
        startScanningWithOptions();
    }

    private void startScanningWithOptions() {
        visitManager.setVisitListener(this);
        ProximityOptions options = new ProximityOptions();
        options.setOption(ProximityOptions.VisitOptionSignalStrengthWindowKey,
                ProximityOptions.VisitOptionSignalStrengthWindowNone);
        visitManager.startWithOptions(options);
    }

    @Override
    public void receivedSighting(Visit visit, Date updateTime, Integer rssi) {
        Log.d(TAG, "<<Visit Manager Handler>> I received a sighting! " + visit.getTransmitter().getName() 
        		+ " and RSSI is:" + rssi 
        		+ ", temperature is " + visit.getTransmitter().getTemperature() + ", battery is " + visit.getTransmitter().getBattery());

        String name = visit.getTransmitter().getName();
        TransmitterAttributes attributes = new TransmitterAttributes();
        attributes.setBattery(visit.getTransmitter().getBattery());
        attributes.setIdentifier(visit.getTransmitter().getIdentifier());
        attributes.setName(visit.getTransmitter().getName());
        attributes.setTemperature(visit.getTransmitter().getTemperature());
        attributes.setRssi(rssi);
        
        
        this.setLastStatus(name, rssi, attributes);
        
        attributes.setDepart(false);
        transmitters.put(name, attributes);
        
        
//        this.activity.addDevice(transmitters);
    }
    
    public void setLastStatus(String name, int rssi, TransmitterAttributes attributes){
    	if(transmitters.get(name) != null){
	    	if(-transmitters.get(name).getRssi() < 70 && -rssi > 70){
	    		this.sendRequest(transmitters.get(name), false, rssi);
	    	}else if(-transmitters.get(name).getRssi() > 70 && -rssi < 70){
	    		this.sendRequest(transmitters.get(name), true, rssi);
	    	}
    	}else{
    		if(-rssi > 70){
	    		this.sendRequest(attributes, false, rssi);
	    	}else if(-rssi < 70){
	    		this.sendRequest(attributes, true, rssi);
	    	}
    	}
    	
    }
    
    public void sendRequest(TransmitterAttributes attributes, boolean status, int rssi){
        // The toggle is enabled
    	PostTask pt = new PostTask();
    	
    	JSONObject jsonObject = new JSONObject();
    	
        try {
			jsonObject.accumulate("userId", "1");
//			jsonObject.accumulate("needHeat", false);
			jsonObject.accumulate("inRange", status);
//			jsonObject.accumulate("rssi", attributes.getRssi());
//			jsonObject.accumulate("batteryLevel", attributes.getBattery());
			jsonObject.accumulate("deviceId", attributes.getIdentifier());

			Calendar c = Calendar.getInstance(); 
			int seconds = c.get(Calendar.SECOND);
			
			System.out.println("deviceId: " + attributes.getIdentifier() + ", inRange: " + status + ", rssi: " + rssi + ", time: " + seconds);
			
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	
		pt.generateJsonObj(jsonObject); 										
		pt.execute(IP + "iBeacon");		        	

    }    

    @Override
    public void didArrive(Visit visit) {
        Log.d(TAG, "I got ARRIVE for " + visit.getTransmitter().getName());        
    }

    @Override
    public void didDepart(Visit visit) {
        Log.d(TAG, "I got DEPART for " + visit.getTransmitter().getName());
        String name = visit.getTransmitter().getName();
        TransmitterAttributes attributes = new TransmitterAttributes();
        attributes.setDepart(true);
        transmitters.put(name, attributes);
        this.activity.addDevice(transmitters);        
    }

}
