package com.example.MyWeibo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MyWeibo.R;
import com.example.MyWeibo.weight.viewpagerIndicator.MyPagerIndicator;

/**
 * 资讯主题
 */
public class SubscribeHostFragment extends Fragment {
    private MyPagerIndicator myPagerIndicator;
    private ViewPager mViewPager;
    private Fragment[] fragments = new Fragment[]{new SubscribedFragment(), new AllSubscribeFragment()};
    private String[] titles = new String[]{"\u5df2\u5173\u6ce8", "\u53ef\u5173\u6ce8"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribehost, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.subscribe_host_vp);
        myPagerIndicator = (MyPagerIndicator) view.findViewById(R.id.viewpager_indicator);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setAdapter(new NavigatorTabAdapter(getChildFragmentManager()));
        myPagerIndicator.setViewPager(mViewPager);
    }

    private class NavigatorTabAdapter extends FragmentPagerAdapter {
        public NavigatorTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position].toString();
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
