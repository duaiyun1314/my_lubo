package com.example.MyWeibo.fragments;

import com.example.MyWeibo.data.BaseNewsDataProvider;
import com.example.MyWeibo.processers.NewsListProcesser;

/**
 * Created by wanglu on 15/6/16.
 */
public abstract class BaseNewsListFragment<Provider extends BaseNewsDataProvider> extends BaseListFragment<Provider, NewsListProcesser<Provider>> {
    @Override
    protected NewsListProcesser<Provider> createProcesser() {
        return new NewsListProcesser<>(getProvider());
    }
}
