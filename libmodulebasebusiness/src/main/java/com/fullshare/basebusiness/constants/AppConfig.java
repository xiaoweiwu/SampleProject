package com.fullshare.basebusiness.constants;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuxiaowei on 16/9/7.
 */
public class AppConfig {
    /**
     * 友盟分享，第三方appid和appkey
     */
    public final static String UMENG_SHARE_QQ_APPID = "";
    public final static String UMENG_SHARE_QQ_APPKEY = "";
    public final static String UMENG_SHARE_SINA_APPID = "";
    public final static String UMENG_SHARE_SINA_APPSECRET = "";
    public final static String UMENG_SHARE_WX_APPID = "";
    public final static String UMENG_SHARE_WX_APPSECRET = "";
    public static final String UMENT_APP_KEY = "";

    public static class BusinessMethod {
        public static final String HEALTH_AUTHOR_DETAIL = "getAuthorDetail"; //专家基本信息
        public static Map<String, String> methodDescMap = new HashMap<>();

        static {
            methodDescMap.put(HEALTH_AUTHOR_DETAIL, "专家基本信息");
        }
    }
}
