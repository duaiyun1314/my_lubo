package com.example.MyWeibo;

import android.app.Application;
import android.content.Context;

import com.example.MyWeibo.utils.imageload.ImageCacheManager;
import com.example.dbutil.DaoMaster;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;


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
        initImageLoader(getApplicationContext());

    }

    private void initDb() {
        mHelper = new DaoMaster.DevOpenHelper(this, "lubo", null);
    }

    public DaoMaster.DevOpenHelper getHelper() {
        return mHelper;
    }

    public void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                        // .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                        // .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .diskCacheFileCount(5)
                .diskCache(getLruDiskCache())
                .tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoaderConfiguration config = builder.build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    private LruDiskCache getLruDiskCache() {
        LruDiskCache lruDiskCache = null;
        try {
            File file = ImageCacheManager.getDiskCacheDir(getApplicationContext(), "newsImages");
            lruDiskCache = new LruDiskCache(file
                    , new Md5FileNameGenerator(), 50 * 1024 * 1024);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lruDiskCache;
    }
}
