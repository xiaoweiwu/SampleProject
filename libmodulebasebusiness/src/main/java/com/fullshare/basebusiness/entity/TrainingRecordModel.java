package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/22.
 */

public class TrainingRecordModel implements BaseData {
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
    }

    public TrainingRecordModel() {
    }

    protected TrainingRecordModel(Parcel in) {
    }

    public static final Creator<TrainingRecordModel> CREATOR = new Creator<TrainingRecordModel>() {
        @Override
        public TrainingRecordModel createFromParcel(Parcel source) {
            return new TrainingRecordModel(source);
        }

        @Override
        public TrainingRecordModel[] newArray(int size) {
            return new TrainingRecordModel[size];
        }
    };
}
