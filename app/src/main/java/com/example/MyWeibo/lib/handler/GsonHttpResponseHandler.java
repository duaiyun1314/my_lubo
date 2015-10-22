package com.example.MyWeibo.lib.handler;

import android.os.Environment;
import android.util.Log;

import com.example.MyWeibo.lib.kits.ToolKit;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;

/**
 * 将response string 转化成responseObject
 */
public abstract class GsonHttpResponseHandler<T> extends TextHttpResponseHandler {

    protected Type type;

    public GsonHttpResponseHandler(TypeToken<T> tTypeToken) {
        type = tTypeToken.getType();
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        if (statusCode == 200) {
            try {
                T t = ToolKit.getGson().fromJson(responseString, type);
                if (t != null) {
                    onSuccess(statusCode, headers, responseString, t);
                } else {
                    onFailure(statusCode, headers, responseString, new RuntimeException("response emyty"));
                }
            } catch (Exception e) {
                onError(statusCode, headers, responseString, e);
            }
        }
    }

    protected abstract void onError(int statusCode, Header[] headers, String responseString, Throwable cause);

    public abstract void onSuccess(int statusCode, Header[] headers, String responseString, T object);
}
