package com.yy.only.onekey.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class ToastManager {
    private static ToastManager sInstance;

    private Toast mLastToast;

    public static ToastManager getInstance() {
        if (sInstance == null) {
            sInstance = new ToastManager();
        }
        return sInstance;
    }

    public void showToast(Context context, int resId, int length) {
        if (mLastToast != null) {
            mLastToast.cancel();
            mLastToast = null;
        }
        mLastToast = Toast.makeText(context, resId, length);
        mLastToast.show();
    }

    public void showToast(Context context, String text, int length) {
        if (mLastToast != null) {
            mLastToast.cancel();
            mLastToast = null;
        }
        mLastToast = Toast.makeText(context, text, length);
        mLastToast.show();
    }
}
