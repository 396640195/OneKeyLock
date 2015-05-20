package com.yy.only.onekey.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.yy.only.onekey.common.OneKeyLockApplication;


public class PreferenceHelper {

    public static SharedPreferences getDefaultPreferences() {
        return OneKeyLockApplication.getDefaultApplication().getSharedPreferences("share.xml", Activity.MODE_MULTI_PROCESS);
    }


    public static boolean putString(String key, String value) {
        SharedPreferences sp = getDefaultPreferences();
        Editor editor = sp.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean putInt(String key, int value) {
        SharedPreferences sp = getDefaultPreferences();
        Editor editor = sp.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean putLong(String key, long value) {
        SharedPreferences sp = getDefaultPreferences();
        Editor editor = sp.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean putBoolean(String key, boolean value) {
        SharedPreferences sp = getDefaultPreferences();
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sp = getDefaultPreferences();
        return sp.getString(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences sp = getDefaultPreferences();
        return sp.getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        SharedPreferences sp = getDefaultPreferences();
        return sp.getLong(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = getDefaultPreferences();
        return sp.getBoolean(key, defValue);
    }

    public static boolean contains(String key) {
        SharedPreferences sp = getDefaultPreferences();
        return sp.contains(key);
    }

    public static boolean putDefaultBoolean(String key, boolean value) {
        SharedPreferences sp = getDefaultPreferences();
        if (!sp.contains(key)) {
            return putBoolean(key, value);
        }
        return false;
    }

}
