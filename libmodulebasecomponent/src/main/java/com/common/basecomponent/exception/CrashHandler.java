package com.common.basecomponent.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import com.common.basecomponent.BaseApplication;
import com.common.basecomponent.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: wuxiaowei
 * Date: 2015-07-30
 * Time: 17:05
 * Desc:
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    //系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    //程序的Context对象
    private Context mContext;
    private String mFilePath;
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    /**
     * 保证只有一个CrashHandler实例
     */
    public CrashHandler(Context context, String filePath) {
        mContext = context;
        mFilePath = filePath;
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
        //PollingUtils.stopPollingService(mContext, PollingService.class, PollingService.ACTION);
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //退出程序
            BaseApplication.get().onExitApp();
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        //收集设备参数信息
        StringBuilder builder = collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex, builder);

        //使用Toast来显示异常信息
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Looper.loop();
//            }
//        }.start();
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private StringBuilder collectDeviceInfo(Context ctx) {
        StringBuilder builder = new StringBuilder();
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                builder.append("versionName = ").append(pi.versionName).append('\n');
                builder.append("versionCode = ").append(pi.versionCode).append('\n');
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                builder.append(field.getName()).append(field.get(null).toString()).append('\n');
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
        return builder;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private void saveCrashInfo2File(Throwable ex, StringBuilder builder) {
        builder.append('\n').append(Log.getStackTraceString(ex));

        String crashName = String.format("Crash_%s.log", formatter.format(new Date()));
        try {
            FileUtil.saveStringFile(new File(mFilePath, crashName), builder.toString());
        } catch (IOException e) {
            Log.e(TAG, "saveCrashInfo2File", e);
        }
    }

}
