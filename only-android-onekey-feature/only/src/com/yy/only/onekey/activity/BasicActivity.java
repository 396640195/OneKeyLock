package com.yy.only.onekey.activity;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;
import com.yy.only.onekey.utils.LockManager;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class BasicActivity extends Activity {
    protected static LockManager mLockManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLockManager = LockManager.getInstance();
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
