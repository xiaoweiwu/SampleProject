package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

import java.util.List;

/**
 * Created by wuxiaowei on 2018/4/24.
 */

public class TrainTypeResponseModel implements BaseData {

    private List<TrainingTypeCountModel>trainTypeList;

    public List<TrainingTypeCountModel> getTrainTypeList() {
        return trainTypeList;
    }

    public void setTrainTypeList(List<TrainingTypeCountModel> trainTypeList) {
        this.trainTypeList = trainTypeList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.trainTypeList);
    }

    public TrainTypeResponseModel() {
    }

    protected TrainTypeResponseModel(Parcel in) {
        this.trainTypeList = in.createTypedArrayList(TrainingTypeCountModel.CREATOR);
    }

    public static final Creator<TrainTypeResponseModel> CREATOR = new Creator<TrainTypeResponseModel>() {
        @Override
        public TrainTypeResponseModel createFromParcel(Parcel source) {
            return new TrainTypeResponseModel(source);
        }

        @Override
        public TrainTypeResponseModel[] newArray(int size) {
            return new TrainTypeResponseModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
