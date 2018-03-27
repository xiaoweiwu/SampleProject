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
    public final static String UMENG_SHARE_QQ_APPID = "1106129164";
    public final static String UMENG_SHARE_QQ_APPKEY = "Kt5fgE7bgaa97ax7";
    public final static String UMENG_SHARE_SINA_APPID = "3507961391";
    public final static String UMENG_SHARE_SINA_APPSECRET = "c1bec4a9d9f9d1d6af1ad0fcdff898e1";
    public final static String UMENG_SHARE_WX_APPID = "wx086f458ddd2343d4";
    public final static String UMENG_SHARE_WX_APPSECRET = "980df10fd191ca4b027a3a1d5cf0c385";
    public static final String UMENT_APP_KEY = "584a5755aed17934bb0019aa";

    public static final String APP_ENCRYPT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQWlCJKux6th72ZLa9+axSvJqvZdmUNQt2ExGcqx41Xd6ToGICPPzDdSf+Vk/LqLop2xoTz4JpxSpO8YyBNTyS77b4/Jf7Iu8p38NCS33J133sC+03deUII1ClkCwseHjaBPGkTHUH2IJgGLd6dZCrsaSGP418a70kJTb5DvoLywIDAQAB";
    /**
     * 淘宝客pid
     **/
    public static final String TAOBAO_LINK_PID = "2bbe952ed5c37becd894e64f23f7d818";
    /**
     * 有赞client_id
     **/
    public static final String YOUZAN_CLIENT_ID = "7b36cd00328ebf7f50";

    /**
     * 智齿ID
     */
    public static final String ZHI_CHI_ID = "b085f89f072f4c56befd1f4e814bbd7d";

    public static class BusinessMethod {
        public static final String HEALTH_AUTHOR_DETAIL = "getAuthorDetail"; //专家基本信息
        public static Map<String, String> methodDescMap = new HashMap<>();

        static {
            methodDescMap.put(HEALTH_AUTHOR_DETAIL, "专家基本信息");
        }
    }
}
