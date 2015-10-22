package com.example.MyWeibo.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by wanglu on 15/4/24.
 */
public class LuBoCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> mMemoryCache;
    private static LuBoCache mCache;

    private LuBoCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };

    }

    public static LuBoCache getInstance() {
        if (mCache == null) {
            mCache = new LuBoCache();
        }
        return mCache;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }
}
