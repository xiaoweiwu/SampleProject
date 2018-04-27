package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/25.
 */

public class MemberModel implements BaseData{

    private String memberAvatarUrl;
    private String memberCode;
    private int memberCreatecoach;
    private String memberCreatedate;
    private String memberId;
    private String memberMobile;
    private String memberName;
    private String memberNickname;
    private int memberSex;
    private int memberType;
    private int memberUpdatecoach;
    private String memberUpdatedate;
    private int registerType;

    @Override
    public String getDisplayTitle() {
        return null;
    }

    public String getMemberAvatarUrl() {
        return memberAvatarUrl;
    }

    public void setMemberAvatarUrl(String memberAvatarUrl) {
        this.memberAvatarUrl = memberAvatarUrl;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public int getMemberCreatecoach() {
        return memberCreatecoach;
    }

    public void setMemberCreatecoach(int memberCreatecoach) {
        this.memberCreatecoach = memberCreatecoach;
    }

    public String getMemberCreatedate() {
        return memberCreatedate;
    }

    public void setMemberCreatedate(String memberCreatedate) {
        this.memberCreatedate = memberCreatedate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public int getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(int memberSex) {
        this.memberSex = memberSex;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public int getMemberUpdatecoach() {
        return memberUpdatecoach;
    }

    public void setMemberUpdatecoach(int memberUpdatecoach) {
        this.memberUpdatecoach = memberUpdatecoach;
    }

    public String getMemberUpdatedate() {
        return memberUpdatedate;
    }

    public void setMemberUpdatedate(String memberUpdatedate) {
        this.memberUpdatedate = memberUpdatedate;
    }

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.memberAvatarUrl);
        dest.writeString(this.memberCode);
        dest.writeInt(this.memberCreatecoach);
        dest.writeString(this.memberCreatedate);
        dest.writeString(this.memberId);
        dest.writeString(this.memberMobile);
        dest.writeString(this.memberName);
        dest.writeString(this.memberNickname);
        dest.writeInt(this.memberSex);
        dest.writeInt(this.memberType);
        dest.writeInt(this.memberUpdatecoach);
        dest.writeString(this.memberUpdatedate);
        dest.writeInt(this.registerType);
    }
    public MemberModel(String memberName) {
        this.memberName = memberName;
    }
    public MemberModel() {
    }

    protected MemberModel(Parcel in) {
        this.memberAvatarUrl = in.readString();
        this.memberCode = in.readString();
        this.memberCreatecoach = in.readInt();
        this.memberCreatedate = in.readString();
        this.memberId = in.readString();
        this.memberMobile = in.readString();
        this.memberName = in.readString();
        this.memberNickname = in.readString();
        this.memberSex = in.readInt();
        this.memberType = in.readInt();
        this.memberUpdatecoach = in.readInt();
        this.memberUpdatedate = in.readString();
        this.registerType = in.readInt();
    }

    public static final Creator<MemberModel> CREATOR = new Creator<MemberModel>() {
        @Override
        public MemberModel createFromParcel(Parcel source) {
            return new MemberModel(source);
        }

        @Override
        public MemberModel[] newArray(int size) {
            return new MemberModel[size];
        }
    };
}
