package com.lavenderangle.service.callback;

/**
 * Created by disanyun on 2015/7/23 0023.
 */
public interface DownloadCallback {
    void  onStart();
    void  onGoing(int progress);
    void  onFinish();
    void  onCancel();
    void  onFaiure();
}
