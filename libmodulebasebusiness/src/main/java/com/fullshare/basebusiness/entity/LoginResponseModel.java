package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/24.
 */

public class LoginResponseModel implements BaseData {

    private long coachId;

    private String token;

    public long getCoachId() {
        return coachId;
    }

    public void setCoachId(long coachId) {
        this.coachId = coachId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.coachId);
        dest.writeString(this.token);
    }

    public LoginResponseModel() {
    }

    protected LoginResponseModel(Parcel in) {
        this.coachId = in.readLong();
        this.token = in.readString();
    }

    public static final Creator<LoginResponseModel> CREATOR = new Creator<LoginResponseModel>() {
        @Override
        public LoginResponseModel createFromParcel(Parcel source) {
            return new LoginResponseModel(source);
        }

        @Override
        public LoginResponseModel[] newArray(int size) {
            return new LoginResponseModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
