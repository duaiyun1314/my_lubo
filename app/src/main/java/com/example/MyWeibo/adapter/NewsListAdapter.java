package com.example.MyWeibo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.MyWeibo.R;
import com.example.MyWeibo.model.NewsItem;
import com.example.MyWeibo.ui.NewsItemView;

import java.util.ArrayList;

/**
 * Created by wanglu on 15/6/18.
 */
public class NewsListAdapter extends BaseAdapter {

    public NewsListAdapter(Context context, ArrayList<NewsItem> items) {
        super(context, items);
    }

    @Override
    protected View bindViewAndData(LayoutInflater infater, int position, View convertView, ViewGroup parent) {
        /*TextView tv = new TextView(context);
        tv.setText(((NewsItem) getItem(position)).getHometext());*/
        NewsItemView itemView = (NewsItemView) convertView;
        if (itemView == null) {
            itemView = (NewsItemView) infater.inflate(R.layout.news_item, parent, false);
        }
        NewsItem item = (NewsItem) getDataSetItem(position);
        itemView.showNews(item);
        return itemView;
    }
}
