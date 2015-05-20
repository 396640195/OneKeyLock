package com.yy.only.onekey.download;

import java.io.File;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public class DownloadManager{
    private DownloadListener mListener;
    private Thread mDownloadThread;
    private DownloadTask mTask;
    public DownloadManager (DownloadListener listener){
        this.mListener = listener;
        mTask = new DownloadTask(mListener);
    }
    public void start(){
        mTask.stop();
        mTask.reset();
        mDownloadThread = new Thread(mTask);
        mDownloadThread.start();
    }

    public void stop(){
        mTask.stop();
    }
    public boolean isPause(){
        return (mTask.getStatus() == Status.ERROR || mTask.getStatus() == Status.PAUSE);
    }

    public boolean isDownloading(){
        return mTask.getStatus() == Status.DOWNLOADING;
    }

    public boolean isDownloaded(){
        return mTask.getStatus() == Status.FINISH;
    }
    public enum Status{
        INIT,DOWNLOADING,FINISH,PAUSE,ERROR
    }

    public File getApk(){
        return mTask.getApk();
    }
}
