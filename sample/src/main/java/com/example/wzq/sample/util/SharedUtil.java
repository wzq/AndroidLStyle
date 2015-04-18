package com.example.wzq.sample.util;


import java.util.Map;


import android.content.Context;
import android.content.SharedPreferences;


public class SharedUtil{
	
	public static SharedPreferences sp;
	public static void setUser(Context context,EasyMap map){
		sp = context.getSharedPreferences(Constants.USER_INFO, 0);
		setMap(map);
	}
	@SuppressWarnings("unchecked")
	public static EasyMap getUser(Context context){
		sp = context.getSharedPreferences(Constants.USER_INFO, 0);
		return new EasyMap((Map<String, Object>) sp.getAll());
	}
	
//	public static void setUserExt(Context context,EasyMap map){
//		sp = context.getSharedPreferences(Configuration.Constants.CAR_USER_EXT, 0);
//		setMap(map);
//	}
//	@SuppressWarnings("unchecked")
//	public static EasyMap getUserExt(Context context){
//		sp = context.getSharedPreferences(Configuration.Constants.CAR_USER_EXT, 0);
//		return new EasyMap((Map<String, String>) sp.getAll());
//	}
	
	public static void clear(Context context){
		context.getSharedPreferences(Constants.USER_INFO, 0).edit().clear().commit();
	}
	public static void updateUser(Context context,String key,String value){
		sp = context.getSharedPreferences(Constants.USER_INFO, 0);
		sp.edit().putString(key, value).commit();
	}
	
	private static void setMap(EasyMap map){
		for (String key : map.keySet()) {
			sp.edit().putString(key, map.getString(key)).commit();
		}
	}
	


}
