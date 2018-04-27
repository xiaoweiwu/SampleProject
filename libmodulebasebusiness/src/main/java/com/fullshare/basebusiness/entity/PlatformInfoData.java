package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by wuxiaowei on 2017/3/7.
 */

public class PlatformInfoData implements BaseData {
    private String uid;
    private String name;
    private String profileImageUrl;
    private String sex;
    private SHARE_MEDIA type;
    private String accessToken;
    private String openId;

    public static PlatformInfoData initWithResponse(Map<String, String> map, SHARE_MEDIA platform) {
        PlatformInfoData platformInfoData = new PlatformInfoData();
        String uid = "";
        String name = map.get("name");
        String profileImageUrl = map.get("iconurl");
        String sex = map.get("gender");
        String accessToken = map.get("accessToken");
        String openId = null;
        SHARE_MEDIA type = null;
        switch (platform){
            case WEIXIN:
                uid = map.get("uid");
                openId = map.get("openid");
                type = SHARE_MEDIA.WEIXIN;
                break;
            case QQ:
                uid = map.get("uid");
                openId = map.get("openid");
                if ("男".equals(sex)) {
                    sex = "1";
                } else if ("女".equals(sex)) {
                    sex = "2";
                } else {
                    sex = "0";
                }
                type = SHARE_MEDIA.QQ;
                break;
            case SINA:
                uid = map.get("uid");
                type = SHARE_MEDIA.SINA;
                openId = uid;
                break;
        }
        platformInfoData.setAccessToken(accessToken);
        platformInfoData.setUid(uid);
        platformInfoData.setName(name);
        platformInfoData.setProfileImageUrl(profileImageUrl);
        platformInfoData.setSex(sex);
        platformInfoData.setType(type);
        platformInfoData.setOpenId(openId);
        return platformInfoData;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public SHARE_MEDIA getType() {
        return type;
    }

    public void setType(SHARE_MEDIA type) {
        this.type = type;
    }

    public int getSocailType(){
        if (type == SHARE_MEDIA.QQ) {
            return 4;
        }
        if (type == SHARE_MEDIA.WEIXIN) {
            return 3;
        }

        if (type == SHARE_MEDIA.SINA) {
            return 5;
        }
        return 0;
    }

    public String getSocialName(){
        if (type == SHARE_MEDIA.QQ) {
            return "QQ";
        }
        if (type == SHARE_MEDIA.WEIXIN) {
            return "微信";
        }

        if (type == SHARE_MEDIA.SINA) {
            return "新浪";
        }
        return "";
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.sex);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.accessToken);
        dest.writeString(this.openId);
    }

    public PlatformInfoData() {
    }

    protected PlatformInfoData(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.profileImageUrl = in.readString();
        this.sex = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : SHARE_MEDIA.values()[tmpType];
        this.accessToken = in.readString();
        this.openId = in.readString();
    }

    public static final Creator<PlatformInfoData> CREATOR = new Creator<PlatformInfoData>() {
        @Override
        public PlatformInfoData createFromParcel(Parcel source) {
            return new PlatformInfoData(source);
        }

        @Override
        public PlatformInfoData[] newArray(int size) {
            return new PlatformInfoData[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
