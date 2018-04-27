package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/20.
 */

public class TrainingTargetModel implements BaseData {

    private String targetTypeName;

    private long targetId;


    @Override
    public String getDisplayTitle() {
        return targetTypeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.targetTypeName);
        dest.writeLong(this.targetId);
    }

    public TrainingTargetModel() {
    }

    public TrainingTargetModel(String targetType, long targetId) {
        this.targetTypeName = targetType;
        this.targetId = targetId;
    }

    protected TrainingTargetModel(Parcel in) {
        this.targetTypeName = in.readString();
        this.targetId = in.readLong();
    }

    public static final Creator<TrainingTargetModel> CREATOR = new Creator<TrainingTargetModel>() {
        @Override
        public TrainingTargetModel createFromParcel(Parcel source) {
            return new TrainingTargetModel(source);
        }

        @Override
        public TrainingTargetModel[] newArray(int size) {
            return new TrainingTargetModel[size];
        }
    };

    public String getTargetTypeName() {
        return targetTypeName;
    }

    public void setTargetTypeName(String targetTypeName) {
        this.targetTypeName = targetTypeName;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }
}
