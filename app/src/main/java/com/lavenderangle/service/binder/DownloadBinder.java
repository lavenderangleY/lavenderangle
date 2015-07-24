package com.lavenderangle.service.binder;

import android.os.Binder;

import com.lavenderangle.service.callback.DownloadCallback;

/**
 * Created by disanyun on 2015/7/23 0023.
 */
public class DownloadBinder extends Binder{
    private  DownloadCallback callback;//�����еĻص�����
    private  String downloadUrl;//���ص�ַ
    private  String  savePath;//���غ�ı����ַ
    /**
     * �Ƿ�ȡ������
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
