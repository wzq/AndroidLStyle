package com.example.wzq.sample.util.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wzq.sample.model.User;
import com.example.wzq.sample.util.AppInfo;
import com.example.wzq.sample.util.EasyMap;
import com.example.wzq.sample.util.JsonUtil;
import com.example.wzq.sample.util.PreferenceUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by wzq on 15/4/14.
 */
public class VolleyHelper {

    private Context context;

    private static VolleyHelper helper = null;

    private RequestQueue requestQueue;

    private static final int TIMEOUT = 30000;

    private static final int RETRIES = 1;

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

//    public static VolleyHelper getNewInstance(Context context) {
//        return new VolleyHelper(context);
//    }

    /**
     * @param hostSet
     * @param params
     * @param clazz
     * @param callback
     */
    public void get(HostSet hostSet, EasyMap params, Class clazz, EasyListener.CallBack callback) {
        addHeadInfo(params);
        String uri = map2url(params, hostSet.getHost());
        StringRequest request = new StringRequest(uri, new EasyListener(context, callback, hostSet, clazz), new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        addRequest(request);
    }

    /**
     * @param hostSet It is must has a key.
     * @param params
     * @param clazz  result type
     * @param callback
     */
    public void post(final HostSet hostSet, final Object params, Class clazz, EasyListener.CallBack callback) {
        StringRequest request = new StringRequest(Request.Method.POST, hostSet.getHost(), new EasyListener(context, callback, hostSet, clazz), new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return parseParams(hostSet.getKey(), params);
            }
        };
        addRequest(request);
    }

    private Map<String, String> parseParams(String key, Object params) {
        Map<String, String> temp = new HashMap<>();
        addHeadInfo(temp);
        String value;
        if (params instanceof Map) {
            value = JsonUtil.map2json((Map) params);
        } else {
            value = JsonUtil.obj2json(params);
        }
        temp.put(key, value);
        return temp;
    }

    private void addRequest(Request<?> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(context.toString());
        requestQueue.add(request);
    }

    public void stop(){
        requestQueue.stop();
    }

    public void cancelByTag(Context c){
        requestQueue.cancelAll(c.toString());
    }

    public void start(){
        requestQueue.start();
    }

    public RequestQueue getQueue(){
        return requestQueue;
    }

    private String map2url(EasyMap params, String str) {
        StringBuilder url = new StringBuilder(str);
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

    /**
     * @param params keep the value type is string.
     */
    @SuppressWarnings("unchecked")
    private void addHeadInfo(Map params) {
        params.put("d_id", AppInfo.getDeviceId(context));
        params.put("ts", AppInfo.getTime(0));
        params.put("p", AppInfo.getClientType());
        params.put("ver", AppInfo.getAppVersion(context));
        params.put("c_id", AppInfo.getChannelId());
        User u = PreferenceUtil.getUser(context);
        if (u != null) {
            params.put("u_id", u.getId() + "");
            params.put("tk", u.getToken());
        }
    }
}
