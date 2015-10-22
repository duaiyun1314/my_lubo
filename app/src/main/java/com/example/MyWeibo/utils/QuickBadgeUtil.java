package com.example.MyWeibo.utils;

import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by wanglu on 15/5/11.
 */
public class QuickBadgeUtil {
    private static final String MULTI_BLANK = "[ ]+";

    public static String setContactBadgeText(String source) {
        if (TextUtils.isEmpty(source)) {
            return "...";
        } else {
            String text = source.trim();
            if (TextUtils.isEmpty(text)) {
                return "...";
            } else {
                return getName(source);
            }
        }
    }

    private static String getName(String name) {
        int len = name.length();
        char c;
        for (int i = len - 1; i >= 0; i--) {
            c = name.charAt(i);
            if (isChinese(c)) {
                return "" + c;
            }
        }

        String firstLetter = name.substring(0, 1);
        c = firstLetter.charAt(0);
        if (Character.isLetter(c)) {
            String[] subString = name.split(MULTI_BLANK);
            firstLetter = firstLetter.toUpperCase(Locale.getDefault());
            int length = subString.length;
            if (length >= 2) {
                String lastLetter = subString[1].substring(0, 1);
                c = lastLetter.charAt(0);
                if (Character.isLetter(c)) {
                    lastLetter = lastLetter.toUpperCase(Locale.getDefault());
                    return firstLetter + lastLetter;
                } else
                    return firstLetter;

            } else {
                return firstLetter;
            }
        } else {
            return "";
        }
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
            //|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            //|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            //|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
