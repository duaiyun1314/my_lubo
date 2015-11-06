package com.example.MyWeibo.fragments;

import com.example.MyWeibo.data.BaseNewsDataProvider;
import com.example.MyWeibo.processers.NewsListProcesser;

/**
 * Created by wanglu on 15/6/16.
 */
public abstract class BaseNewsListFragment extends BaseListFragment {
    @Override
    protected NewsListProcesser createProcesser() {
        return new NewsListProcesser(getProvider());
    }
}
