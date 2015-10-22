package com.example.MyWeibo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


/**
 * Created by wanglu on 15/5/14.
 */
public class SettingsActivity extends ActionBarCastActivity {
    private static final String HAS_THEME_CHANGED = "has_theme_changed";

    private boolean mHasThemeChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHasThemeChanged = getIntent().getBooleanExtra(HAS_THEME_CHANGED, false);
        setContentView(R.layout.activity_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mHasThemeChanged) {
            Intent intent = new Intent();
            overridePendingTransition(0,0);
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
