package com.example.MyWeibo.fragments;


import com.example.MyWeibo.data.impl.NetHotCommentDataProvider;
import com.example.MyWeibo.processers.BaseListProcesser;

/**
 * Created by wanglu on 15/6/16.
 */
public class HotComentFragment extends BaseListFragment<NetHotCommentDataProvider, BaseListProcesser<NetHotCommentDataProvider>> {
    @Override
    protected BaseListProcesser createProcesser(NetHotCommentDataProvider provider) {
        return new BaseListProcesser(provider);
    }

    @Override
    protected NetHotCommentDataProvider getProvider() {
        return new NetHotCommentDataProvider(getActivity());
    }
}
