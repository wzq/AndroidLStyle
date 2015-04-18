package com.example.wzq.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * Created by wzq on 15/4/14.
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected ActionBar actionbar;

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

    protected void showTips(String str, int time){
        Toast.makeText(this, str, time).show();
    }

    protected void openHome() {
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void go(Class<?> act){
        startActivity(new Intent(this,act));
    }

    protected void go(Intent intent){
        startActivity(intent);
    }
}
