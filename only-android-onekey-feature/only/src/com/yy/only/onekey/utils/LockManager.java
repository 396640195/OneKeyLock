package com.yy.only.onekey.utils;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yy.only.onekey.R;
import com.yy.only.onekey.activity.LockSettingActivity;
import com.yy.only.onekey.common.OneKeyLockApplication;
import com.yy.only.onekey.receiver.AdminReceiver;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public class LockManager {
    public static final int REQUEST_OPEN_LOCK_DEVICE=101;
    private DevicePolicyManager mPolicyManager;
    private ComponentName mComponentName;
    private static LockManager sLockManager;
    public static LockManager getInstance(){
        if(sLockManager == null){
            sLockManager = new LockManager();
        }
        return  sLockManager;
    }

    public LockManager(){
        mPolicyManager = (DevicePolicyManager) OneKeyLockApplication.getDefaultApplication().getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(OneKeyLockApplication.getDefaultApplication(), AdminReceiver.class);
    }

    public void removeDeviceManager(){
        mPolicyManager.removeActiveAdmin(mComponentName);
    }
    //获取权限
    public void activeDeviceManager(Activity activity)
    {
        // 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);

        //描述(additional explanation)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, activity.getString(R.string.please_activate_device));

        activity.startActivityForResult(intent, REQUEST_OPEN_LOCK_DEVICE);
    }

    public boolean onActivityResult(Activity activity,int requestCode, int resultCode, Intent data)
    {
        //获取权限成功，立即锁屏并finish自己，否则继续获取权限
        if (requestCode == REQUEST_OPEN_LOCK_DEVICE && resultCode == Activity.RESULT_OK)
        {
            return true;
        }else{
        }
        return false;
    }

    public void lockDevice(Context context){
        boolean hasPermission = PreferenceHelper.getBoolean(Const.KEY_OF_DEVICE_PERMISSION, false);
        if(!hasPermission)
        {
            Intent intent = new Intent();
            intent.setClass(context,LockSettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            if(!(context instanceof Activity)){
                Toast.makeText(context,R.string.enable_one_key_lock_hint,Toast.LENGTH_LONG).show();
            }
        }else{
            mPolicyManager.lockNow();
        }
    }
}
