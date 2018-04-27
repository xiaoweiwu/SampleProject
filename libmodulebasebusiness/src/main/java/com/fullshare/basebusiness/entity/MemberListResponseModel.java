package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

import java.util.List;

/**
 * Created by wuxiaowei on 2018/4/25.
 */

public class MemberListResponseModel implements BaseData {

    private int expiredCount;

    private int remainCount;

    public int getExpiredCount() {
        return expiredCount;
    }

    private List<MemberModel>memberList;

    public int getRemainCount() {
        return remainCount;
    }

    public void setMemberList(List<MemberModel> memberList) {
        this.memberList = memberList;
    }

    public List<MemberModel> getMemberList() {
        return memberList;
    }

    public void setRemainCount(int remainCount) {
        this.remainCount = remainCount;
    }

    public void setExpiredCount(int expiredCount) {
        this.expiredCount = expiredCount;
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
        dest.writeInt(this.expiredCount);
        dest.writeInt(this.remainCount);
        dest.writeTypedList(this.memberList);
    }

    public MemberListResponseModel() {
    }

    protected MemberListResponseModel(Parcel in) {
        this.expiredCount = in.readInt();
        this.remainCount = in.readInt();
        this.memberList = in.createTypedArrayList(MemberModel.CREATOR);
    }

    public static final Creator<MemberListResponseModel> CREATOR = new Creator<MemberListResponseModel>() {
        @Override
        public MemberListResponseModel createFromParcel(Parcel source) {
            return new MemberListResponseModel(source);
        }

        @Override
        public MemberListResponseModel[] newArray(int size) {
            return new MemberListResponseModel[size];
        }
    };
}
