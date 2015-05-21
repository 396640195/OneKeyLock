package com.yy.only.onekey.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yy.only.onekey.R;
import com.yy.only.onekey.utils.ScreenUtil;


/**
 * Created by Administrator on 2015/4/25 0025.
 */
public class RedDotTextView extends TextView {
    public boolean isShowRedDot=true;
    private int mDeltDotWidth = 13;

    public RedDotTextView(Context context) {
        super(context);
    }

    public RedDotTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RedDotTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowRedDot) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_dot);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setTextSize(this.getTextSize());
            int width = (int) p.measureText(this.getText().toString());
            canvas.drawBitmap(bitmap, width + ScreenUtil.dp2px(5), ScreenUtil.dp2px(5), p);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec) + ScreenUtil.dp2px(mDeltDotWidth);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setShowRedDot(boolean isShowRedDot) {
        this.isShowRedDot = isShowRedDot;
        this.invalidate();
    }

}
