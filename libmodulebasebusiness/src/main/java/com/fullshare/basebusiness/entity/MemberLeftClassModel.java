package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/25.
 */

public class MemberLeftClassModel implements BaseData {

    MemberModel member;

    MemberCardModel memberCard ;

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public MemberCardModel getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCardModel memberCard) {
        this.memberCard = memberCard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.member, flags);
        dest.writeParcelable(this.memberCard, flags);
    }

    public MemberLeftClassModel() {
    }

    protected MemberLeftClassModel(Parcel in) {
        this.member = in.readParcelable(MemberModel.class.getClassLoader());
        this.memberCard = in.readParcelable(MemberCardModel.class.getClassLoader());
    }

    public static final Creator<MemberLeftClassModel> CREATOR = new Creator<MemberLeftClassModel>() {
        @Override
        public MemberLeftClassModel createFromParcel(Parcel source) {
            return new MemberLeftClassModel(source);
        }

        @Override
        public MemberLeftClassModel[] newArray(int size) {
            return new MemberLeftClassModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
