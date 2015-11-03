package com.example.MyWeibo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.MyWeibo.data.ListDataProvider;
import com.example.MyWeibo.data.impl.TopicScribedDataProvider;
import com.example.MyWeibo.processers.BaseListProcesser;
import com.example.MyWeibo.processers.BaseProcesser;
import com.example.MyWeibo.processers.SubscribedProcesser;

/**
 * Created by Andy.Wang on 2015/11/2.
 */
public class SubscribedFragment extends BaseListFragment<TopicScribedDataProvider, SubscribedProcesser> {

    @Override
    protected SubscribedProcesser createProcesser() {
        return new SubscribedProcesser(getProvider());
    }

    @Override
    protected TopicScribedDataProvider getProvider() {
        return new TopicScribedDataProvider(getActivity());
    }
}
