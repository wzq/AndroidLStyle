package com.example.wzq.sample.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyMap extends HashMap<String, Object> implements Serializable{

	private static final long serialVersionUID = 1L;

	public EasyMap() {
		super();
	}

	public EasyMap(Map<String, Object> map) {
		super(map);
	}

	public int getInt(String key, int def) {
		Object temp = this.get(key);
		return temp != null ? Integer.valueOf(temp.toString()) : def;
	}

	public String getString(String key) {
		Object temp = this.get(key);
		return temp != null ? temp.toString() : "";
	}

	public EasyMap getMap(String key) {
		Object temp = this.get(key);
		return temp != null ? (EasyMap) temp : null;
	}

	@SuppressWarnings("unchecked")
	public List<EasyMap> getList(String key) {
		Object temp = this.get(key);
		return check(temp) ? (List<EasyMap>) temp : null;
	}
	
	public List<EasyMap> getList(String key, int from, int to) {
		List<EasyMap> temp = getList(key);
		if(temp != null && temp.size() >= to)
			return temp.subList(from, to);
		else
			return temp;
	}

	public boolean getBoolean(String key) {
		Object temp = this.get(key);
		return temp != null ? Boolean.parseBoolean(temp.toString()) : null;
	}

//	public String getDateString(String key, String pattern) {
//		return FormatUtil.formatDate(this.getString(key), pattern);
//	}

	public String getString(String key, int len) {
		String temp = this.getString(key);
		return temp.length() > len ? temp.substring(0, len - 1) + "..." : temp;
	}
	
//	public String getMoney(String key){
//		Object temp = this.get(key);
//		return "￥"+FormatUtil.formatNumber(check(temp) ? temp.toString() : "0");
//	}
	
	private boolean check(Object temp){
		if(temp != null && !temp.toString().equals("null")){
			return true;
		}
		return false;
	}

	
}
