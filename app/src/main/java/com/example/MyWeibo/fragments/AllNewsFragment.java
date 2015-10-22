package com.example.MyWeibo.fragments;


import com.example.MyWeibo.data.impl.NetNewsDataProvider;

/**
 * Created by wanglu on 15/6/16.
 */
public class AllNewsFragment extends BaseNewsListFragment {
    @Override
    protected NetNewsDataProvider getProvider() {
        return new NetNewsDataProvider(getActivity()) {
            @Override
            public String getTypeKey() {
                return "all";
            }

            @Override
            public String getTypeName() {
                return "全部资讯";
            }
        };
    }
}
