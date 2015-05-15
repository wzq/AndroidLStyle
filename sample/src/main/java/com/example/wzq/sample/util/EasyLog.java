package com.example.wzq.sample.util;

import android.util.Log;

import com.example.wzq.sample.BuildConfig;

/**
 * Created by wzq on 15/5/15.
 */
public class EasyLog {
    private static boolean FLAG = BuildConfig.LOG_SWITCH;
    private static final String TAG = "EasyLog";

    public static boolean e(String msg){
        if(FLAG)Log.e(TAG, msg);
        return FLAG;
    }

    public static boolean e(String tag, String msg){
        if(FLAG) Log.e(tag, msg);
        return FLAG;
    }

    public static boolean i(String msg){
        if(FLAG)Log.i(TAG, msg);
        return FLAG;
    }

    public static boolean i(String tag, String msg){
        if(FLAG)Log.i(tag, msg);
        return FLAG;
    }

    public static boolean v(String msg){
        if(FLAG)Log.v(TAG, msg);
        return FLAG;
    }

    public static boolean v(String tag, String msg){
        if(FLAG)Log.v(tag, msg);
        return FLAG;
    }

    public static boolean d(String msg){
        if(FLAG) Log.d(TAG, msg);
        return FLAG;
    }

    public static boolean d(String tag, String msg){
        if(FLAG)Log.d(tag, msg);
        return FLAG;
    }
}
