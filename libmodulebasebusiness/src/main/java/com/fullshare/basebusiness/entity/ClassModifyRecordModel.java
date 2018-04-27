package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/22.
 */

public class ClassModifyRecordModel implements BaseData{

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

    public ClassModifyRecordModel() {
    }

    protected ClassModifyRecordModel(Parcel in) {
    }

    public static final Creator<ClassModifyRecordModel> CREATOR = new Creator<ClassModifyRecordModel>() {
        @Override
        public ClassModifyRecordModel createFromParcel(Parcel source) {
            return new ClassModifyRecordModel(source);
        }

        @Override
        public ClassModifyRecordModel[] newArray(int size) {
            return new ClassModifyRecordModel[size];
        }
    };
}
