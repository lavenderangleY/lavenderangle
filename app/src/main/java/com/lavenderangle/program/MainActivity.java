package com.lavenderangle.program;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.lavenderangle.service.DownloadService;
import com.lavenderangle.service.binder.DownloadBinder;
import com.lavenderangle.service.callback.DownloadCallback;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    DownloadBinder binder;
    private boolean isBinded;
    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "绑定关系意外中断");
            isBinded = false;
            unbindService(conn);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (DownloadBinder) service;
            Log.i(TAG, "绑定成功");
            // 开始下载
            isBinded = true;
            binder.setCallback(new DownloadCallback() {
                @Override
                public void onStart() {//下载开始
                    ((LaApplication)getApplication()).setIsDownloading(true);
                }

                @Override
                public void onGoing(int progress) {//下载进行中
                    ((LaApplication)getApplication()).setIsDownloading(true);
                }

                @Override
                public void onFinish() {//下载完成
                    ((LaApplication)getApplication()).setIsDownloading(false);
                }

                @Override
                public void onCancel() {//取消下载
                    ((LaApplication)getApplication()).setIsDownloading(false);
                }

                @Override
                public void onFaiure() {//下载失败
                    ((LaApplication)getApplication()).setIsDownloading(false);
                }
            });
        }
    };
    private void checkUpdate(){//开启下载服务
        boolean isMustUpdate = false;
        Intent it = new Intent(MainActivity.this,
                DownloadService.class);
        startService(it);
        if (isMustUpdate) {//强制更新
            //progress_relative.setVisibility(View.VISIBLE);显示下载界面，不让进入程序
            bindService(it, conn, Context.BIND_AUTO_CREATE);
        }
    }
}
