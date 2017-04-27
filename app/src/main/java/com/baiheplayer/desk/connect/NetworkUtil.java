//
//  Copyright (c) 2014 Texas Instruments. All rights reserved.
//

package com.baiheplayer.desk.connect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

public class NetworkUtil {

	public static int NOT_CONNECTED = 0;
	public static int WIFI = 1;
	public static int MOBILE = 2;
	private static List<WifiConfiguration> configuredNetworks;
	
	public static int getConnectionStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return WIFI;
			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return MOBILE;
		}
		return NOT_CONNECTED;
	}
	
	public static String getConnectionStatusString (Context context) {
		int connectionStatus = NetworkUtil.getConnectionStatus(context);
		if (connectionStatus == NetworkUtil.WIFI)
			return "Connected to Wifi";
		if (connectionStatus == NetworkUtil.MOBILE)
			return "Connected to Mobile Data";
		return "No internet connection";	
	}
	
	public static String getWifiName (Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		String wifiName = wifiManager.getConnectionInfo().getSSID();
		if (wifiName != null){
			if (!wifiName.contains("unknown ssid") && wifiName.length() > 2){
				if (wifiName.startsWith("\"") && wifiName.endsWith("\""))
					wifiName = wifiName.subSequence(1, wifiName.length() - 1).toString();
				return wifiName;
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public static String getGateway (Context context) {
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		
		System.out.println("NetWorkUtil--------------->getGatway:"+wm.getDhcpInfo().gateway);
		
		return NetworkUtil.intToIp(wm.getDhcpInfo().gateway);
	}
	
	public static String intToIp(int i) {
		String ip = (i & 0xFF) + "." +((i >> 8 ) & 0xFF) + "." +((i >> 16 ) & 0xFF) + "." +((i >> 24 ) & 0xFF );;
		System.out.println("NetWorkUtil--------------->intToIp:"+ip);
		
		return ip;
	}

	public static boolean connectAP(WifiManager manager, String ssid){
		configuredNetworks = manager.getConfiguredNetworks();
		for(int i = 0; i < configuredNetworks.size(); i++){
			WifiConfiguration wifi = configuredNetworks.get(i);
			if(wifi.SSID.equals("\""+ssid+"\"")){
				int wifiId = wifi.networkId;
				if((manager.enableNetwork(wifiId, true))){//激活该Id，建立连接,返回true表示激活成功
					//status:0--已经连接，1--不可连接，2--可以连接
					Log.i("chen",ssid+" 连接？"+String.valueOf(configuredNetworks.get(wifiId).status));
					return true;
				}
			}
		}
		return false;
	}
}
