package com.example.MyWeibo.weight.viewpagerIndicator;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.MyWeibo.R;

/**
 * Created by Andy.Wang on 2015/11/2.
 */
public class MyPagerIndicator extends HorizontalScrollView {
    private ViewPager mViewPager;
    private boolean mDistributeEvenly = true;
    private TabStrip mTabStrip;
    private int mTitleOffset;


    private static final int TAB_VIEW_TEXT_SIZE = 12;
    private static final int TAB_VIEW_PADDING_DIPS = 12;
    private static final int TITLE_OFFSET_DIPS = 24;

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

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mTabStrip = new TabStrip(getContext());
        addView(mTabStrip, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        TabClickListener tabClickListener = new TabClickListener();
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
            tabTitleView.setOnClickListener(tabClickListener);
            mTabStrip.addView(tabTitleView);
            if (i == mViewPager.getCurrentItem()) {
                tabTitleView.setSelected(true);
            }
        }
    }

    private TextView createDefaultTabTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(0, padding, 0, padding);
        textView.setMinWidth(300);
        textView.setTextColor(getResources().getColorStateList(R.color.tab_selector));
        return textView;

    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mTabStrip.onPagerChanged(position, positionOffset);
            View selectTitle = mTabStrip.getChildAt(position);
            int offset = selectTitle != null ? (int) (selectTitle.getWidth() * positionOffset) : 0;
            scrollTabTo(position, offset);

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                mTabStrip.getChildAt(i).setSelected(position == i);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void scrollTabTo(int titleIndex, int positionOffset) {
        View selectTitle = mTabStrip.getChildAt(titleIndex);
        if (selectTitle != null) {
            int scrollX = selectTitle.getLeft() + positionOffset;
            if (titleIndex > 0 || positionOffset > 0) {
                scrollX -= mTitleOffset;

            }
            scrollTo(scrollX, 0);
        }

    }


    private class TabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }

        }
    }

}
