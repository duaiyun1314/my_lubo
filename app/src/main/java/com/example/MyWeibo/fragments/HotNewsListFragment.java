package com.example.MyWeibo.fragments;


import com.example.MyWeibo.data.impl.NetNewsDataProvider;

/**
 * Created by wanglu on 15/6/16.
 */
public class HotNewsListFragment extends BaseNewsListFragment {
    @Override
    protected NetNewsDataProvider getProvider() {
        return new NetNewsDataProvider(getActivity()) {
            @Override
            public String getTypeKey() {
                return "dig";
            }

            @Override
            public String getTypeName() {
                return "人气推荐";
            }
        };
    }
}
