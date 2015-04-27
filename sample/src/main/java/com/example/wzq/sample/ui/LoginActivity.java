package com.example.wzq.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.wzq.sample.R;
import com.example.wzq.sample.model.User;
import com.example.wzq.sample.model.result.ResultLogin;
import com.example.wzq.sample.util.Constants;
import com.example.wzq.sample.util.EasyMap;
import com.example.wzq.sample.util.SharedUtil;
import com.example.wzq.sample.util.network.EasyListener;
import com.example.wzq.sample.util.network.VolleyHelper;

/**
 * Created by wzq on 15/4/27.
 */
public class LoginActivity extends BaseActivity implements EasyListener.CallBack, View.OnClickListener{

    private EditText account, password;

    @Override
    protected void mainCode(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);
        User user = SharedUtil.getUser(this);
        if(user!=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void updateUI(Object result, int reqCode) {
        ResultLogin user = (ResultLogin) result;
        SharedUtil.setUser(this, user.getUser());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        EasyMap params = new EasyMap();
        params.put("mobile", account.getText().toString());
        params.put("verificationcode", password.getText().toString());
        VolleyHelper.getInstance(this).get(Constants.LOGIN_REQ, Constants.LOGIN, params, ResultLogin.class, this);
    }
}
