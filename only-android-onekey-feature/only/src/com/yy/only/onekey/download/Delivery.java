package com.yy.only.onekey.download;

import android.os.Handler;
import android.os.Looper;

import com.yy.only.onekey.download.DownloadListener;

import java.io.File;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public class Delivery {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private DownloadListener mListener;
    public Delivery (DownloadListener listener){
        mListener = listener;
    }
    public void onError(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onError();
            }
        });
    }
    public void onFinish(final File file){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFinish(file);
            }
        });
    }
    public void onProgressChanged(final long completeSize,final long totalSize){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onProgressChanged(completeSize,totalSize);
            }
        });
    }
}
