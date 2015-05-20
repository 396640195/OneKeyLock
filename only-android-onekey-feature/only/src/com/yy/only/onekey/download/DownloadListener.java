package com.yy.only.onekey.download;

import java.io.File;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public interface DownloadListener {
    public void onError();
    public void onFinish(File file);
    public void onProgressChanged(long completeSize,long totalSize);
}
