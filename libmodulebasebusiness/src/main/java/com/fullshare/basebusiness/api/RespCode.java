package com.fullshare.basebusiness.api;

/**
 * author: wuxiaowei
 * date : 2017/3/22
 */
public class RespCode {
    /** **/
    public static final int BUSINESS_RESPONSE_OK = 200;
    /**
     * token失效
     **/
    public static final int TOKEN_OUT_DATE = 9521;
    /**
     * token被踢
     **/
    public static final int TOKEN_CONFLICT = 9522;
    /**
     * token为空
     **/
    public static final int TOKEN_IS_EMPTY = 9527;
    /**
     * token非法
     **/
    public static final int TOKEN_ILLEGAL = 9528;

    public static final int USER_NOT_EXISTS = 3004;

    //MOBILE_EXIT_ERROR(3014,"该手机号码已经被注册","该手机号码已经被注册"),
    //CMS_VLID_ERROR(3018,"短信校验错误","短信校验错误"),
    //CMS_TIMEOUT_ERROR(3019,"短信验证码过期","短信验证码过期"),
}
