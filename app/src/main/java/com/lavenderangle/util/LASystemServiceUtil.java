package com.lavenderangle.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by disanyun on 2015/7/23 0023.
 * 系统服务工具类
 */
public class LASystemServiceUtil {

    /**
     * 启动APK安装页面
     * @param context 上下文对象
     * @param apkFile 安装包文件对象
     */
    public static void installApk(Context context,File apkFile ) {
        if (!apkFile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        context.startActivity(i);
    }
}
