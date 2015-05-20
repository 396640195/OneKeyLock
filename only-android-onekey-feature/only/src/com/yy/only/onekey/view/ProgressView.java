package com.yy.only.onekey.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public class ProgressView extends View {
    private long mCompleteSize=10;
    private long mTotalSize=100;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String mProgressColor="#52b7ff";
    private String mSecondProgressColor="#9e9e9e";
    private DecimalFormat mDecimalFormat = new DecimalFormat("00.00");
    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        //绘制总进度
        mPaint.setColor(Color.parseColor(mSecondProgressColor));
        canvas.drawRect(new Rect(0,0,this.getWidth(),this.getHeight()),mPaint);
        //绘制当前进度
        float progress = mCompleteSize * 1.f / mTotalSize * this.getWidth();
        mPaint.setColor(Color.parseColor(mProgressColor));
        canvas.drawRect(new Rect(0,0,(int)progress,this.getHeight()),mPaint);
    }

    public void notifyProgressChanged(long completeSize,long totalSize){
        this.mCompleteSize = completeSize;
        this.mTotalSize = totalSize;
        this.invalidate();
    }

}
