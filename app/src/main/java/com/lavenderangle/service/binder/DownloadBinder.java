package com.lavenderangle.service.binder;

import android.os.Binder;

import com.lavenderangle.service.callback.DownloadCallback;

/**
 * Created by disanyun on 2015/7/23 0023.
 */
public class DownloadBinder extends Binder{
    private  DownloadCallback callback;//下载中的回调方法
    private  String downloadUrl;//下载地址
    private  String  savePath;//下载后的保存地址
    /**
     * 是否取消下载
     */
    private boolean canceled = false;

    public DownloadCallback getCallback() {
        return callback;
    }

    public void setCallback(DownloadCallback callback) {
        this.callback = callback;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
