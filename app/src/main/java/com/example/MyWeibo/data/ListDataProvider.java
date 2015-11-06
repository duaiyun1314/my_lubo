package com.example.MyWeibo.data;

import android.app.Activity;

import com.example.MyWeibo.adapter.BaseAdapter;

import java.util.List;

/**
 * Created by wanglu on 15/6/16.
 */
public abstract class ListDataProvider extends BaseDataProvider {
    private BaseAdapter adapter;
    protected boolean hasCached;
    protected int pageSize;
    protected int minPageSize;


    public ListDataProvider(Activity activity) {

        super(activity);
    }

    public BaseAdapter getAdapter() {
        if (adapter == null) {
            adapter = newAdapter();
        }
        return adapter;
    }

    public abstract String getTypeKey();

    public abstract String getTypeName();

    protected abstract BaseAdapter newAdapter();

    /**
     * 第一次加载网络数据
     */
    public abstract void loadNewData();

    /**
     * 加载更多数据
     */
    public abstract void loadNextData();

    public boolean isCached() {
        return hasCached;
    }

    public int getMinPageSize() {
        return minPageSize;
    }

    public void setMinPageSize(int pageSize) {
        this.minPageSize = pageSize;
    }

    public void setPageSize(int pageSize) {
        this.minPageSize = this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }
}
