package com.example.MyWeibo.data.impl;

import android.app.Activity;

import com.example.MyWeibo.adapter.HotContentListAdapter;
import com.example.MyWeibo.data.ListDataProvider;
import com.example.MyWeibo.lib.Configure;
import com.example.MyWeibo.lib.handler.BaseHttpResponseHanlder;
import com.example.MyWeibo.lib.kits.NetKit;
import com.example.MyWeibo.model.HotCommentItem;
import com.example.MyWeibo.model.ResponseObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by wanglu on 15/9/25.
 */
public class NetHotCommentDataProvider extends ListDataProvider {
    private int current;
    private ResponseHandlerInterface responseHandlerInterface = new BaseHttpResponseHanlder<List<HotCommentItem>>(new TypeToken<ResponseObject<List<HotCommentItem>>>() {
    }) {
        @Override
        protected void onSuccess(List<HotCommentItem> result) {
            for (HotCommentItem item : result) {
                Matcher hotMatcher = Configure.HOT_COMMENT_PATTERN.matcher(item.getDescription());
                if (hotMatcher.find()) {
                    item.setFrom(hotMatcher.group(1));
                    item.setDescription(hotMatcher.group(2));
                    item.setSid(Integer.parseInt(hotMatcher.group(3)));
                    item.setNewstitle(hotMatcher.group(4));
                }
            }
            if (current == 1) {
                getAdapter().setDataSet(result);
                //Toolkit.showCrouton(getActivity(), getActivity().getString(R.string.message_flush_success), CroutonStyle.INFO);
                // FileCacheKit.getInstance().putAsync(getTypeKey().hashCode() + "", Toolkit.getGson().toJson(result), "list", null);
            } else {
                getAdapter().getDataSet().addAll(result);
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            callback.onLoadFinish(40);
        }
    };

    public NetHotCommentDataProvider(Activity activity) {
        super(activity);
    }

    @Override
    public String getTypeKey() {
        return "jhcomment";
    }

    @Override
    public String getTypeName() {
        return "精彩评论";
    }

    @Override
    protected HotContentListAdapter newAdapter() {
        return new HotContentListAdapter(getActivity(), new ArrayList<HotCommentItem>());
    }

    @Override
    public void loadNewData() {
        makeRequest(1, getTypeKey(), responseHandlerInterface);
    }

    @Override
    public void loadNextData() {
        current++;
        makeRequest(current, getTypeKey(), responseHandlerInterface);
    }

    @Override
    public void loadData() {
        this.current = 1;
    }

    public void makeRequest(int page, String type, ResponseHandlerInterface handlerInterface) {
        NetKit.getInstance().getNewslistByPage(page, type, handlerInterface);
    }
}
