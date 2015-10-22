package com.example.MyWeibo.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by wanglu on 15/5/25.
 */
public class FileUtils {
    public static final boolean isExistDataCache(Context context, String file) {
        boolean exist = false;
        File data = context.getFileStreamPath(file);
        if (data.exists()) {
            exist = true;
        }
        return exist;
    }
}
