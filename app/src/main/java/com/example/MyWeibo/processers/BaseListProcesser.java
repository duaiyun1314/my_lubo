package com.example.MyWeibo.processers;

import android.graphics.Color;
import android.media.Image;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MyWeibo.R;
import com.example.MyWeibo.data.BaseDataProvider;
import com.example.MyWeibo.data.ListDataProvider;
import com.example.MyWeibo.lib.kits.PrefKit;
import com.example.MyWeibo.lib.kits.ToolKit;
import com.example.MyWeibo.utils.imageload.ImageLoaderUtil;
import com.example.MyWeibo.weight.PagedLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;


/**
 * Created by wanglu on 15/6/16.
 */
public class BaseListProcesser<DataProvider extends ListDataProvider> extends BaseProcesserImpl<DataProvider> implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private SwipeRefreshLayout mSwipeLayout;
    private TextView headView;
    private PagedLoader loader;

    public BaseListProcesser(DataProvider provider) {
        super(provider);
    }

    @Override
    public void assumeView(View view) {
        this.listView = (ListView) view.findViewById(android.R.id.list);
        this.mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        this.mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        this.mSwipeLayout.setOnRefreshListener(this);
        this.mSwipeLayout.setColorSchemeColors(colorPrimary, colorPrimaryDark, colorAccent);
        this.headView = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.type_head, listView, false);
        this.headView.setText("类型：" + mProvider.getTypeName());
        this.listView.addHeaderView(headView, null, false);
        PagedLoader.OnLoadListener onLoadListener = new PagedLoader.OnLoadListener() {
            @Override
            public void onLoading(PagedLoader pagedLoader, boolean isAutoLoad) {
                mProvider.loadNextData();
            }
        };
        this.loader = PagedLoader.from(listView).setFinallyText("--END").setOnLoadListener(onLoadListener).builder();
        this.loader.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        this.loader.setAdatper(this.mProvider.getAdapter());
    }

    @Override
    public void setProvider(BaseDataProvider provider) {

    }


    @Override
    public void onRefresh() {
        mProvider.loadNewData();
    }

    @Override
    public void loadData(boolean startup) {
        ToolKit.runInUIThread(new Runnable() {
            @Override
            public void run() {
                //加载缓存数据
                mProvider.loadData();
                //如果没有缓存数据或者设置为首次启动自动加载
                if (!mProvider.isCached() || PrefKit.getBoolean(getActivity(), mActivity.getString(R.string.pref_auto_reflush_key), false)) {
                    mSwipeLayout.setRefreshing(true);
                    onRefresh();
                }
            }
        }, startup ? 260 : 0);
    }

    @Override
    public void onResume() {
        if (PrefKit.getBoolean(mActivity, mActivity.getString(R.string.pref_auto_page_key), true)) {
            this.loader.setMode(PagedLoader.Mode.AUTO_LOAD);
        } else {
            this.loader.setMode(PagedLoader.Mode.CLICK_TO_LOAD);
        }
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadSuccess(Object object) {

    }

    @Override
    public void onLoadFinish(int size) {
        mProvider.getAdapter().notifyDataSetChanged();
        if (mProvider.getAdapter().getCount() < mProvider.getPageSize() || size == 0) {
            loader.setFinally();
        } else {
            loader.setLoading(false);
        }
        if (mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoadFailure() {

    }
}
