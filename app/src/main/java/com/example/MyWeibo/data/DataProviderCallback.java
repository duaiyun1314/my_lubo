package com.example.MyWeibo.data;

public interface DataProviderCallback<T> {
    void onLoadStart();
    void onLoadSuccess(T object);
    void onLoadFinish(int size);
    void onLoadFailure();
}
