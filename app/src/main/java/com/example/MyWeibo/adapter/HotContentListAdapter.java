package com.example.MyWeibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.MyWeibo.R;
import com.example.MyWeibo.model.HotCommentItem;
import com.example.MyWeibo.model.NewsItem;
import com.example.MyWeibo.ui.NewsHotCommentItemHoderView;
import com.example.MyWeibo.ui.NewsItemView;

import java.util.ArrayList;

/**
 * Created by wanglu on 15/6/18.
 */
public class HotContentListAdapter extends BaseAdapter<HotCommentItem> {

    public HotContentListAdapter(Context context, ArrayList<HotCommentItem> items) {
        super(context, items);
    }

    @Override
    protected View bindViewAndData(LayoutInflater infater, int position, View convertView, ViewGroup parent) {
        NewsHotCommentItemHoderView newsHotCommentItemHoderView = (NewsHotCommentItemHoderView) convertView;
        if (newsHotCommentItemHoderView == null) {
            newsHotCommentItemHoderView = (NewsHotCommentItemHoderView) infater.inflate(R.layout.hot_comment_item, parent, false);

        }
        HotCommentItem hotCommentItem = getDataSetItem(position);
        newsHotCommentItemHoderView.showComment(hotCommentItem);
        return newsHotCommentItemHoderView;
    }
}
