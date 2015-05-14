package com.example.wzq.sample.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.wzq.sample.BuildConfig;

import java.util.UUID;

public class AppInfo {
	public static String sChannel = "1";
	
	private static String sDeviceId;

	public static String getAppPackName() {
		return "com.firstlink";
	}

	public static String getClientType() {
		return "a";
	}

	public static String getAppVersion(Context context) {
		return BuildConfig.VERSION_NAME;
	}

	public static String getChannelId() {
		return sChannel;
	}
	
	
	public static String getDeviceId(Context context) {
		if (TextUtils.isEmpty(sDeviceId)) {
			try {
				TelephonyManager telephonyManager = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				if (telephonyManager != null) {
					String imei = telephonyManager.getDeviceId();
					sDeviceId = imei;
					return sDeviceId;
				}
			} catch (Exception e) {
			}
			// 之后会缓存到系统内
			UUID uuid = UUID.randomUUID();
			sDeviceId = uuid.toString();
			return sDeviceId;
		}
		return sDeviceId;
	}
	
	public static String getTime(int t){
		long time = System.currentTimeMillis()+t;
		return String.valueOf(time);
	}
}
