package com.example.MyWeibo.lib.kits;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;


/**
 * Created by wanglu on 15/6/18.
 */
public class ToolKit {
    public static Gson gson;
    public static Handler uiHandler;

    public static void runInUIThread(Runnable runnable, int delay) {
        if (uiHandler == null) {
            uiHandler = new Handler(Looper.getMainLooper());
        }
        uiHandler.postDelayed(runnable, delay);

    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
