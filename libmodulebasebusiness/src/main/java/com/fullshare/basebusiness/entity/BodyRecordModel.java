package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/25.
 */

public class BodyRecordModel implements BaseData {


    /**
     * bodyArmSize : 0
     * bodyBasalMetabolism : 0
     * bodyBiglegSize : 0
     * bodyChestSize : 0
     * bodyCreatecoach : 0
     * bodyCreatedate : 2018-04-25T01:33:19.263Z
     * bodyFatPreci : 0
     * bodyHight : 0
     * bodyHipsSize : 0
     * bodyId : 0
     * bodyMemberId : 0
     * bodyMuscle : 0
     * bodySmalllegSize : 0
     * bodyWaistSize : 0
     * bodyWeight : 0
     */

    private int bodyArmSize;
    private int bodyBasalMetabolism;
    private int bodyBiglegSize;
    private int bodyChestSize;
    private int bodyCreatecoach;
    private String bodyCreatedate;
    private int bodyFatPreci;
    private int bodyHight;
    private int bodyHipsSize;
    private String bodyId;
    private String bodyMemberId;
    private int bodyMuscle;
    private int bodySmalllegSize;
    private int bodyWaistSize;
    private int bodyWeight;

    public int getBodyArmSize() {
        return bodyArmSize;
    }

    public void setBodyArmSize(int bodyArmSize) {
        this.bodyArmSize = bodyArmSize;
    }

    public int getBodyBasalMetabolism() {
        return bodyBasalMetabolism;
    }

    public void setBodyBasalMetabolism(int bodyBasalMetabolism) {
        this.bodyBasalMetabolism = bodyBasalMetabolism;
    }

    public int getBodyBiglegSize() {
        return bodyBiglegSize;
    }

    public void setBodyBiglegSize(int bodyBiglegSize) {
        this.bodyBiglegSize = bodyBiglegSize;
    }

    public int getBodyChestSize() {
        return bodyChestSize;
    }

    public void setBodyChestSize(int bodyChestSize) {
        this.bodyChestSize = bodyChestSize;
    }

    public int getBodyCreatecoach() {
        return bodyCreatecoach;
    }

    public void setBodyCreatecoach(int bodyCreatecoach) {
        this.bodyCreatecoach = bodyCreatecoach;
    }

    public String getBodyCreatedate() {
        return bodyCreatedate;
    }

    public void setBodyCreatedate(String bodyCreatedate) {
        this.bodyCreatedate = bodyCreatedate;
    }

    public int getBodyFatPreci() {
        return bodyFatPreci;
    }

    public void setBodyFatPreci(int bodyFatPreci) {
        this.bodyFatPreci = bodyFatPreci;
    }

    public int getBodyHight() {
        return bodyHight;
    }

    public void setBodyHight(int bodyHight) {
        this.bodyHight = bodyHight;
    }

    public int getBodyHipsSize() {
        return bodyHipsSize;
    }

    public void setBodyHipsSize(int bodyHipsSize) {
        this.bodyHipsSize = bodyHipsSize;
    }

    public String getBodyId() {
        return bodyId;
    }

    public void setBodyId(String bodyId) {
        this.bodyId = bodyId;
    }

    public String getBodyMemberId() {
        return bodyMemberId;
    }

    public void setBodyMemberId(String bodyMemberId) {
        this.bodyMemberId = bodyMemberId;
    }

    public int getBodyMuscle() {
        return bodyMuscle;
    }

    public void setBodyMuscle(int bodyMuscle) {
        this.bodyMuscle = bodyMuscle;
    }

    public int getBodySmalllegSize() {
        return bodySmalllegSize;
    }

    public void setBodySmalllegSize(int bodySmalllegSize) {
        this.bodySmalllegSize = bodySmalllegSize;
    }

    public int getBodyWaistSize() {
        return bodyWaistSize;
    }

    public void setBodyWaistSize(int bodyWaistSize) {
        this.bodyWaistSize = bodyWaistSize;
    }

    public int getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(int bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bodyArmSize);
        dest.writeInt(this.bodyBasalMetabolism);
        dest.writeInt(this.bodyBiglegSize);
        dest.writeInt(this.bodyChestSize);
        dest.writeInt(this.bodyCreatecoach);
        dest.writeString(this.bodyCreatedate);
        dest.writeInt(this.bodyFatPreci);
        dest.writeInt(this.bodyHight);
        dest.writeInt(this.bodyHipsSize);
        dest.writeString(this.bodyId);
        dest.writeString(this.bodyMemberId);
        dest.writeInt(this.bodyMuscle);
        dest.writeInt(this.bodySmalllegSize);
        dest.writeInt(this.bodyWaistSize);
        dest.writeInt(this.bodyWeight);
    }

    public BodyRecordModel() {
    }

    protected BodyRecordModel(Parcel in) {
        this.bodyArmSize = in.readInt();
        this.bodyBasalMetabolism = in.readInt();
        this.bodyBiglegSize = in.readInt();
        this.bodyChestSize = in.readInt();
        this.bodyCreatecoach = in.readInt();
        this.bodyCreatedate = in.readString();
        this.bodyFatPreci = in.readInt();
        this.bodyHight = in.readInt();
        this.bodyHipsSize = in.readInt();
        this.bodyId = in.readString();
        this.bodyMemberId = in.readString();
        this.bodyMuscle = in.readInt();
        this.bodySmalllegSize = in.readInt();
        this.bodyWaistSize = in.readInt();
        this.bodyWeight = in.readInt();
    }

    public static final Creator<BodyRecordModel> CREATOR = new Creator<BodyRecordModel>() {
        @Override
        public BodyRecordModel createFromParcel(Parcel source) {
            return new BodyRecordModel(source);
        }

        @Override
        public BodyRecordModel[] newArray(int size) {
            return new BodyRecordModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
