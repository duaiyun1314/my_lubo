package com.example.MyWeibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MyWeibo.model.TopicItem;

import java.util.List;

/**
 * Created by wanglu on 15/11/3.
 */
public class TopicListAdpater extends BaseAdapter<TopicItem> {
    public TopicListAdpater(Context context) {
        super(context);
    }

    public TopicListAdpater(Context context, List<TopicItem> items) {
        super(context, items);
    }

    @Override
    protected View bindViewAndData(LayoutInflater infater, int position, View convertView, ViewGroup parent) {
        return null;
    }
}
