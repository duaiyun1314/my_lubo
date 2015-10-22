package com.example.MyWeibo.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.MyWeibo.R;
import com.example.MyWeibo.lib.kits.PrefKit;


/**
 * 管理主题
 * <p/>
 * Created by wanglu.
 */
public class ThemeManger {

    private static int[][] ThemeRes = {{
            R.style.Theme_Basic,
            R.style.Theme_Basic_NoAnim
    }, {
            R.style.Theme_Basic_TEAL,
            R.style.Theme_Basic_TEAL_NoAnim
    }, {
            R.style.Theme_Basic_BROWN,
            R.style.Theme_Basic_BROWN_NoAnim
    }, {
            R.style.Theme_Basic_ORANGE,
            R.style.Theme_Basic_ORANGE_NoAnim
    }, {
            R.style.Theme_Basic_PURPLE,
            R.style.Theme_Basic_PURPLE_NoAnim
    }, {
            R.style.Theme_Basic_GREEN,
            R.style.Theme_Basic_GREEN_NoAnim
    }, {
            R.style.Theme_Basic_Night,
            R.style.Theme_Basic_Night_NoAnim
    }};

    public static void changeToTheme(Activity activity, int theme) {
        changeToTheme(activity, null, theme);
    }


    /**
     * Set the theme of the Activity, and restart it by creating a new Activity
     * of the same type.
     */
    public static void changeToTheme(Activity activity, Bundle saveData, int theme) {
        PrefKit.writeInt(activity, "theme", theme);
        activity.finish();
        Intent intent = new Intent(activity, activity.getClass());
        if (saveData != null) {
            intent.putExtras(saveData);
        }
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.startActivity(intent);
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        int[] theme = ThemeRes[PrefKit.getInt(activity, "theme", 0)];
        // if (activity instanceof ExtendBaseActivity || activity instanceof ImageViewActivity) {
        if (false) {
            activity.setTheme(theme[0]);
        } else {
            activity.setTheme(theme[1]);
        }
    }

    public static int getCurrentTheme(Context context) {
        return PrefKit.getInt(context, "theme", 0);
    }

    public static boolean isNightTheme(Context context) {
        return PrefKit.getInt(context, "theme", 0) == ThemeRes.length - 1;
    }
}
