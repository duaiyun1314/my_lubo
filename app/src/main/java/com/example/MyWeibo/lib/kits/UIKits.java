package com.example.MyWeibo.lib.kits;

import android.content.res.ColorStateList;

/**
 * Created by wanglu on 15/6/15.
 */
public class UIKits {
    public static ColorStateList createStateList(int normal, int actived) {
        int colors[] = new int[]{actived, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_activated};
        states[1] = new int[]{};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        return colorStateList;
    }
}
