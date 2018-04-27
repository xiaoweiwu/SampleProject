package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/23.
 */

public class TrainingActionInputModel implements BaseData {
    
    private String weight;
    
    private String count;
    
    private String unit;

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String getDisplayTitle() {
        return null;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weight);
        dest.writeString(this.count);
        dest.writeString(this.unit);
    }

    public TrainingActionInputModel() {
    }

    protected TrainingActionInputModel(Parcel in) {
        this.weight = in.readString();
        this.count = in.readString();
        this.unit = in.readString();
    }

    public static final Creator<TrainingActionInputModel> CREATOR = new Creator<TrainingActionInputModel>() {
        @Override
        public TrainingActionInputModel createFromParcel(Parcel source) {
            return new TrainingActionInputModel(source);
        }

        @Override
        public TrainingActionInputModel[] newArray(int size) {
            return new TrainingActionInputModel[size];
        }
    };
}
