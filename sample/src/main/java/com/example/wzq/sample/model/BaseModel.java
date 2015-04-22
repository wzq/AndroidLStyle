package com.example.wzq.sample.model;

import com.google.gson.Gson;

/**
 * Created by wzq on 15/4/22.
 */
public class BaseModel {

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
