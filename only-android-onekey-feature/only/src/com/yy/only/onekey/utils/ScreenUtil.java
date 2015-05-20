package com.yy.only.onekey.utils;

import android.util.DisplayMetrics;

import com.yy.only.onekey.common.OneKeyLockApplication;


public class ScreenUtil {
    public static int getScreenWidthPx() {
        DisplayMetrics metrics = OneKeyLockApplication.getDefaultApplication().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeightPx() {
        DisplayMetrics metrics = OneKeyLockApplication.getDefaultApplication().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int px2dp(float px) {
        float density = OneKeyLockApplication.getDefaultApplication().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    public static int dp2px(float dp) {
        float density = OneKeyLockApplication.getDefaultApplication().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public static int getScreenWidthDp() {
        float width = OneKeyLockApplication.getDefaultApplication().getResources().getDisplayMetrics().widthPixels;
        return px2dp(width);

    }

    public static int getScreenHeightDp() {
        float height = OneKeyLockApplication.getDefaultApplication().getResources().getDisplayMetrics().heightPixels;
        return px2dp(height);
    }

    public static int sp2px(float spValue) {
        final float fontScale = OneKeyLockApplication.getDefaultApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
