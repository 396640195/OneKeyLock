package com.yy.only.onekey.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yy.only.onekey.R;
import com.yy.only.onekey.common.OneKeyLockApplication;
import com.yy.only.onekey.dialog.ConfirmDialog;
import com.yy.only.onekey.dialog.FloatLockWindow;
import com.yy.only.onekey.download.DownloadListener;
import com.yy.only.onekey.download.DownloadManager;
import com.yy.only.onekey.utils.Const;
import com.yy.only.onekey.utils.PreferenceHelper;
import com.yy.only.onekey.utils.ToastManager;
import com.yy.only.onekey.view.ProgressView;

import java.io.File;
import java.text.DecimalFormat;


public class LockSettingActivity extends BasicActivity implements DownloadListener,View.OnClickListener {
    final DownloadManager mDownloadManager = new DownloadManager(this);
    private ProgressView mProgress;
    private View mProgressLayout;
    private View mTextHintLayout;
    private TextView mLockButton;
    private boolean isAlreadyDownload;
    private FloatLockWindow mFloatLockWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        this.initTopLayout();
        this.initDisableLockLayout();
        this.initOpenDevicePermissionLayout();
        this.initDownloadLayout();
        this.mFloatLockWindow = OneKeyLockApplication.getFloatLockWindow();
    }

    /**
     * 初始化开启一键锁屏权限布局及逻辑
     */
    private void initOpenDevicePermissionLayout(){
        final ImageView image = (ImageView)this.findViewById(R.id.open_device_permission);
        boolean hasPermission = PreferenceHelper.getBoolean(Const.KEY_OF_DEVICE_PERMISSION,false);
        image.setImageResource(hasPermission ? R.drawable.icon_open_lock : R.drawable.icon_close_lock);
        mLockButton = (TextView)this.findViewById(R.id.lock_screen_at_once);
        setButtonEnable(hasPermission);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasPermission = PreferenceHelper.getBoolean(Const.KEY_OF_DEVICE_PERMISSION,false);
                if(hasPermission == false) {
                    mLockManager.activeDeviceManager(LockSettingActivity.this);
                }else{
                    ConfirmDialog cfd = new ConfirmDialog(LockSettingActivity.this,LockSettingActivity.this);
                    cfd.setMessage(R.string.sure_to_disable_one_key_lock);
                    cfd.setTitle(R.string.disable_one_key_lock);
                    cfd.show();
                }
            }
        });

        mLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLockManager.lockDevice(LockSettingActivity.this);
                finish();
            }
        });
    }
    /**
     * 初始化悬浮窗开关
     */
    private void initTopLayout() {
        View view = this.findViewById(R.id.float_window_layout);
        final ImageView status = (ImageView) view.findViewById(R.id.image_switch_status);
        boolean open = PreferenceHelper.getBoolean(Const.KEY_OF_FLOAT_WINDOW_STATUS, false);
        status.setImageResource(open ? R.drawable.icon_switch_open : R.drawable.icon_switch_off);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean open = PreferenceHelper.getBoolean(Const.KEY_OF_FLOAT_WINDOW_STATUS, false);
                PreferenceHelper.putBoolean(Const.KEY_OF_FLOAT_WINDOW_STATUS, !open);
                status.setImageResource(!open ? R.drawable.icon_switch_open : R.drawable.icon_switch_off);
                if(!open){
                    mFloatLockWindow.show();
                }else{
                    mFloatLockWindow.dissmiss();
                }
            }
        });
    }

    /**
     * 禁用一键锁屏，改变标记状态
     */
    private void initDisableLockLayout(){
        this.findViewById(R.id.disable_lock_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog cfd = new ConfirmDialog(LockSettingActivity.this,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //通过程序的包名创建URL
                        Uri packageURI=Uri.parse("package:"+getPackageName());
                        //创建Intent意图
                        Intent intent=new Intent(Intent.ACTION_DELETE);
                        //设置Uri
                        intent.setData(packageURI);
                        //卸载程序
                        startActivity(intent);
                    }
                });
                cfd.setMessage(R.string.sure_to_remove_one_key_lock);
                cfd.setTitle(R.string.remove_one_key_lock);
                cfd.show();
            }
        });
    }
    /**
     * 初始化下载布局
     */
    private void initDownloadLayout(){
        final ImageView download = (ImageView) this.findViewById(R.id.image_download);
        isAlreadyDownload = PreferenceHelper.getBoolean(Const.KEY_OF_APP_DOWNLOAD_STATUS, false);
        if (isAlreadyDownload) {
            download.setImageResource(R.drawable.download_button_startup_selector);
        } else
        if (mDownloadManager.isDownloading()) {
            download.setImageResource(R.drawable.download_button_pause_selector);
        } else if (mDownloadManager.isPause()) {
            download.setImageResource(R.drawable.download_button_continue_selector);
        } else {
            download.setImageResource(R.drawable.download_button_down_selector);
        }
        mProgressLayout = this.findViewById(R.id.progress_layout);
        mTextHintLayout = this.findViewById(R.id.text_hint);
        mProgress = (ProgressView)this.findViewById(R.id.progress);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAlreadyDownload = PreferenceHelper.getBoolean(Const.KEY_OF_APP_DOWNLOAD_STATUS, false);
                if (isAlreadyDownload) {
                    onFinish(mDownloadManager.getApk());
                } else {
                    mProgressLayout.setVisibility(View.VISIBLE);
                    mTextHintLayout.setVisibility(View.GONE);
                    if (mDownloadManager.isDownloading()) {
                        download.setImageResource(R.drawable.download_button_continue_selector);
                        mDownloadManager.stop();
                    }else {
                        download.setImageResource(R.drawable.download_button_pause_selector);
                        mDownloadManager.start();
                    }
                }
            }
        };
        //初始化下载
        findViewById(R.id.download_layout).setOnClickListener(listener);
        download.setOnClickListener(listener);
    }
    @Override
    public void onError() {
        //下载失败提示用户
        Toast.makeText(this,R.string.download_failed,Toast.LENGTH_SHORT).show();
        final ImageView download = (ImageView) this.findViewById(R.id.image_download);
        download.setImageResource(R.drawable.download_button_down_selector);
    }

    @Override
    public void onProgressChanged(long completeSize,long totalSize) {
        TextView size= (TextView)this.findViewById(R.id.current_size);
        //显示当前下载的文件大小
        size.setText(new DecimalFormat("0.00").format(completeSize*1f/1024/1024)+"MB");
        //更新进度值
        mProgress.notifyProgressChanged(completeSize,totalSize);
    }

    @Override
    public void onFinish(File file) {
        ImageView download = (ImageView) this.findViewById(R.id.image_download);
        download.setImageResource(R.drawable.download_button_startup_selector);
        //安装文件apk路径
        String fileName= file.getAbsolutePath();
        //创建URI
        Uri uri= Uri.fromFile(new File(fileName));
        //创建Intent意图
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//启动新的activity
        //设置Uri和类型
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        //执行安装
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean result = mLockManager.onActivityResult(this,requestCode,resultCode,data);
        if(result){
            //权限开启成功后，改变状态值及图标
            final ImageView image = (ImageView)this.findViewById(R.id.open_device_permission);
            image.setImageResource(R.drawable.icon_open_lock);
            setButtonEnable(true);
            PreferenceHelper.putBoolean(Const.KEY_OF_DEVICE_PERMISSION, true);
        }
    }

    /**
     * 控制一键锁屏按钮状态
     * @param enable
     */
    private void setButtonEnable(boolean enable){
        mLockButton.setEnabled(enable);
        mLockButton.setBackgroundResource(enable ? R.drawable.button_background_enable_selector : R.drawable.button_background_disable_selector);
        if(enable){
            mLockButton.setTextColor(getResources().getColorStateList(R.color.button_text_color));
        }else{
            mLockButton.setTextColor(Color.parseColor("#999999"));
        }
    }

    /**
     * 一键锁屏对话框，取消，确认按钮处理;
     * @param v
     */
    public void onClick(View v) {
        boolean hasPermission = PreferenceHelper.getBoolean(Const.KEY_OF_DEVICE_PERMISSION,false);
        if(hasPermission) {
            //禁用一键锁屏，确认对话框按钮处理
            final ImageView image = (ImageView) findViewById(R.id.open_device_permission);
            mLockManager.removeDeviceManager();
            image.setImageResource(R.drawable.icon_close_lock);
            setButtonEnable(false);
            ToastManager.getInstance().showToast(LockSettingActivity.this, R.string.already_disable_one_key_lock, Toast.LENGTH_LONG);
            PreferenceHelper.putBoolean(Const.KEY_OF_DEVICE_PERMISSION, false);
        }else{
            mLockManager.activeDeviceManager(LockSettingActivity.this);
        }
    }
}
