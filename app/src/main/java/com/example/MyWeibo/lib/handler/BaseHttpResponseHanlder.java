package com.example.MyWeibo.lib.handler;

import com.example.MyWeibo.model.ResponseObject;
import com.google.gson.reflect.TypeToken;

import org.apache.http.Header;

/**
 * Created by wanglu on 15/6/19.
 */
public abstract class BaseHttpResponseHanlder<T> extends GsonHttpResponseHandler<ResponseObject<T>> {
    public BaseHttpResponseHanlder(TypeToken<ResponseObject<T>> type) {
        super(type);
    }

    @Override
    protected void onError(int statusCode, Header[] headers, String responseString, Throwable cause) {

    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString, ResponseObject<T> object) {
        if ("success".equals(object.getState())) {
            onSuccess(object.getResult());
        } else {
            onError(statusCode, headers, responseString, new Exception("load news list fail"));
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

    }

    protected abstract void onSuccess(T result);
}
