package com.example.wzq.sample.util;


import android.content.Context;

import com.example.wzq.sample.model.User;


public class SharedUtil {

    public static final String USER_INFO = "user_info";

    private static final String KEY_USER = "key_user";

    @Deprecated
    public static void setUser(Context context, EasyMap user) {
        if (user != null)
            context.getSharedPreferences(USER_INFO, 0).edit().putString(KEY_USER, JsonUtil.map2json(user)).commit();
    }

    public static void setUser(Context context, User user) {
        if (user != null)
            context.getSharedPreferences(USER_INFO, 0).edit().putString(KEY_USER, user.toString()).commit();
    }

    public static User getUser(Context context) {
        return JsonUtil.json2obj(context.getSharedPreferences(USER_INFO, 0).getString(KEY_USER, null), User.class);
    }

    public static void clear(Context context) {
        context.getSharedPreferences(USER_INFO, 0).edit().clear().commit();
    }
}
