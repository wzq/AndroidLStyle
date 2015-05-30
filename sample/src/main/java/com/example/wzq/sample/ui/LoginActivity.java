package com.example.wzq.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.wzq.sample.R;
import com.example.wzq.sample.model.User;
import com.example.wzq.sample.model.result.ResultLogin;
import com.example.wzq.sample.util.EasyMap;
import com.example.wzq.sample.util.network.HostSet;
import com.example.wzq.sample.util.PreferenceUtil;
import com.example.wzq.sample.util.network.VolleyHelper;

/**
 * Created by wzq on 15/4/27.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText account, password;

    @Override
    protected void mainCode(Bundle savedInstanceState) {
        setBodyView(R.layout.activity_login);
        baseLoading.setVisibility(View.GONE);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);
        User user = PreferenceUtil.getUser(this);
        if(user!=null){
            login(user.getMobile(),"123456");
        }
    }

    @Override
    public void updateUI(Object result, int reqCode) {
        ResultLogin user = (ResultLogin) result;
        PreferenceUtil.setUser(this, user.getUser());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        login(account.getText().toString(), password.getText().toString());
        //go(ProfileActivity.class);
    }

    private void login(String...s){
        EasyMap params = new EasyMap();
        params.put("mobile", s[0]);
        params.put("verificationcode", s[1]);
        VolleyHelper.getInstance(this).get(HostSet.LOGIN, params, ResultLogin.class, this);
    }
}
