package com.lavenderangle.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.lavenderangle.service.binder.DownloadBinder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by disanyun on 2015/7/22 0022.
 */
public class DownloadService extends Service {
    private static final String TAG = "DownloadService";

    private static final int CANCEL = 100;
    private static final int ONGOING = 101;
    private static final int FAILED = 102;
    private static final int SUCCESS = 103;
    private static final int START = 104;

    private int progress;
    private DownloadBinder binder;
    private Thread downLoadThread;

    public Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:// 下载完毕
                    Log.i(TAG, "Download Successfully!");
                    binder.getCallback().onFinish();
                    break;
                case CANCEL:// 用户手动取消
                case FAILED:// 下载失败
                    Log.i(TAG, "Download Failed!");
                    binder.getCallback().onCancel();
                    downLoadThread = null;
                    stopSelf();
                    break;
                case ONGOING://下载中
                    Log.i(TAG, "Download is ongoing！progress = " + progress);
                    if (progress < 100) {
                        binder.getCallback().onGoing(progress);
                    } else {
                        Log.i(TAG, "Download Successfully!");
                        binder.getCallback().onFinish();
                        stopSelf();// 停掉服务自身
                    }
                    break;
                case START://开始下载
                    Log.i(TAG, "Download Start!");
                    binder.getCallback().onStart();
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "DownloadService onBind");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "DownloadService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "DownloadService onDestroy");
        binder.getCallback().onCancel();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("DownloadService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println("DownloadService onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "DownloadService onCreate");
        super.onCreate();
        binder = new DownloadBinder();
        start();
    }
    private void start(){
        if (downLoadThread == null || !downLoadThread.isAlive()) {
            progress = 0;
            download();// 下载
        }
    }
    private void download() {
        downLoadThread = new Thread(mDownloadRunnable);
        downLoadThread.start();
    }
    private Runnable mDownloadRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(binder.getDownloadUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn == null) {
                    Log.w(TAG, " The HttpURLConnection opened failed ");
                    mHandler.sendEmptyMessage(FAILED);
                    return;
                }
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                File file = new File(binder.getSavePath());
                FileOutputStream fos = new FileOutputStream(file);
                int count = 0 , lastRate = 0;
                byte buf[] = new byte[1024];
                do {
                    int numRead = is.read(buf);
                    count += numRead;
                    progress = (int) (((float) count / length) * 100);
                    //每次增加1%时，更新进度
                    if (progress >= lastRate + 1) {
                        mHandler.sendEmptyMessage(ONGOING);
                        lastRate = progress;
                    }
                    if (numRead <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(SUCCESS);
                        // 下载完了，cancelled也要设置
                        binder.setCanceled(true);
                        break;
                    }
                    fos.write(buf, 0, numRead);
                } while (!binder.isCanceled());// 点击取消就停止下载.

                fos.close();
                is.close();
            }catch (Exception e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(FAILED);
            }

        }
    };

}
