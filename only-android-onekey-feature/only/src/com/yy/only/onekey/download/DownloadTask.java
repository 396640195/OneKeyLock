package com.yy.only.onekey.download;

import android.os.Environment;
import android.text.TextUtils;

import com.yy.only.onekey.utils.Const;
import com.yy.only.onekey.utils.MD5;
import com.yy.only.onekey.utils.PreferenceHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/19 0019.
 */
public class DownloadTask implements Runnable {
    private long mTotalSize;
    private long mCompeleteSize; 
    private File mFileStore;
    private Delivery mDelivery;
    private boolean mQuit;
    private DownloadManager.Status mStatus;

    public DownloadTask(DownloadListener listener) {
        this.mDelivery = new Delivery(listener);
        this.mQuit = false;
        File root = Environment.getExternalStorageDirectory();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String apkName = sdf.format(new Date());
        mFileStore = new File(root, String.format("%1s.apk", apkName));
        if (mFileStore.exists()) {
            mCompeleteSize = mFileStore.length();
        } else {
            mCompeleteSize = 0;
        }
    }

    @Override
    public void run() {
        mStatus = DownloadManager.Status.DOWNLOADING;
        HttpURLConnection connection;
        RandomAccessFile randomAccessFile=null;
        InputStream is = null;
        long lastTime = System.currentTimeMillis();
        try {
            String requestURL = requestURL();
            //如果请求url为空，则停止下载;
            if(TextUtils.isEmpty(requestURL)){
                mStatus = DownloadManager.Status.ERROR;
                mDelivery.onError();
                return;
            }
            URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();
            //设置超时
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            //设置获取字节范围
            connection.setRequestProperty("Range", "bytes=" + mCompeleteSize + "-");
            //获取要下载的字节大小
            mTotalSize = connection.getContentLength();
            randomAccessFile = new RandomAccessFile(mFileStore, "rw");
            is = connection.getInputStream();
            randomAccessFile.seek(mCompeleteSize);
            byte[] buffer = new byte[4096];
            int length = 0;
            mTotalSize = mCompeleteSize + mTotalSize;
            if (mCompeleteSize == mTotalSize){
                mDelivery.onFinish(mFileStore);
                return;
            }
            mDelivery.onProgressChanged(mCompeleteSize,mTotalSize);
            while ((length = is.read(buffer)) != -1) {
                if (mQuit) {
                    mStatus = DownloadManager.Status.PAUSE;
                    return;
                }
                randomAccessFile.write(buffer, 0, length);
                mCompeleteSize += length;
                if (System.currentTimeMillis() - lastTime > 1000) {
                    mDelivery.onProgressChanged(mCompeleteSize,mTotalSize);
                    lastTime = System.currentTimeMillis();
                }
                mStatus = DownloadManager.Status.DOWNLOADING;
            }
            mStatus = DownloadManager.Status.FINISH;
            mDelivery.onProgressChanged(mCompeleteSize,mTotalSize);
            mDelivery.onFinish(mFileStore);
            PreferenceHelper.putString(Const.KEY_OF_APP_MD5, MD5.getMD5(mFileStore));
        } catch (Exception e) {
            e.printStackTrace();
            mDelivery.onError();
            mStatus = DownloadManager.Status.ERROR;
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mQuit = true;
        }
    }

    public String requestURL() {
        String result = null;
        URL url;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(Const.KEY_OF_APP_DOWNLOAD_URL);
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.getInt("status");
            if(status == 200){
                return jsonObject.getString("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public void stop() {
        mStatus = DownloadManager.Status.PAUSE;
        mQuit = true;
    }

    public DownloadManager.Status getStatus() {
        return mStatus;
    }

    public void reset(){
        this.mQuit = false;
        mStatus = DownloadManager.Status.INIT;
    }

    public File getApk(){
        return mFileStore;
    }
}
