//
//  Copyright (c) 2014 Texas Instruments. All rights reserved.
//

package com.baiheplayer.desk.connect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

public class MDnsHelper implements ServiceListener, ServiceTypeListener {
	
	public static final String SERVICE_TYPE = "_http._tcp.";
	public static final String SMARTCONFIG_IDENTIFIER = "srcvers=1D90645";
	public static final String CONFIG_STOPED = "com.usr.tismartconfig.stoped";

    Context context;
	MDnsCallbackInterface callback;
	private JmDNS jmdns;
    private MulticastLock multicastLock;
    WifiManager wm;
    InetAddress bindingAddress;
    boolean isDiscovering;
    
    public void init(Context context, MDnsCallbackInterface callback) {
    	this.context = context;
    	this.callback = callback;
    	isDiscovering = false;
    	wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    	multicastLock = wm.createMulticastLock(getClass().getName());
    	multicastLock.setReferenceCounted(true);
    }


    public void startDiscovery() {
    	if (isDiscovering)
    		return;
    	final InetAddress deviceIpAddress = getDeviceIpAddress(wm);
    	if (!multicastLock.isHeld()){
    		multicastLock.acquire();
    	} else {
    		Log.i("chen"," Muticast lock already held...");
    	}
    	try {
    		jmdns = JmDNS.create(deviceIpAddress, "SmartConfig");
    		jmdns.addServiceTypeListener(this);
    		jmdns.addServiceListener("usr--------", this);
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (jmdns != null) {
    			isDiscovering = true;
				Log.i("chen","discovering services");
    		}
    	}
    }
    
    public void stopDiscovery() {
    	try {
    		if (!isDiscovering || jmdns == null)
    			return;
    		if (multicastLock.isHeld()){
    			multicastLock.release();
    		} else {
				Log.i("chen","Multicast lock already released");
    		}
    		jmdns.unregisterAllServices();
    		jmdns.close();
    		jmdns = null;
    		isDiscovering = false;
//			Log.i("chen","MDNS discovery stopped");
    		
    		context.sendBroadcast(new Intent(CONFIG_STOPED));
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
	@Override
	public void serviceAdded(ServiceEvent service) {
//		Log.i("chen","MDnsHelper------------------>serviceAdded");
	}

	@Override
	public void serviceRemoved(ServiceEvent service) {
//		Log.i("chen","MDnsHelper------------------>serviceRemoved");
	}

	@Override
	public void serviceResolved(ServiceEvent service) {
		if (service.getInfo().getNiceTextString().contains(SMARTCONFIG_IDENTIFIER)){
			System.out.println("resolved: " + service.toString());
			JSONObject deviceJSON = new JSONObject();
			try {
				deviceJSON.put("name", service.getName());
				deviceJSON.put("host", service.getInfo().getHostAddresses()[0]);
				deviceJSON.put("port", service.getInfo().getPort());
				callback.onDeviceResolved(deviceJSON);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private InetAddress getDeviceIpAddress(WifiManager wifi) {
		InetAddress result = null;
		try {
			// default to Android localhost
			result = InetAddress.getByName("10.0.0.2");

			// figure out our wifi address, otherwise bail
			WifiInfo wifiinfo = wifi.getConnectionInfo();
			int intaddr = wifiinfo.getIpAddress();
			byte[] byteaddr = new byte[] { (byte) (intaddr & 0xff), (byte) (intaddr >> 8 & 0xff), (byte) (intaddr >> 16 & 0xff), (byte) (intaddr >> 24 & 0xff) };
			result = InetAddress.getByAddress(byteaddr);
		} catch (UnknownHostException ex) {
			Log.w("MDNSHelper", String.format("getDeviceIpAddress Error: %s", ex.getMessage()));
		}

		return result;
	}

	@Override
	public void serviceTypeAdded(ServiceEvent event) {
		System.out.println("MDnsHelper------------------>serviceTypeAdded");
		// TODO Auto-generated method stub
		if (event.getType().contains(SERVICE_TYPE)) {
			jmdns.addServiceListener(event.getType(), this);
		}
	}

	@Override
	public void subTypeForServiceTypeAdded(ServiceEvent event) {
		System.out.println("MDnsHelper------------------>subTypeForServiceTypeAdded");
	}

}