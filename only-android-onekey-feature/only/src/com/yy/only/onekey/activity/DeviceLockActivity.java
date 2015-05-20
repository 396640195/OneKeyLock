package com.yy.only.onekey.activity;

import android.os.Bundle;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class DeviceLockActivity extends BasicActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLockManager.lockDevice(this);
        finish();
    }
}
