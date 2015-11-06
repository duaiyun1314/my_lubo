package com.example.MyWeibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MyWeibo.R;
import com.example.MyWeibo.model.NewsItem;
import com.example.MyWeibo.ui.NewsItemView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by wanglu on 15/6/18.
 */
public class NewsListAdapter extends BaseAdapter {

    DisplayImageOptions options;

    public NewsListAdapter(Context context, ArrayList<NewsItem> items) {
        super(context, items);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.imagehoder)
                .showImageOnFail(R.drawable.imagehoder_error)
                .build();
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
        itemView.showNews(item, options);
        return itemView;
    }
}
