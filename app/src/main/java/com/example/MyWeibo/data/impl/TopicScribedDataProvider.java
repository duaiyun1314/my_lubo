package com.example.MyWeibo.data.impl;

import android.app.Activity;

import com.example.MyWeibo.adapter.BaseAdapter;
import com.example.MyWeibo.adapter.TopicListAdpater;
import com.example.MyWeibo.data.ListDataProvider;

/**
 * Created by wanglu on 15/11/3.
 */
public class TopicScribedDataProvider extends ListDataProvider {
    public TopicScribedDataProvider(Activity activity) {
        super(activity);
    }

    @Override
    public String getTypeKey() {
        return "";
    }

    @Override
    public String getTypeName() {
        return "已关注";
    }

    @Override
    protected BaseAdapter newAdapter() {
        return new TopicListAdpater(getActivity());
    }

    @Override
    public void loadNewData() {


    }

    @Override
    public void loadNextData() {

    }

    @Override
    public void loadData() {

    }
}
