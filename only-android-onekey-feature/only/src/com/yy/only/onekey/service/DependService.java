package com.yy.only.onekey.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yy.only.onekey.common.OneKeyLockApplication;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class DependService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        OneKeyLockApplication.getFloatLockWindow().show();
        return super.onStartCommand(intent, START_STICKY, startId);
    }
}
