package com.example.MyWeibo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.MyWeibo.adapter.BaseAdapter;
import com.example.MyWeibo.utils.TopicsHelper;
import com.example.dbutil.TopicItem;

import java.util.List;

/**
 * Created by Andy.Wang on 2015/11/2.
 */
public class AllSubscribeFragment extends Fragment {
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView = new ListView(getActivity());
        return listView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<TopicItem> lists = TopicsHelper.readLocalTopics();
        if (lists != null) {
            listView.setAdapter(new MyaDapter(getActivity(), lists));
        }
    }

    private class MyaDapter extends BaseAdapter {

        List<TopicItem> lists;

        public MyaDapter(Context context, List items) {
            super(context, items);
            lists = items;
        }

        @Override
        protected View bindViewAndData(LayoutInflater infater, int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getActivity());
            textView.setText(lists.get(position).getTopicName());
            return textView;
        }
    }
}
