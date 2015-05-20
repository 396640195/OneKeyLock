package com.yy.only.onekey.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import com.yy.only.onekey.utils.Const;
import com.yy.only.onekey.utils.PreferenceHelper;

public class AdminReceiver extends DeviceAdminReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //多进程时，该代码已无效
        if (ACTION_DEVICE_ADMIN_ENABLED.equals(action)) {
            PreferenceHelper.putBoolean(Const.KEY_OF_DEVICE_PERMISSION, true);
            onEnabled(context, intent);
        } else if (ACTION_DEVICE_ADMIN_DISABLE_REQUESTED.equals(action)) {
            PreferenceHelper.putBoolean(Const.KEY_OF_DEVICE_PERMISSION,false);
        } else if (ACTION_DEVICE_ADMIN_DISABLED.equals(action)) {
            PreferenceHelper.putBoolean(Const.KEY_OF_DEVICE_PERMISSION,false);
            onDisabled(context, intent);
        }
    }
}
