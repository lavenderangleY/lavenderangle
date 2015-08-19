package com.lavenderangle.util;

import android.util.Log;

/**
 * Created by disanyun on 2015/8/19 0019.
 */
public class LALogUtil {


    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg, Exception e) {
        Log.e(tag,msg,e);
    }
}
