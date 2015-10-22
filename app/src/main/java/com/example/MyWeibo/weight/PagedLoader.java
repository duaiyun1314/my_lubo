package com.example.MyWeibo.weight;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MyWeibo.R;
import com.pnikosis.materialishprogress.ProgressWheel;


/**
 * Created by wanglu on 15/6/18.
 */
public class PagedLoader extends DataSetObserver implements View.OnClickListener, AbsListView.OnScrollListener {

    private ListView listView;
    private Mode mode = Mode.AUTO_LOAD;
    // ListView底部View
    private View moreView;
    private TextView normalTextView;
    private TextView finallyTextView;
    private ProgressWheel progressBar;
    private OnLoadListener mOnLoadListener;
    private AbsListView.OnScrollListener mOnScrollListener;
    private boolean enable;
    private ListAdapter mAdapter;

    /**
     * 最后一条可视条目的索引
     */
    private int lastVisibleIndex;

    /**
     * 正在加载
     *
     * @param listview
     * @return
     */
    private boolean isLoading;

    public static Builder from(ListView listview) {
        return new Builder(listview);
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        setDisplay(enable);
    }

    public void setAdatper(ListAdapter adatper) {
        if (this.mAdapter == null && adatper == null) {
            throw new RuntimeException("adapter must be null");
        }
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this);
        }
        this.mAdapter = adatper;
        //监听adapter data 来控制footview的显隐
        this.mAdapter.registerDataSetObserver(this);
        this.listView.setAdapter(mAdapter);
        bindEvent();
    }

    @Override
    public void onChanged() {
        if (mAdapter == null) {
            throw new RuntimeException("adapter must not be null");
        }
        bindEvent();
    }

    private void bindEvent() {
        if (mAdapter.getCount() == 0) {
            setEnable(false);
        } else {
            if (mOnLoadListener == null) {
                setEnable(false);
            } else {
                setEnable(true);
            }
        }
    }


    private void setDisplay(boolean show) {
        if (show) {
            moreView.setVisibility(View.VISIBLE);
        } else {
            moreView.setVisibility(View.GONE);
        }
    }

    /**
     * 暴露正在加载的接口，方便让加载器加载下一页数据
     *
     * @param mOnLoadListener
     */
    public void setOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    /**
     * 暴露正在滚动的接口，方便知道滚动状态，控制图片的加载
     *
     * @param mOnScrollListener
     */
    public void setOnScrollListener(AbsListView.OnScrollListener mOnScrollListener) {
        this.mOnScrollListener = mOnScrollListener;
    }

    public void setFinallyText(CharSequence text) {
        finallyTextView.setText(text);
    }

    public void setFinallyText(int res) {
        finallyTextView.setText(res);
    }

    public void setNormalText(CharSequence title) {
        normalTextView.setText(title);
    }

    public void setNormalText(int res) {
        normalTextView.setText(res);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void onClick(View view) {
        if (enable && mode == Mode.CLICK_TO_LOAD) {
            setLoading(true);
            mOnLoadListener.onLoading(this, false);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        //到达最后一条 并且是自动加载模式  调用接口方法
        //如果有listview增加了headview 需要调用getAdapter().getCount();用自己的adapter得到的count是不正确的，headview占position 0 。
        if (isEnable()/**footview enable*/ && getMode() == Mode.AUTO_LOAD && !isLoading && lastVisibleIndex == listView.getAdapter().getCount() &&
                i == SCROLL_STATE_IDLE) {
            setLoading(true);
            mOnLoadListener.onLoading(this, true);
        }

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(absListView, i);
        }
    }

    public void setLoading(boolean isloading) {
        if (isEnable()) {
            this.isLoading = isloading;
            if (isloading) {
                progressBar.spin();
                progressBar.setVisibility(View.VISIBLE);
                normalTextView.setVisibility(View.GONE);
            } else {
                progressBar.stopSpinning();
                progressBar.setVisibility(View.GONE);
                normalTextView.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

        lastVisibleIndex = firstVisibleItem + visibleItemCount;
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    public void setFinally() {
        setFinally(true);
    }

    private void setFinally(boolean isFinall) {
        if (isFinall) {
            setLoading(false, true);
            enable = false;
        } else {
            setLoading(false, false);
            enable = true;
        }
    }

    private void setLoading(boolean isLoading, boolean isFinall) {
        setLoading(isLoading);
        if (isFinall) {
            normalTextView.setVisibility(View.GONE);
            finallyTextView.setVisibility(View.VISIBLE);
        } else {
            normalTextView.setVisibility(View.VISIBLE);
            finallyTextView.setVisibility(View.GONE);
        }
    }

    public enum Mode {
        CLICK_TO_LOAD, AUTO_LOAD
    }

    public interface OnLoadListener {
        void onLoading(PagedLoader pagedLoader, boolean isAutoLoad);
    }

    public static class Builder {
        private PagedLoader pagedLoader;
        private Context mContext;

        private Builder(ListView listView) {
            mContext = listView.getContext();
            pagedLoader = new PagedLoader();
            pagedLoader.listView = listView;
            //初始化底部布局
            pagedLoader.moreView = LayoutInflater.from(mContext).inflate(R.layout.paged_foot,
                    pagedLoader.listView, false);
            pagedLoader.normalTextView = (TextView) pagedLoader.moreView.findViewById(R.id.bt_load);
            pagedLoader.finallyTextView = (TextView) pagedLoader.moreView.findViewById(R.id.bt_finally);
            pagedLoader.progressBar = (ProgressWheel) pagedLoader.moreView.findViewById(R.id.pg);

        }

        public Builder setMode(Mode mode) {
            pagedLoader.setMode(mode);
            return this;
        }

        public Builder setNormalText(CharSequence text) {
            pagedLoader.setNormalText(text);
            return this;
        }

        public Builder setNormalText(int res) {
            pagedLoader.setNormalText(res);
            return this;
        }

        public Builder setFinallyText(CharSequence text) {
            pagedLoader.setFinallyText(text);
            return this;
        }

        public Builder setFinallyText(int res) {
            pagedLoader.setFinallyText(res);
            return this;
        }

        public Builder setOnLoadListener(OnLoadListener mOnLoadListener) {
            pagedLoader.setOnLoadListener(mOnLoadListener);
            return this;
        }

        public Builder setOnScrollListener(AbsListView.OnScrollListener mOnScrollListener) {
            pagedLoader.setOnScrollListener(mOnScrollListener);
            return this;
        }

        public PagedLoader builder() {
            if (pagedLoader.listView.getAdapter() != null) {
                throw new RuntimeException("must set footview before setAdapter()");
            }
            //设置footview
            pagedLoader.listView.addFooterView(pagedLoader.moreView, null, false);
            pagedLoader.listView.setFooterDividersEnabled(false);
            pagedLoader.normalTextView.setOnClickListener(pagedLoader);
            //此时处于onCreateView   mode还是初始化值
            if (pagedLoader.mode == Mode.AUTO_LOAD) {
                pagedLoader.listView.setOnScrollListener(pagedLoader);
            }
            //开始默认不显示footview
            pagedLoader.setEnable(false);
            return pagedLoader;
        }
    }
}
