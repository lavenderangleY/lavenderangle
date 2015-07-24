package com.lavenderangle.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by disanyun on 2015/7/23 0023.
 * ϵͳ���񹤾���
 */
public class LASystemServiceUtil {

    /**
     * ����APK��װҳ��
     * @param context �����Ķ���
     * @param apkFile ��װ���ļ�����
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
