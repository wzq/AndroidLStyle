package com.example.wzq.sample.util.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wzq.sample.util.AppInfo;
import com.example.wzq.sample.util.Constants;
import com.example.wzq.sample.util.EasyMap;
import com.example.wzq.sample.util.JsonUtil;
import com.example.wzq.sample.util.SharedUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class VolleyHelper {

    private Context context;

    private static VolleyHelper helper = null;

    private RequestQueue requestQueue;

    private VolleyHelper(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyHelper getInstance(Context context) {
        if (helper == null) {
            helper = new VolleyHelper(context);
        }
        return helper;
    }

    public static VolleyHelper getNewInstance(Context context) {
        return new VolleyHelper(context);
    }

    /**
     * @param reqCode
     * @param url
     * @param params
     * @param clazz
     * @param callback
     */
    public void get(int reqCode, String url, EasyMap params, Class clazz, EasyListener.CallBack callback) {
        addHeadInfo(params);
        String uri = map2url(params, url);
        StringRequest request = new StringRequest(uri, new EasyListener(context, callback, reqCode, clazz), new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    /**
     * @param reqCode
     * @param url
     * @param key
     * @param params
     * @param clazz
     * @param callback
     */
    public void post(int reqCode, String url, final String key, final EasyMap params, Class clazz, EasyListener.CallBack callback) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.HOST + url, new EasyListener(context, callback, reqCode, clazz), new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> temp = new HashMap<>();
                addHeadInfo(temp);
                temp.put(key, JsonUtil.map2json(params));
                System.out.println(temp);
                return temp;
            }
        };
        requestQueue.add(request);
    }

    private String map2url(EasyMap params, String str) {
        StringBuilder url;
        url = new StringBuilder(Constants.HOST);
        url.append(str);
        Set<String> keySet = params.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            url.append(key).append("=").append(params.getString(key));
            if (it.hasNext()) {
                url.append("&");
            }
        }
        return url.toString();
    }

    @SuppressWarnings("unchecked")
    private void addHeadInfo(Map params) {
        params.put("d_id", AppInfo.getDeviceId(context));
        params.put("ts", AppInfo.getTime(0));
        params.put("p", AppInfo.getClientType());
        params.put("ver", AppInfo.getAppVersion(context));
        params.put("c_id", AppInfo.getChannelId());
        if (SharedUtil.getUser(context).get("userid") != null) {
            params.put("u_id", SharedUtil.getUser(context).get("userid"));
            params.put("tk", SharedUtil.getUser(context).get("token"));
        }
    }
}
