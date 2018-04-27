package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/26.
 */

public class BodyRecordValueModel implements BaseData {

    /**
     * bodyDetailId : 0
     * bodyMemberId : 0
     * bodyPartType : 0
     * bodyPartUnit : string
     * bodyPartValue : 0
     * createCoach : string
     * createDate : 2018-04-26T03:49:46.243Z
     */

    private String bodyDetailId;
    private String bodyMemberId;
    private int bodyPartType;
    private String bodyPartUnit;
    private double bodyPartValue;
    private String createCoach;
    private String createDate;

    public String getBodyDetailId() {
        return bodyDetailId;
    }

    public void setBodyDetailId(String bodyDetailId) {
        this.bodyDetailId = bodyDetailId;
    }

    public String getBodyMemberId() {
        return bodyMemberId;
    }

    public void setBodyMemberId(String bodyMemberId) {
        this.bodyMemberId = bodyMemberId;
    }

    public int getBodyPartType() {
        return bodyPartType;
    }

    public void setBodyPartType(int bodyPartType) {
        this.bodyPartType = bodyPartType;
    }

    public String getBodyPartUnit() {
        return bodyPartUnit;
    }

    public void setBodyPartUnit(String bodyPartUnit) {
        this.bodyPartUnit = bodyPartUnit;
    }

    public double getBodyPartValue() {
        return bodyPartValue;
    }

    public void setBodyPartValue(double bodyPartValue) {
        this.bodyPartValue = bodyPartValue;
    }

    public String getCreateCoach() {
        return createCoach;
    }

    public void setCreateCoach(String createCoach) {
        this.createCoach = createCoach;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bodyDetailId);
        dest.writeString(this.bodyMemberId);
        dest.writeInt(this.bodyPartType);
        dest.writeString(this.bodyPartUnit);
        dest.writeDouble(this.bodyPartValue);
        dest.writeString(this.createCoach);
        dest.writeString(this.createDate);
    }

    public BodyRecordValueModel() {
    }

    protected BodyRecordValueModel(Parcel in) {
        this.bodyDetailId = in.readString();
        this.bodyMemberId = in.readString();
        this.bodyPartType = in.readInt();
        this.bodyPartUnit = in.readString();
        this.bodyPartValue = in.readDouble();
        this.createCoach = in.readString();
        this.createDate = in.readString();
    }

    public static final Creator<BodyRecordValueModel> CREATOR = new Creator<BodyRecordValueModel>() {
        @Override
        public BodyRecordValueModel createFromParcel(Parcel source) {
            return new BodyRecordValueModel(source);
        }

        @Override
        public BodyRecordValueModel[] newArray(int size) {
            return new BodyRecordValueModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
