package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/24.
 */

public class TrainingTypeCountModel implements BaseData{

    private int actionCount;

    private TrainingTypeModel trainType;


    @Override
    public String getDisplayTitle() {
        return null;
    }

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public TrainingTypeModel getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainingTypeModel trainType) {
        this.trainType = trainType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.actionCount);
        dest.writeParcelable(this.trainType, flags);
    }

    public TrainingTypeCountModel() {
    }

    protected TrainingTypeCountModel(Parcel in) {
        this.actionCount = in.readInt();
        this.trainType = in.readParcelable(TrainingTypeModel.class.getClassLoader());
    }

    public static final Creator<TrainingTypeCountModel> CREATOR = new Creator<TrainingTypeCountModel>() {
        @Override
        public TrainingTypeCountModel createFromParcel(Parcel source) {
            return new TrainingTypeCountModel(source);
        }

        @Override
        public TrainingTypeCountModel[] newArray(int size) {
            return new TrainingTypeCountModel[size];
        }
    };
}
