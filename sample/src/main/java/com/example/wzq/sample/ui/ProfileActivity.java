package com.example.wzq.sample.ui;

import android.os.Bundle;

import com.example.wzq.sample.R;
import com.example.wzq.sample.util.network.HostSet;

/**
 * Created by wzq on 15/5/8.
 */
public class ProfileActivity extends BaseActivity{

    @Override
    protected void mainCode(Bundle savedInstanceState) {
        setBodyView(R.layout.activity_profile);
    }

    @Override
    public void updateUI(Object result, HostSet request) {

    }
}
