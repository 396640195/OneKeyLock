package com.yy.only.onekey.utils;

import android.text.TextUtils;
import android.util.Log;


public class YLog {

    private static boolean enable = true;

    public static String getLogTag() {
        return "Only";
    }

    public static void i(String msg) {
        if (enable == false) {
            return;
        }
        Log.i(getLogTag(), TextUtils.isEmpty(msg) ? "" : msg);
    }

    public static void e(String msg) {
        if (enable == false) {
            return;
        }
        Log.e(getLogTag(), TextUtils.isEmpty(msg) ? "" : msg);
    }

    public static void w(String msg) {
        if (enable == false) {
            return;
        }
        Log.w(getLogTag(), TextUtils.isEmpty(msg) ? "" : msg);
    }

    public static void d(String msg) {
        if (enable == false) {
            return;
        }
        Log.d(getLogTag(), TextUtils.isEmpty(msg) ? "" : msg);
    }

    public static void v(String msg) {
        if (enable == false) {
            return;
        }
        Log.v(getLogTag(), TextUtils.isEmpty(msg) ? "" : msg);
    }

}
