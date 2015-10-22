package com.example.MyWeibo;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.MyWeibo.lib.ThemeManger;
import com.example.MyWeibo.weight.TranslucentStatus.TranslucentStatusHelper;

/**
 * Created by wanglu on 15-3-23.
 */
public class ActionBarCastActivity extends AppCompatActivity {
    protected TranslucentStatusHelper helper;
    protected TranslucentStatusHelper.Option option;
    protected int colorPrimary;
    protected int colorPrimaryDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置主题
        ThemeManger.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        super.setContentView(getBasicContentLayout());
        helper = TranslucentStatusHelper.from(this)
                .setStatusView(findViewById(R.id.statusView))
                .setActionBarSizeAttr(R.attr.actionBarSize)
                .setTranslucentProxy(TranslucentStatusHelper.TranslucentProxy.STATUS_BAR)
                .builder();
        //初始化ToolBar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        TypedArray array = obtainStyledAttributes(new int[]{R.attr.colorPrimary, R.attr.colorPrimaryDark, R.attr.colorAccent});
        colorPrimary = array.getColor(0, getResources().getColor(R.color.toolbarColor));
        colorPrimaryDark = array.getColor(1, getResources().getColor(R.color.statusColor));
        array.recycle();
        option = new TranslucentStatusHelper.Option()
                .setStatusColor(colorPrimaryDark)
                .setInsertProxy(TranslucentStatusHelper.InsertProxy.NONE);
        helper.setOption(option);
    }

    protected int getBasicContentLayout() {
        return R.layout.activity_basetoolbar;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        helper.notifyConfigureChanged();
    }
}
