package com.lavenderangle.util;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by disanyun on 2015/7/23 0023.
 * 存储卡工具类
 * 需要配置存储卡权限
 * <!--在SDCard中创建与删除文件权限 -->
 * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 * <!--往SDCard写入数据权限 -->
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */
public class LASDCardUtil {

    private static final String TAG = "LASDCardUtil";

    /**
     * 获取内存卡的路径
     * @return
     */
    public static String  getSdcardPath(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return getPath();
    }

    /**
     * SD卡非正常挂载时,获取内存卡的路径
     * @return
     */
    private static  String getPath() {
        String sdcard_path = null;
        String sd_default = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        LALogUtil.i(TAG, sd_default);
        if (sd_default.endsWith("/")) {
            sd_default = sd_default.substring(0, sd_default.length() - 1);
        }
        // 得到路径
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
            LALogUtil.i(TAG, sdcard_path);
        }
        return sdcard_path;
    }

    /**
     * 保存文件内容
     */
    public static void saveFile(String filePath, String content){
        File file = new File(filePath);
        File dir = file.getParentFile();//获取到上一级目录
        try {
            if (!dir.exists()){
                dir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
