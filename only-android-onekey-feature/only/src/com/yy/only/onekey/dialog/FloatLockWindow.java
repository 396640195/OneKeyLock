package com.yy.only.onekey.dialog;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yy.only.onekey.R;
import com.yy.only.onekey.common.OneKeyLockApplication;
import com.yy.only.onekey.utils.Const;
import com.yy.only.onekey.utils.LockManager;
import com.yy.only.onekey.utils.PreferenceHelper;
import com.yy.only.onekey.utils.ScreenUtil;
import com.yy.only.onekey.utils.YLog;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class FloatLockWindow {
    private static FloatLockWindow sFloatLockWindow;
    private boolean isShowing;

    public static FloatLockWindow getInstance() {
        if (sFloatLockWindow == null) {
            sFloatLockWindow = new FloatLockWindow();
        }
        return sFloatLockWindow;
    }

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;
    private ImageView mContentView;
    private LockManager mLockManager;

    public FloatLockWindow() {
        mWindowManager = (WindowManager) OneKeyLockApplication.getDefaultApplication().getSystemService(Context.WINDOW_SERVICE);
        mWindowParams = OneKeyLockApplication.getWindowLayoutParams();
        mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mContentView = new ContentView(OneKeyLockApplication.getDefaultApplication());
        mContentView.setImageResource(R.drawable.icon_float_default);
        //ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ScreenUtil.dp2px(30),ScreenUtil.dp2px(30));
        //mContentView.setLayoutParams(lp);
        mLockManager = LockManager.getInstance();

    }

    private class ContentView extends ImageView {
        private float mLastX, mLastY;
        private int moveCount;

        public ContentView(Context context) {
            super(context);
        }

        public ContentView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ContentView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(ScreenUtil.dp2px(50), ScreenUtil.dp2px(50));
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mLastX = event.getRawX();
                    mLastY = event.getRawY();
                    moveCount = 0;
                    setImageResource(R.drawable.icon_float_pressed);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = event.getRawX() - mLastX;
                    float dy = event.getRawY() - mLastY;

                    mLastX = event.getRawX();
                    mLastY = event.getRawY();

                    mWindowParams.x -= (int) dx;
                    mWindowParams.y += (int) dy;
                    mWindowManager.updateViewLayout(this, mWindowParams);
                    moveCount++;
                    break;
                case MotionEvent.ACTION_UP:
                    if (moveCount <= 3) {
                        mLockManager.lockDevice(OneKeyLockApplication.getDefaultApplication());
                    }
                    setImageResource(R.drawable.icon_float_default);
                    break;
            }
            return true;
        }
    }

    public void show() {
        boolean hasPermission = PreferenceHelper.getBoolean(Const.KEY_OF_DEVICE_PERMISSION,false);
        boolean hasOpen = PreferenceHelper.getBoolean(Const.KEY_OF_FLOAT_WINDOW_STATUS,false);
        if(!isShowing && hasPermission && hasOpen) {
            mWindowManager.addView(mContentView, mWindowParams);
            isShowing = true;
        }
    }

    public void dissmiss() {
        try {
            isShowing = false;
            mWindowManager.removeView(mContentView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
