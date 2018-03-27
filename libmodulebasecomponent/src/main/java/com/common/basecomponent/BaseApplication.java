package com.common.basecomponent;

import android.app.Application;

/**
 * Created by wuxiaowei on 2017/3/16.
 */

public abstract class BaseApplication extends Application {

    protected static BaseApplication instance;

    public static BaseApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        //Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());
    }

    /**
     * 退出app
     */
    public abstract void onExitApp();

    public abstract boolean isDebug();

    public abstract boolean isLogDebug();

    public abstract String getChannel();

    public abstract String getApplicationId();

    public abstract String getAppVersionName();

    public abstract int getAppVersionCode();

    public abstract int getEnvironment();

}
