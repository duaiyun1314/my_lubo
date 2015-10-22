package com.example.MyWeibo.data.impl;

import android.app.Activity;
import android.text.Html;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.MyWeibo.adapter.NewsListAdapter;
import com.example.MyWeibo.data.BaseNewsDataProvider;
import com.example.MyWeibo.lib.handler.BaseHttpResponseHanlder;
import com.example.MyWeibo.lib.kits.NetKit;
import com.example.MyWeibo.model.NewsItem;
import com.example.MyWeibo.model.NewsListObject;
import com.example.MyWeibo.model.ResponseObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglu on 15/6/16.
 */
public abstract class NetNewsDataProvider extends BaseNewsDataProvider {
    private int current;
    private int topSid;

    public NetNewsDataProvider(Activity activity) {
        super(activity);
    }

    private ResponseHandlerInterface responseHandlerInterface = new BaseHttpResponseHanlder<NewsListObject>(new TypeToken<ResponseObject<NewsListObject>>() {
    }) {
        @Override
        protected void onSuccess(NewsListObject result) {
            List<NewsItem> itemList = result.getList();
            List<NewsItem> dataSet = getAdapter().getDataSet();
            int size = 0;
            boolean find = false;
            for (int i = 0; i < itemList.size(); i++) {
                NewsItem item = itemList.get(i);
                if (itemList.get(i).getCounter() != null && item.getComments() != null) {
                    int num = Integer.parseInt(item.getCounter());
                    if (num > 9999) {
                        item.setCounter("9999+");
                    }
                    num = Integer.parseInt(item.getComments());
                    if (num > 999) {
                        item.setComments("999+");
                    }
                } else {
                    item.setCounter("0");
                    item.setComments("0");
                }
                StringBuilder sb = new StringBuilder(Html.fromHtml(item.getHometext().replaceAll("<.*?>|[\\r|\\n]", "")));
                if (sb.length() > 140) {
                    item.setSummary(sb.replace(140, sb.length(), "...").toString());
                } else {
                    item.setSummary(sb.toString());
                }
                if (item.getThumb().contains("thumb")) {
                    item.setLargeImage(item.getThumb().replaceAll("(\\.\\w{3,4})?_100x100|thumb/mini/", ""));
                }
                if (!find && item.getSid() != topSid) {
                    size++;
                } else if (!find) {
                    find = true;
                }
            }
            if (!find) {
                size++;
            }

            if (!hasCached || result.getPage() == 1) {
                hasCached = true;
                getAdapter().setDataSet(itemList);
                if (itemList.size() > 2) {
                    topSid = itemList.get(1).getSid();
                }
                // showToastAndCache(itemList, size - 1);
            } else {
                dataSet.addAll(itemList);
            }
            current = result.getPage();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (callback != null) {
                callback.onLoadFinish(40);
            }
        }
    };

    @Override
    public void loadData() {
        this.current = 1;
    }

    @Override
    protected NewsListAdapter newAdapter() {
        return new NewsListAdapter(getActivity(), new ArrayList<NewsItem>());
    }

    @Override
    public void loadNewData() {
        makeRequest(1, getTypeKey(), responseHandlerInterface);
    }

    @Override
    public void loadNextData() {
        makeRequest(current + 1, getTypeKey(), responseHandlerInterface);

    }

    public void makeRequest(int page, String type, ResponseHandlerInterface handlerInterface) {
        NetKit.getInstance().getNewslistByPage(page, type, handlerInterface);
    }
}
