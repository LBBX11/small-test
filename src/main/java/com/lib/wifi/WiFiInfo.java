package com.lib.wifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @title : 第四关
 * @Desc  :
 * 			1.获取所有的WiFI列表
 * 			2.获取所有的WIFI的SSID
 * @author Administrator
 *
 */
public class WiFiInfo {

	public static void main(String[] args) throws IOException {
		//1.获取所有的WiFI列表
		List<String> wifiList = getWiFiList();
		//2.获取所有的WIFI的SSID
		List<String> ssidList = getWiFiSSIDList(wifiList);
	}
	
	
	/**
	 * 1.获取所有的WiFI列表
	 * @return
	 * @throws IOException 
	 */
	public static List<String> getWiFiList() throws IOException{
		List<String> wifiList = new ArrayList<>();
		Process pro =  Runtime.getRuntime().exec("netsh wlan show network");
		
		InputStream is = pro.getInputStream();
		BufferedReader  br = new BufferedReader(new InputStreamReader(is,"GBK"));
		while(true){
			String msg = br.readLine();
			if(msg==null || msg==""){
				break;
			}
			System.err.println(msg);
			wifiList.add(msg);
		}
		return wifiList;
	}
	/**
	 * 2.获取所有的WIFI的SSID
	 * @return
	 */
	public static List<String> getWiFiSSIDList(List<String> wifiList){
		List<String> ssidList = new ArrayList<>();
		String result = "";
		for (String str : wifiList) {
			if(str.startsWith("SSID")){
				result = str.substring(str.indexOf(":")+2);
				System.err.println(result);
				ssidList.add(result);
			}
		}
		return ssidList;
	}
	

}
