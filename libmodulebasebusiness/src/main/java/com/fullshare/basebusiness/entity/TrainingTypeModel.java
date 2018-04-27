package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/24.
 */

public class TrainingTypeModel  implements BaseData{

    private long typeId;
    private String typeName;

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.typeId);
        dest.writeString(this.typeName);
    }

    public TrainingTypeModel() {
    }

    protected TrainingTypeModel(Parcel in) {
        this.typeId = in.readLong();
        this.typeName = in.readString();
    }

    public static final Creator<TrainingTypeModel> CREATOR = new Creator<TrainingTypeModel>() {
        @Override
        public TrainingTypeModel createFromParcel(Parcel source) {
            return new TrainingTypeModel(source);
        }

        @Override
        public TrainingTypeModel[] newArray(int size) {
            return new TrainingTypeModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
