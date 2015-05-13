package com.example.wzq.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wzq.sample.R;
import com.example.wzq.sample.util.network.EasyListener;

/**
 * Created by wzq on 15/4/14.
 */
public abstract class BaseActivity extends AppCompatActivity implements EasyListener.CallBack {

    @Deprecated
    protected ActionBar actionbar;

    protected Toolbar toolbar;

    protected ProgressBar baseLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            actionbar = getSupportActionBar();
            mainCode(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void mainCode(Bundle savedInstanceState);

    /**
     * If you have to custom a toolbar, you should use 'setContentView'.
     * @param resId
     */
    protected void setBodyView(int resId) {
        LinearLayout baseView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout baseBody = (FrameLayout) baseView.findViewById(R.id.base_body);
        baseBody.addView(getLayoutInflater().inflate(resId, null));
        setContentView(baseView);
        toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        baseLoading = (ProgressBar) findViewById(R.id.base_progress);
        setSupportActionBar(toolbar);
        //ButterKnife.inject(this);
    }

    protected void setBodyView(View view) {
        LinearLayout baseView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout baseBody = (FrameLayout) baseView.findViewById(R.id.base_body);
        baseBody.addView(view);
        setContentView(baseView);
        toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        baseLoading = (ProgressBar) findViewById(R.id.base_progress);
        setSupportActionBar(toolbar);
        //ButterKnife.inject(this);
    }

    protected void setBarTitle(String title) {
        toolbar.setTitle(title);
    }

    protected void setBarTitle(int resId) {
        toolbar.setTitle(resId);
    }

    protected void go(Class<?> act) {
        startActivity(new Intent(this, act));
    }

    protected void go(Intent intent) {
        startActivity(intent);
    }

    protected void showTips(String str, int time) {
        Toast.makeText(this, str, time).show();
    }

}
