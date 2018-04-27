package com.common.basecomponent.util;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.common.basecomponent.BaseApplication;

import java.util.UUID;

/**
 * Created by wuxiaowei on 2017/3/16.
 */

public class AppInfoUtil {

    public static String getDeviceId(Context context) {
        String deviceId = "";
        if (ActivityCompat.checkSelfPermission(context, permission.READ_PHONE_STATE)==PackageManager.PERMISSION_GRANTED) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = telephonyManager.getDeviceId();
            } catch (Exception e) {
            }
        }
        return deviceId;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

}
