package com.fullshare.basebusiness.api;

import android.content.Context;

import com.common.basecomponent.exception.ErrorType;
import com.common.basecomponent.util.L;
import com.fullshare.basebusiness.entity.PlatformInfoData;
import com.fullshare.basebusiness.net.CommonHttpRequest;
import com.fullshare.basebusiness.net.HttpService;
import com.fullshare.basebusiness.net.OnResponseCallback;
import com.fullshare.basebusiness.net.ResponseStatus;
import com.fullshare.basebusiness.util.RSAUtil;
import com.google.gson.Gson;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by wuxiaowei on 2018/4/23.
 */

public class CoachApi {


    public static final String QQ_LOGIN = "qqLogin";
    public static final String WX_LOGIN = "wechatLogin";
    public static final String SINA_LOGIN = "sinaLogin";
    /**
     * 发送验证码类型，1注册，2登录，3绑定，4找回密码，5验证旧手机
     */
    public static class SmsType{
        /**
         * 注册
         */
        public static final String REGISTER = "1";
        /**
         * 登录
         */
        public static final String LOGIN = "2";
        /**
         * 绑定手机
         */
        public static final String BIND = "3";
        /**
         * 找回密码
         */
        public static final String FIND_PASSWORD = "4";
        public static final String CHECK_OLD_MOBILE = "5";
    }
    public static void loginByPwd(Context context, String mobile, String pwd, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.COACH_API)
                .businessMethod("coachLogin")
//                .addbody("ciphertext", RSAUtil.encrypt(System.currentTimeMillis() + "|" + pwd))
                .addbody("ciphertext",pwd)
                .addbody("mobile", mobile)
                .build(), callback);
    }

    public static void updatePassword(Context context, String newPwd, String oldPwd, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.COACH_API)
                .businessMethod("updatePassword")
                .addbody("ciphertext", RSAUtil.encrypt(System.currentTimeMillis() + "|" + oldPwd + "|" + newPwd))
                .build(), callback);
    }


    public static void socialLogin(Context context, PlatformInfoData platform, OnResponseCallback callback) {
        L.json(new Gson().toJson(platform));
        CommonHttpRequest.Builder builder = new CommonHttpRequest.Builder()
                .url(ApiConstant.COACH_API);
        if (platform.getType() == SHARE_MEDIA.WEIXIN) {
            builder.businessMethod(WX_LOGIN);
            builder.addbody("unionid", platform.getUid());
        } else if (platform.getType() == SHARE_MEDIA.QQ) {
            builder.businessMethod(QQ_LOGIN);
        } else if (platform.getType() == SHARE_MEDIA.SINA) {
            builder.businessMethod(SINA_LOGIN);
        }
//        builder.version(ApiVersion.VERSION_3_0_0);
        builder.addbody("ciphertext", RSAUtil.encrypt(System.currentTimeMillis() + "|" + platform.getOpenId()));
        builder.addbody("accessToken", platform.getAccessToken());
        HttpService.request(context, builder.build(), callback);
    }

    public static void loginBySms(Context context, String mobile, String smsCode, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.COACH_API)
                .businessMethod("loginBySms")
                .addbody("ciphertext", RSAUtil.encrypt(System.currentTimeMillis() + "|" + smsCode))
                .addbody("mobile", mobile)
                .build(), callback);
    }

    /**
     * 发送验证码
     *
     * @param context
     * @param mobile   手机号
     * @param smsType  {@link SmsType}
     * @param callback
     */
    public static void sendMessage(Context context, String mobile, String smsType, final OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .businessMethod("send")
                .addbody("mobile", mobile)
                .addbody("smsType", smsType)
                .build(), new OnResponseCallback<String>() {

            @Override
            public void onStart() {
                callback.onStart();
            }

            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFinish(boolean isResponseSuccess, ResponseStatus status) {
                if (isResponseSuccess) {
                    status.setMessage("短信发送成功");
                } else {
                    if (status.getErrorType() == ErrorType.UNKNOW_ERROR) {
                        status.setMessage("发送短信失败，请重试");
                    } else {
                        if (status.getCode() == 3037) {
                            status.setMessage("瞄~操作太频繁了 ^c^");
                        }
                    }
                }
                callback.onFinish(isResponseSuccess, status);
            }
        });
    }
}
