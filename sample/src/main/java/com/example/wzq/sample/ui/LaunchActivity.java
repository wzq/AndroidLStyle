package com.example.wzq.sample.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.wzq.sample.R;
import com.example.wzq.sample.util.Constants;
import com.example.wzq.sample.util.EasyMap;
import com.example.wzq.sample.util.SharedUtil;
import com.example.wzq.sample.util.network.EasyListener;
import com.example.wzq.sample.util.network.VolleyHelper;

/**
 * Created by wzq on 15/4/15.
 */
public class LaunchActivity extends Activity implements EasyListener.CallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        SharedUtil.clear(this);
        EasyMap params = new EasyMap();
        params.put("mobile", "18530593293");
        params.put("verificationcode", "123456");
        VolleyHelper.getInstance(this).get(Constants.LOGIN_REQ, Constants.LOGIN, params, EasyMap.class, this);
    }

    @Override
    public void updateUI(Object obj, int reqCode) {
        EasyMap result = (EasyMap) obj;
        if(result.getMap("user")!=null){
            SharedUtil.setUser(this, result.getMap("user"));
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
