package com.example.wzq.sample.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.wzq.sample.R;
import com.example.wzq.sample.util.network.EasyListener;
import com.example.wzq.sample.util.network.HostSet;

/**
 * Created by wzq on 15/4/15.
 */
public class LaunchActivity extends Activity implements EasyListener.CallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void updateUI(Object result, HostSet request) {

    }
}
