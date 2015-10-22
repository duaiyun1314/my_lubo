package com.example.MyWeibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wanglu on 15/6/19.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected List<T> items;
    protected Context context;
    private LayoutInflater inflater;

    public BaseAdapter(Context context, List<T> items) {
        this(context);
        this.items = items;

    }

    public BaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return bindViewAndData(inflater, i, view, viewGroup);
    }

    public List<T> getDataSet() {
        return items;
    }

    public void setDataSet(List<T> dataset) {
        this.items = dataset;
    }

    public T getDataSetItem(int postion) {
        return items.get(postion);
    }

    protected abstract View bindViewAndData(LayoutInflater infater, int position, View convertView, ViewGroup parent);

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
