package com.lavenderangle.program;

import android.app.Application;

/**
 * Created by disanyun on 2015/7/23 0023.
 */
public class LaApplication extends Application {
    /**
     * 是否正在下载
     */
    private boolean isDownloading = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setIsDownloading(boolean isDownloading) {
        this.isDownloading = isDownloading;
    }
}
