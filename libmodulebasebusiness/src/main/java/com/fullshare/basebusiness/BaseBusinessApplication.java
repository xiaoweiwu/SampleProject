package com.fullshare.basebusiness;

import com.common.basecomponent.BaseApplication;
import com.common.basecomponent.util.L;
import com.fullshare.basebusiness.constants.AppConfig;
import com.orhanobut.logger.Logger;
//import com.umeng.message.IUmengRegisterCallback;

import common.service.SocialManager;
import common.service.StatisticsManager;

/**
 * Created by wuxiaowei on 2017/3/16.
 */

public abstract class BaseBusinessApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        long t1 = System.currentTimeMillis();
        init();
        L.e("启动耗时" + (System.currentTimeMillis() - t1) + "");
    }

    private void init() {
        initLogger();
        initShare();
        initUMStat();
        initPush();
    }

    /**
     * 初始化日志
     */
    private void initLogger() {
        Logger
                .init(L.TAG)                 // default PRETTYLOGGER or use just initView()
//                .methodCount(3)                 // default 2
                .hideThreadInfo()              // default shown
//                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
//                .methodOffset(2)                // default 0
//                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
        ;
    }

    /**
     * 友盟分享
     */
    private void initShare() {
        SocialManager.init(this, isDebug())
                .setWeixin(AppConfig.UMENG_SHARE_WX_APPID,
                        AppConfig.UMENG_SHARE_WX_APPSECRET)
                .setQQZone(AppConfig.UMENG_SHARE_SINA_APPID,
                        AppConfig.UMENG_SHARE_SINA_APPSECRET)
                .setSinaWeibo(AppConfig.UMENG_SHARE_QQ_APPID,
                        AppConfig.UMENG_SHARE_QQ_APPKEY);

    }

    private void initUMStat() {
        StatisticsManager.init(this, AppConfig.UMENT_APP_KEY, getChannel());
    }

    private void initPush() {
//        PushManager.init(this, getChannel(), isDebug(), new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String s) {
//
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//
//            }
//        });
    }

}
