package com.yy.only.onekey.common;

import android.app.Application;
import android.content.Intent;
import android.view.WindowManager;

import com.yy.only.onekey.dialog.FloatLockWindow;
import com.yy.only.onekey.service.DependService;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public class OneKeyLockApplication extends Application {

    private static OneKeyLockApplication mOneKeyLockApplication;
    private static WindowManager.LayoutParams sWindowManagerParams;
    public  static OneKeyLockApplication getDefaultApplication() {
        return mOneKeyLockApplication;
    }
    private static FloatLockWindow sFloatLockWindow;
    @Override
    public void onCreate() {
        super.onCreate();
        mOneKeyLockApplication = this;
        sWindowManagerParams = new WindowManager.LayoutParams();
        sFloatLockWindow = FloatLockWindow.getInstance();
        this.startDependService();
    }

    private void startDependService(){
        Intent intent = new Intent();
        intent.setClass(this, DependService.class);
        startService(intent);
    }
    public static WindowManager.LayoutParams getWindowLayoutParams(){
        return  sWindowManagerParams;
    }

    public static FloatLockWindow getFloatLockWindow(){
        return  sFloatLockWindow;
    }
}
