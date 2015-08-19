package com.lavenderangle.program;

import android.content.Context;

import com.lavenderangle.util.LAConstants;
import com.lavenderangle.util.LADateUtil;
import com.lavenderangle.util.LALogUtil;
import com.lavenderangle.util.LASDCardUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by disanyun on 2015/8/19 0019.
 *
 * 全局异常处理类，当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 */
public class LACrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //程序的Context对象
    private Context mContext;

    //CrashHandler实例
    private static LACrashHandler instance;
    /** 获取CrashHandler实例 ,单例模式 */
    public static LACrashHandler getInstance() {
        if (instance == null)
            instance = new LACrashHandler();
        return instance;
    }

    /** 保证只有一个CrashHandler实例 */
    private LACrashHandler() {
    }


    /**
     * 初始化
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //保存日志文件
        saveCatchInfo2File(ex);
        return true;
    }


    /**
     * 保存错误信息到文件中
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private String saveCatchInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String fileName = "CRASH-" + LADateUtil.getCurrentTime("yyyyMMddHHmmss") + ".log";
            LASDCardUtil.saveFile(LAConstants.crashDir + fileName, sb.toString());
            return fileName;
        } catch (Exception e) {
            LALogUtil.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}
