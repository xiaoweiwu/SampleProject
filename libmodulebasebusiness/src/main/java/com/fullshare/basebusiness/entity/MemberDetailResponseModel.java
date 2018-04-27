package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/25.
 */

public class MemberDetailResponseModel implements BaseData {

    private boolean isPlaned;

    private MemberModel memberInfo;

    private MemberCardModel memberCardInfo;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isPlaned ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.memberInfo, flags);
        dest.writeParcelable(this.memberCardInfo, flags);
    }

    public MemberDetailResponseModel() {
    }

    protected MemberDetailResponseModel(Parcel in) {
        this.isPlaned = in.readByte() != 0;
        this.memberInfo = in.readParcelable(MemberModel.class.getClassLoader());
        this.memberCardInfo = in.readParcelable(MemberCardModel.class.getClassLoader());
    }

    public static final Creator<MemberDetailResponseModel> CREATOR = new Creator<MemberDetailResponseModel>() {
        @Override
        public MemberDetailResponseModel createFromParcel(Parcel source) {
            return new MemberDetailResponseModel(source);
        }

        @Override
        public MemberDetailResponseModel[] newArray(int size) {
            return new MemberDetailResponseModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }

    public boolean isPlaned() {
        return isPlaned;
    }

    public void setPlaned(boolean planed) {
        isPlaned = planed;
    }

    public MemberModel getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberModel memberInfo) {
        this.memberInfo = memberInfo;
    }

    public MemberCardModel getMemberCardInfo() {
        return memberCardInfo;
    }

    public void setMemberCardInfo(MemberCardModel memberCardInfo) {
        this.memberCardInfo = memberCardInfo;
    }
}
