package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaowei on 2018/4/22.
 */

public class TrainingProgramModel implements BaseData {

    private List<Integer>actionList;

    public List<Integer> getActionList() {
        return actionList;
    }

    public void setActionList(List<Integer> actionList) {
        this.actionList = actionList;
    }

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
        dest.writeList(this.actionList);
    }

    public TrainingProgramModel() {
    }

    protected TrainingProgramModel(Parcel in) {
        this.actionList = new ArrayList<Integer>();
        in.readList(this.actionList, Integer.class.getClassLoader());
    }

    public static final Creator<TrainingProgramModel> CREATOR = new Creator<TrainingProgramModel>() {
        @Override
        public TrainingProgramModel createFromParcel(Parcel source) {
            return new TrainingProgramModel(source);
        }

        @Override
        public TrainingProgramModel[] newArray(int size) {
            return new TrainingProgramModel[size];
        }
    };
}
