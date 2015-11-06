package com.example.MyWeibo.processers;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.MyWeibo.data.BaseDataProvider;

/**
 * Created by wanglu on 15/6/16.
 */
public interface BaseProcesser<DataProvider extends BaseDataProvider,T extends  Object> {
    void assumeView(View view);

    AppCompatActivity getActivity();

    void setActivity(AppCompatActivity activity);

    void setProvider(DataProvider provider);

    /**
     * 控制器控制加载数据
     *
     * @param startup
     */
    void loadData(boolean startup);

    void onResume();
}
