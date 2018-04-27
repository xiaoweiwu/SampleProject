package com.common.basecomponent;

import android.text.TextUtils;

import com.common.basecomponent.constant.BaseSpConstant;
import com.common.basecomponent.util.SharedPreferencesUtil;
import com.google.gson.Gson;

/**
 * Created by wuxiaowei on 2018/4/23.
 */

public class SystemInfo {

    private static SystemInfo systemInfo;

    private String deviceId;

    private String channel;

    private String versionName;

    private int versionCode;

    private boolean debug;

    private boolean logDebug;

    private String packageName;

    private int hostType;

    public SystemInfo setHostType(int hostType) {
        this.hostType = hostType;
        return systemInfo;
    }

    public int getHostType() {
        return hostType;
    }

    public SystemInfo setPackageName(String packageName) {
        this.packageName = packageName;
        return systemInfo;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public SystemInfo setVersion(int versionCode,String versionName) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        return systemInfo;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isLogDebug() {
        return logDebug;
    }

    public SystemInfo setDebug(boolean debug,boolean logDebug) {
        this.logDebug = logDebug;
        this.debug = debug;
        return systemInfo;
    }

    public String getChannel() {
        return channel;
    }

    public SystemInfo setChannel(String channel) {
        this.channel = channel;
        return systemInfo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public SystemInfo setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return systemInfo;
    }
    private SystemInfo(){}

    public static SystemInfo getSystemInfo() {
        if (systemInfo == null) {
            String userInfo = (String) SharedPreferencesUtil.getDefault().get(BaseSpConstant.SYSTEM_INFO, "");
            if (!TextUtils.isEmpty(userInfo)) {
                Gson gson = new Gson();
                systemInfo = gson.fromJson(userInfo, SystemInfo.class);
            } else {
                systemInfo = new SystemInfo();
            }
        }
        return systemInfo;
    }

    public  void update() {
        if (systemInfo != null) {
            Gson gson = new Gson();
            SharedPreferencesUtil.getDefault().put(BaseSpConstant.SYSTEM_INFO, gson.toJson(systemInfo));
        }
    }
}
