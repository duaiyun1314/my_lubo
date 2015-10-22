package com.example.MyWeibo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;

import com.example.MyWeibo.fragments.SlideMenuFragment;


/**
 * 主界面
 */
public class MainActivity extends ActionBarCastActivity implements SlideMenuFragment.NavigationDrawerCallbacks {
    private SlideMenuFragment mSlideMenuFragment;
    private int mCurrentPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSlideMenuFragment = (SlideMenuFragment) (getSupportFragmentManager().findFragmentById(R.id.navigation_drawer));
        //初始化drawerlayout
        mSlideMenuFragment.setup(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected int getBasicContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onNavigationItemsSelected(Fragment fragment, int pos) {
        if (fragment != null && mCurrentPos != pos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            mCurrentPos = pos;
        }
    }
}
