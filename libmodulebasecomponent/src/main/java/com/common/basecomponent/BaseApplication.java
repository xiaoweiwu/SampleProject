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
    }

    /**
     * 退出app
     */
    public abstract void onExitApp();
}
