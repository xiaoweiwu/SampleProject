package com.common.basecomponent.util;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.common.basecomponent.BaseApplication;

import java.util.UUID;

/**
 * Created by wuxiaowei on 2017/3/16.
 */

public class AppInfoUtil {

    public static String getAppVersionName(Context context) {
        return BaseApplication.get().getAppVersionName();
    }

    public static String getAppVersionCode(Context context) {
        return BaseApplication.get().getAppVersionName();
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return packageInfo;
    }

    public static boolean checkPermisson(Context context, String permisson) {
        return context.checkCallingOrSelfPermission(permisson) == PackageManager.PERMISSION_GRANTED;
//        return ContextCompat.checkSelfPermission(context,permisson)== PackageManager.PERMISSION_GRANTED;
    }

    public static String getDeviceId(Context context) {
        String deviceId = "";
        if (checkPermisson(context, permission.READ_PHONE_STATE)) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = telephonyManager.getDeviceId();
                deviceId = deviceId == null ? getAndroidId(context) : deviceId;
            } catch (Exception e) {
                deviceId = getAndroidId(context);
            }
        } else {
            deviceId = getAndroidId(context);
        }
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = UUID.randomUUID().toString();
        }
        return deviceId;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

}
