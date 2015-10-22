package com.example.MyWeibo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MyWeibo.R;
import com.example.MyWeibo.data.ListDataProvider;
import com.example.MyWeibo.processers.BaseProcesser;

/**
 * Created by wanglu on 15/6/16.
 */
public abstract class BaseListFragment<DataProvider extends ListDataProvider, Processer extends BaseProcesser> extends Fragment {
    protected Processer processer;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (processer == null) {
            processer = createProcesser(getProvider());
        }
        processer.setActivity((AppCompatActivity) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        processer.assumeView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        processer.loadData(true);
    }

    protected abstract Processer createProcesser(DataProvider provider);

    protected abstract DataProvider getProvider();

    @Override
    public void onResume() {
        super.onResume();
        processer.onResume();
    }
}
