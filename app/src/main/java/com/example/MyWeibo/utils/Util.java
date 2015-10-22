package com.example.MyWeibo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.MyWeibo.LuBoApplication;

/**
 * Created by wanglu on 15-3-23.
 */
public class Util {
    public static boolean isNetworkConnected() {
        Context context = LuBoApplication.getInstance();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable()) {
            return true;
        }
        return false;
    }
}
