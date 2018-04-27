package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/25.
 */

public class MemberHeaderModel implements BaseData{


    public static final int TYPE_EXPIRE = 1;

    public static final int TYPE_LEFT_3_CLASS = 2;
    private int count;

    private String title;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MemberHeaderModel(int count, String title,int type) {
        this.count = count;
        this.title = title;
        this.type = type;
    }

    public MemberHeaderModel() {
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
        dest.writeInt(this.count);
        dest.writeString(this.title);
    }

    protected MemberHeaderModel(Parcel in) {
        this.count = in.readInt();
        this.title = in.readString();
    }

    public static final Creator<MemberHeaderModel> CREATOR = new Creator<MemberHeaderModel>() {
        @Override
        public MemberHeaderModel createFromParcel(Parcel source) {
            return new MemberHeaderModel(source);
        }

        @Override
        public MemberHeaderModel[] newArray(int size) {
            return new MemberHeaderModel[size];
        }
    };
}
