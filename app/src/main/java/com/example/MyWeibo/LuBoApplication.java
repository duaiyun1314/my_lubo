package com.example.MyWeibo;

import android.app.Application;
import android.content.Context;

import com.example.MyWeibo.utils.imageload.ImageLoaderUtil;
import com.example.dbutil.DaoMaster;
import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by wanglu on 15-3-12.
 */
public class LuBoApplication extends Application {
    private static LuBoApplication mInstance;
    private DaoMaster.DevOpenHelper mHelper;

    public static LuBoApplication from(Context context) {
        return (LuBoApplication) context.getApplicationContext();
    }

    public static LuBoApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Fresco.initialize(this);
        initDb();
        ImageLoaderUtil.initImageLoader(getApplicationContext());

    }

    private void initDb() {
        mHelper = new DaoMaster.DevOpenHelper(this, "lubo", null);
    }

    public DaoMaster.DevOpenHelper getHelper() {
        return mHelper;
    }
}
