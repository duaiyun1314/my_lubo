package com.example.MyWeibo.utils.imageload;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.MyWeibo.LuBoApplication;

/**
 * 图片加载类
 */
public class ImageLoaderUtil {
    private static final int maxMemory = 1024 * 1024 * ((ActivityManager) LuBoApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;
    private static final int maxDiskSize = 10 * 1024 * 1024;
    private static ImageCacheManager imageCacheManager = new ImageCacheManager(maxMemory, "luboImages", maxDiskSize);
    private static RequestQueue mRequestQueue = Volley.newRequestQueue(LuBoApplication.getInstance());
    private static ImageLoader mImageLoader = new ImageLoader(mRequestQueue, imageCacheManager);

    public static void loadImage(String url, ImageView imageView, int defaultResId, int errorResId) {

        if (url == null || TextUtils.isEmpty(url)) {
            return;
        } else {
            //重置imageview
            imageView.setImageResource(defaultResId);
        }
        //url = "http://static.cnbetacdn.com/thumb/mini/article/2015/0911/2f1c0f82ce27242_100x100.jpg";
        mImageLoader.get(url, ImageLoader.getImageListener(imageView, defaultResId, errorResId));

    }

    public static void stop() {
        mRequestQueue.stop();
    }

    public static void start() {
        mRequestQueue.start();
    }

}
