package com.example.wzq.sample.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	public static JSONObject getObj(String str) throws Exception {
		JSONObject obj = null;
		if(!TextUtils.isEmpty(str)){
			obj = new JSONObject(str);
		}
		return obj;
	}
	
	public static Map<String,Object> getMap(String str) throws Exception{
		Map<String,Object> map = null;
		if(!TextUtils.isEmpty(str)){
			JSONObject obj = getObj(str);
			map = new HashMap<String,Object>();
			Iterator<String> it = obj.keys();
			while (it.hasNext()) {
				String key = it.next();
				Object value = obj.get(key);
				if(value instanceof JSONObject){
					map.put(key, getMap(value.toString()));
				}else if (value instanceof JSONArray) {
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					JSONArray v = (JSONArray) value;
					for (int i = 0; i < v.length(); i++) {
						list.add(getMap(v.getString(i)));
					}
					map.put(key, list);
				}else{
					map.put(key, value);
				}
			}
		}
		return map;
	}
	public static EasyMap getEasyMap(String str) throws Exception{
		EasyMap map = null;
		if(!TextUtils.isEmpty(str)){
			JSONObject obj = getObj(str);
			map = new EasyMap();
			Iterator<String> it = obj.keys();
			while (it.hasNext()) {
				String key = it.next();
				Object value = obj.get(key);
				if(value instanceof JSONObject){
					map.put(key, getEasyMap(value.toString()));
				}else if (value instanceof JSONArray) {
					List<EasyMap> list = new ArrayList<EasyMap>();
					JSONArray v = (JSONArray) value;
					for (int i = 0; i < v.length(); i++) {
						list.add(getEasyMap(v.getString(i)));
					}
					map.put(key, list);
				}else{
					map.put(key, value);
				}
			}
		}
		return map;
	}
	
	public static String map2json(Map map){
		JSONObject obj = new JSONObject(map);
		return obj.toString().trim();
	}

	public static <T> T json2obj(String json, Class<T> tClass){
		Gson gson = new Gson();
		return gson.fromJson(json, tClass);
	}
}
