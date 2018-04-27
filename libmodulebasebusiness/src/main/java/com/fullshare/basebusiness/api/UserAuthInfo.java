package com.fullshare.basebusiness.api;

import android.text.TextUtils;

import com.common.basecomponent.util.SharedPreferencesUtil;
import com.fullshare.basebusiness.constants.SpConstant;
import com.google.gson.Gson;

/**
 * Created by wuxiaowei on 2018/4/23.
 */

public class UserAuthInfo {
    private static UserAuthInfo userAuthInfo;

    private String token="";

    private long coachId;

    private int loginType;

    public UserAuthInfo setLoginType(int loginType) {
        this.loginType = loginType;
        return userAuthInfo;
    }

    public int getLoginType() {
        return loginType;
    }

    public String getToken() {
        return token;
    }

    public UserAuthInfo setCoachId(long coachId) {
        this.coachId = coachId;
        return userAuthInfo;
    }

    public long getCoachId() {
        return coachId;
    }

    public UserAuthInfo setToken(String token) {
        this.token = token;
        return userAuthInfo;
    }

    public UserAuthInfo setTokenCoachId(String token,long coachId) {
        this.token = token;
        this.coachId = coachId;
        return userAuthInfo;
    }

    private UserAuthInfo(){}

    public static UserAuthInfo getUserAuthInfo() {
        if (userAuthInfo == null) {
            String userInfo = (String) SharedPreferencesUtil.getDefault().get(SpConstant.USER_AUTH_INFO, "");
            if (!TextUtils.isEmpty(userInfo)) {
                Gson gson = new Gson();
                userAuthInfo = gson.fromJson(userInfo, UserAuthInfo.class);
            } else {
                userAuthInfo = new UserAuthInfo();
            }
        }
        return userAuthInfo;
    }

    public  void update() {
        if (userAuthInfo != null) {
            Gson gson = new Gson();
            SharedPreferencesUtil.getDefault().put(SpConstant.USER_AUTH_INFO, gson.toJson(userAuthInfo));
        }
    }
}
