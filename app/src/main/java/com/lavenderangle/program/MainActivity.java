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
            Log.i(TAG, "�󶨹�ϵ�����ж�");
            isBinded = false;
            unbindService(conn);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (DownloadBinder) service;
            Log.i(TAG, "�󶨳ɹ�");
            // ��ʼ����
            isBinded = true;
            binder.setCallback(new DownloadCallback() {
                @Override
                public void onStart() {//���ؿ�ʼ
                    ((LaApplication)getApplication()).setIsDownloading(true);
                }

                @Override
                public void onGoing(int progress) {//���ؽ�����
                    ((LaApplication)getApplication()).setIsDownloading(true);
                }

                @Override
                public void onFinish() {//�������
                    ((LaApplication)getApplication()).setIsDownloading(false);
                }

                @Override
                public void onCancel() {//ȡ������
                    ((LaApplication)getApplication()).setIsDownloading(false);
                }

                @Override
                public void onFaiure() {//����ʧ��
                    ((LaApplication)getApplication()).setIsDownloading(false);
                }
            });
        }
    };
    private void checkUpdate(){//�������ط���
        boolean isMustUpdate = false;
        Intent it = new Intent(MainActivity.this,
                DownloadService.class);
        startService(it);
        if (isMustUpdate) {//ǿ�Ƹ���
            //progress_relative.setVisibility(View.VISIBLE);��ʾ���ؽ��棬���ý������
            bindService(it, conn, Context.BIND_AUTO_CREATE);
        }
    }
}
