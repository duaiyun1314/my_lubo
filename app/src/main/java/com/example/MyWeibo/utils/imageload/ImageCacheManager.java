package com.example.MyWeibo.utils.imageload;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.example.MyWeibo.LuBoApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wanglu on 15/9/11.
 */
public class ImageCacheManager implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> lruCache;
    private DiskLruCache mDiskLruCache;
    private Context context;

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        try {
            String key = hashKeyForDisk(url);
            bitmap = lruCache.get(url);
            if (bitmap == null) {
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                if (snapshot != null) {
                    InputStream inputStream = snapshot.getInputStream(0);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    //Log.i("wanglu1", "fromDiskCache:" + url);
                }
            } else {
                // Log.i("wanglu1", "fromLruCache:" + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        String key = hashKeyForDisk(url);
        lruCache.put(url, bitmap);
        try {
            if (mDiskLruCache.get(key) == null) {
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream)) {
                        editor.commit();
                    }
                } else {
                    editor.abort();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ImageCacheManager(int maxSize, String folderName, int maxDiaCacheSize) {
        context = LuBoApplication.getInstance();
        initLruCache(maxSize);
        initDiskCache(folderName, maxDiaCacheSize);
        Log.i("wanglu", "缓存容量:" + maxSize / 1024 / 1024);
    }

    private void initDiskCache(String folderName, int maxSize) {
        try {
            mDiskLruCache = DiskLruCache.open(getDiskCacheDir(context, folderName), getVersionCode(), 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getVersionCode() {
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }

    }

    private void initLruCache(int maxSize) {
        if (lruCache == null) {
            lruCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        return value.getByteCount();
                    }
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }
    }

    public static File getDiskCacheDir(Context context, String folderName) {
        String cachePath;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        Log.i("wanglu", "cachePaht:" + cachePath);

        return new File(cachePath + File.separator + folderName);
    }

    /**
     * 获取url的md5值
     *
     * @param url
     * @return
     */
    private String hashKeyForDisk(String url) {
        String cacheKey;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            cacheKey = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] digest) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte i : digest) {
            String hex = Integer.toHexString(0xFF & i);
            if (hex.length() == 1) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hex);

        }
        return stringBuilder.toString();
    }
}
