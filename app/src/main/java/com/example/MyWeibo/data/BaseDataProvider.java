package com.example.MyWeibo.data;

import android.app.Activity;

public abstract class BaseDataProvider<T> {

    private Activity mActivity;
    protected DataProviderCallback<T> callback;

    public BaseDataProvider(Activity activity) {
        mActivity = activity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 加载本地缓存数据
     */
    public abstract void loadData();

    public void setDataProviderCallback(DataProviderCallback<T> callback) {
        this.callback = callback;
    }
}
