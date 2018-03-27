package com.common.basecomponent.constant;

import android.os.Build;

/**
 * Created by wuxiaowei on 2017/3/16.
 */

public class BaseConstant {

    public static final String DB_NAME = "fullshare.db";
    public static final int DB_VERSION = 1;

    /**
     * 第一页
     */
    public static final int FIRST_PAGE_INDEX = 1;
    /**
     * 默认分页数
     */
    public static final int PAGE_NUM = 10;

    public static class HttpConfig {
        //连接超时时间 s
        public static final int CONNECT_TIMEOUT = 15;
        public static final int READ_TIMEOUT = 15;
        public static final int WRITE_TIMEOUT = 15;
    }


    public static class HttpParamKey {
        public static final String BRAND = "brand";
        public static final String MODEL = "mode";
        public static final String PLATFORM = "platform";
        public static final String OS = "osVersion";
        public static final String AUTHORIZATION = "authorization";
        public static final String ACCEPT = "Accept";
        public static final String CONTENT_TYPE = "Content-type";
        public static final String CHANNEL = "channel";
        public static final String APP_VERSION = "appVersion";
        public static final String DEVICE_ID = "deviceId";
    }

    public static class HttpParam {
        public static final String BRAND = Build.BRAND;
        public static final String MODEL = Build.MODEL;
        public static final String PLATFORM = "android";
        public static final String OS = Build.VERSION.RELEASE;
        public static final String ACCEPT = "Application/json";
        public static final String CONTENT_TYPE = "Application/json";
        //"application/x-www-from-urlencoded";
    }
}
