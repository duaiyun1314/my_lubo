package com.example.MyWeibo.weight.viewpagerIndicator;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Andy.Wang on 2015/11/2.
 */
public class MyPagerIndicator extends HorizontalScrollView {
    private ViewPager mViewPager;
    private boolean mDistributeEvenly = true;
    private TabStrip mTabStrip;

    private static final int TAB_VIEW_TEXT_SIZE = 12;
    private static final int TAB_VIEW_PADDING_DIPS = 12;

    public MyPagerIndicator(Context context) {
        super(context);
        init();
    }

    public MyPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //不显示scroll bar
        setHorizontalScrollBarEnabled(false);
        //保证子view填充整个horizontalScrollview
        setFillViewport(true);

        mTabStrip = new TabStrip(getContext());
        addView(mTabStrip, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            throw new IllegalArgumentException("viewpager con not be null");
        }
        this.mViewPager = viewPager;
        viewPager.setOnPageChangeListener(new InternalViewPagerListener());
        populateTabStrip();
    }

    private void populateTabStrip() {
        mTabStrip.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter == null) throw new IllegalArgumentException("hasn't set adapter for viewpager");
        for (int i = 0; i < adapter.getCount(); i++) {
            TextView tabTitleView = null;
            if (tabTitleView == null) {
                tabTitleView = createDefaultTabTextView(getContext());
            }
            if (mDistributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabTitleView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }
            tabTitleView.setText(adapter.getPageTitle(i));
            mTabStrip.addView(tabTitleView);
        }
    }

    private TextView createDefaultTabTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(0, padding, 0, padding);
        return textView;

    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mTabStrip.onPagerChanged(position, positionOffsetPixels);

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
