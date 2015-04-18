package com.example.wzq.sample.model.result;

import com.example.wzq.sample.model.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wzq on 15/4/16.
 */
public class ResultLogin {

    @SerializedName("isnew")
    private boolean isNew;

    private User user;

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
