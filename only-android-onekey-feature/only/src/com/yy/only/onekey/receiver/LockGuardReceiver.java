package com.yy.only.onekey.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yy.only.onekey.service.DependService;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class LockGuardReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, DependService.class);
        context.startService(i);
    }
}
