package com.example.MyWeibo.processers;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.MyWeibo.R;
import com.example.MyWeibo.data.BaseDataProvider;
import com.example.MyWeibo.data.DataProviderCallback;

/**
 * Created by wanglu on 15/6/16.
 */
public abstract class BaseProcesserImpl<DataProvider extends BaseDataProvider> implements BaseProcesser, DataProviderCallback {
    protected AppCompatActivity mActivity;

    protected int colorPrimary;
    protected int colorPrimaryDark;
    protected int titleColor;
    protected int windowBackground;
    protected int colorAccent;
    protected DataProvider mProvider;

    public BaseProcesserImpl(DataProvider provider) {
        this.mProvider = provider;
        this.mProvider.setDataProviderCallback(this);
    }

    @Override
    public void setActivity(AppCompatActivity activity) {
        this.mActivity = activity;
        this.mProvider.setActivity(activity);
        TypedArray array = activity.obtainStyledAttributes(new int[]{R.attr.colorPrimary,
                R.attr.colorPrimaryDark, R.attr.titleColor, android.R.attr.windowBackground,
                R.attr.colorAccent
        });
        colorPrimary = array.getColor(0, activity.getResources().getColor(R.color.toolbarColor));
        colorPrimaryDark = array.getColor(1, activity.getResources().getColor(R.color.statusColor));
        titleColor = array.getColor(2, activity.getResources().getColor(R.color.toolbarColor));
        windowBackground = array.getColor(3, Color.WHITE);
        colorAccent = array.getColor(4, activity.getResources().getColor(R.color.toolbarColor));
        array.recycle();
    }

    @Override
    public void setProvider(BaseDataProvider provider) {

    }

    @Override
    public void loadData(boolean startup) {

    }

    @Override
    public void assumeView(View view) {

    }

    @Override
    public AppCompatActivity getActivity() {
        return mActivity;
    }

}
