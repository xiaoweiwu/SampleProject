package com.fullshare.basebusiness.api;

import android.content.Context;
import android.os.Build;

import com.common.basecomponent.BaseApplication;
import com.fullshare.basebusiness.net.CommonHttpRequest;
import com.fullshare.basebusiness.net.HttpService;
import com.fullshare.basebusiness.net.OnResponseCallback;

/**
 * author: wuxiaowei
 * date : 2017/4/10
 */

public class AppManagerApi {

    public static final String NAME = "/app";

    public static void postDeviceInfo(Context context, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(NAME)
                .businessMethod("device")
                .addbody("platform", "Android")
                .addbody("appVersion", BaseApplication.get().getAppVersionName())
                .addbody("osVersion", Build.VERSION.RELEASE)
                .addbody("mode", Build.MODEL)
                .addbody("brand", Build.BRAND)
                .build(), callback);
    }

}
