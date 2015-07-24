package com.lavenderangle.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by disanyun on 2015/7/23 0023.
 * �洢��������
 * ��Ҫ���ô洢��Ȩ��
 * <!--��SDCard�д�����ɾ���ļ�Ȩ�� -->
 * <uses-permissioandroid:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 * <!--��SDCardд������Ȩ�� -->
 * <uses-permissionandroid:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */
public class LASDCardUtil {

    private static final String TAG = "LASDCardUtil";

    /**
     * ��ȡ�ڴ濨��·��
     * @return
     */
    public static String  getSdcardPath(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return getPath();
    }

    /**
     * SD������������ʱ,��ȡ�ڴ濨��·��
     * @return
     */
    private static  String getPath() {
        String sdcard_path = null;
        String sd_default = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        Log.i(TAG, sd_default);
        if (sd_default.endsWith("/")) {
            sd_default = sd_default.substring(0, sd_default.length() - 1);
        }
        // �õ�·��
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sdcard_path != null) {
            Log.i(TAG, sdcard_path);
        }
        return sdcard_path;
    }
}