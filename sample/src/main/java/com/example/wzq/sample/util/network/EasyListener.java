package com.example.wzq.sample.util.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.example.wzq.sample.util.EasyMap;
import com.example.wzq.sample.util.JsonUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by wzq on 15/4/14.
 */
public class EasyListener implements Listener<String> {

    public static final int RESULT_CODE_OK = 1;

    private CallBack callback;

    private HostSet request;

    private Class clazz;

    private Context context;

    private Gson gson;

    public EasyListener(Context context, CallBack callback, HostSet request, Class clazz) {
        this.callback = callback;
        this.request = request;
        this.clazz = clazz;
        this.context = context;
        gson = new Gson();
    }

    @Override
    public void onResponse(String response) {
        try {
            parser(response);
        } catch (Exception e) {
            Toast.makeText(context, "数据异常", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public interface CallBack {
        void updateUI(Object result, HostSet request);
    }


    /**
     * custom your parser method.
     * @param response
     * @throws Exception
     */
    private void parser(String response) throws Exception {
        JSONObject jsonObject = JsonUtil.getObj(response);
        int code = jsonObject.getInt("code");
        String data = jsonObject.getString("data");
        String errorMsg = jsonObject.getString("message");
        if (code == RESULT_CODE_OK) {
            Object result;
            if (clazz.isInstance(EasyMap.class.newInstance())) {
                result = JsonUtil.getEasyMap(data);
            } else {
                result = gson.fromJson(data, clazz);
            }
            callback.updateUI(result, request);
        } else {
            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
        }
    }
}
