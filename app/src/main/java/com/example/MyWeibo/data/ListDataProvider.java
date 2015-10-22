package com.example.MyWeibo.data;

import android.app.Activity;

import com.example.MyWeibo.adapter.BaseAdapter;

import java.util.List;

/**
 * Created by wanglu on 15/6/16.
 */
public abstract class ListDataProvider<DataType, DataAdapter extends BaseAdapter<DataType>> extends BaseDataProvider<List<DataType>> {
    private DataAdapter adapter;
    protected boolean hasCached;
    protected int pageSize;
    protected int minPageSize;


    public ListDataProvider(Activity activity) {

        super(activity);
    }

    public DataAdapter getAdapter() {
        if (adapter == null) {
            adapter = newAdapter();
        }
        return adapter;
    }

    public abstract String getTypeKey();

    public abstract String getTypeName();

    protected abstract DataAdapter newAdapter();

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
