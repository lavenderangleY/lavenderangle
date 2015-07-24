package com.lavenderangle.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by disanyun on 2015/7/9 0009.
 *
 * 自定义Toast工具类
 */
public class LAToastUtil {
    /**
     * 上下文对象
     */
    private static Context mContext = null;
    /**
     * 显示Toast
     */
    private static final int SHOW = 101;

    private static Handler baseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW:
                    showToast(mContext,""+ msg.getData().getString("Text"));
                    break;
            }
            return false;
        }
    });

    /**
     * Toast提示文本信息
     * @param context
     * @param text
     */
    public static void  showToast(Context context , String text){
        mContext = context;
        if (!LAStringUtil.isEmpty(text)){
            Toast.makeText(mContext,text,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Toast提示文本
     * @param context
     * @param resId  文本资源ID
     */
    public static void showToast(Context context,int resId){
        mContext = context;
        Toast.makeText(mContext,""+ context.getResources().getString(resId),Toast.LENGTH_SHORT).show();
    }

    /**
     * 在线程中提示文本信息
     * @param context
     * @param string  文本
     */
    public static void showToastInThread(Context context , String string ){
        mContext = context;
        Message message = baseHandler.obtainMessage(SHOW);
        Bundle bundle = new Bundle();
        bundle.putString("Text",string);
        message.setData(bundle);
        baseHandler.sendMessage(message);
    }

    /**
     * 在线程中提示文本
     * @param context
     * @param resId 文本资源ID
     */
    public static void showToastInThread(Context context, int resId){
        showToastInThread(context, "" + context.getString(resId));
    }


}
