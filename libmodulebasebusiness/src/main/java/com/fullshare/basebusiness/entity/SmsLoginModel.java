package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/23.
 */

public class SmsLoginModel implements BaseData{
    private String authorization;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Override
    public String getDisplayTitle() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.authorization);
    }

    public SmsLoginModel() {
    }

    protected SmsLoginModel(Parcel in) {
        this.authorization = in.readString();
    }

    public static final Creator<SmsLoginModel> CREATOR = new Creator<SmsLoginModel>() {
        @Override
        public SmsLoginModel createFromParcel(Parcel source) {
            return new SmsLoginModel(source);
        }

        @Override
        public SmsLoginModel[] newArray(int size) {
            return new SmsLoginModel[size];
        }
    };
}
