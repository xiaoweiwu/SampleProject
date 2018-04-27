package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/24.
 */

public class TrainingActionModel implements BaseData{

    /**
     * actionCalorie : 0
     * actionCalorieLevel : 0
     * actionCreatedate : 2018-04-24T03:51:44.921Z
     * actionId : 0
     * actionName : string
     * actionPartId : 0
     * actionTrainType : 0
     * actionType : 0
     * actionUnit : 0
     * actionUpdatedate : 2018-04-24T03:51:44.921Z
     * coachId : 0
     */

    private int actionCalorie;
    private String actionCalorieLevel;
    private String actionCreatedate;
    private String actionId;
    private String actionName;
    private String actionPartId;
    private long actionTrainType;
    private int actionType;
    private int actionUnit;
    private String actionUpdatedate;
    private String coachId;

    public int getActionCalorie() {
        return actionCalorie;
    }

    public void setActionCalorie(int actionCalorie) {
        this.actionCalorie = actionCalorie;
    }

    public String getActionCalorieLevel() {
        return actionCalorieLevel;
    }

    public void setActionCalorieLevel(String actionCalorieLevel) {
        this.actionCalorieLevel = actionCalorieLevel;
    }

    public String getActionCreatedate() {
        return actionCreatedate;
    }

    public void setActionCreatedate(String actionCreatedate) {
        this.actionCreatedate = actionCreatedate;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionPartId() {
        return actionPartId;
    }

    public void setActionPartId(String actionPartId) {
        this.actionPartId = actionPartId;
    }

    public long getActionTrainType() {
        return actionTrainType;
    }

    public void setActionTrainType(long actionTrainType) {
        this.actionTrainType = actionTrainType;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getActionUnit() {
        return actionUnit;
    }

    public void setActionUnit(int actionUnit) {
        this.actionUnit = actionUnit;
    }

    public String getActionUpdatedate() {
        return actionUpdatedate;
    }

    public void setActionUpdatedate(String actionUpdatedate) {
        this.actionUpdatedate = actionUpdatedate;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.actionCalorie);
        dest.writeString(this.actionCalorieLevel);
        dest.writeString(this.actionCreatedate);
        dest.writeString(this.actionId);
        dest.writeString(this.actionName);
        dest.writeString(this.actionPartId);
        dest.writeLong(this.actionTrainType);
        dest.writeInt(this.actionType);
        dest.writeInt(this.actionUnit);
        dest.writeString(this.actionUpdatedate);
        dest.writeString(this.coachId);
    }

    public TrainingActionModel() {
    }

    protected TrainingActionModel(Parcel in) {
        this.actionCalorie = in.readInt();
        this.actionCalorieLevel = in.readString();
        this.actionCreatedate = in.readString();
        this.actionId = in.readString();
        this.actionName = in.readString();
        this.actionPartId = in.readString();
        this.actionTrainType = in.readLong();
        this.actionType = in.readInt();
        this.actionUnit = in.readInt();
        this.actionUpdatedate = in.readString();
        this.coachId = in.readString();
    }

    public static final Creator<TrainingActionModel> CREATOR = new Creator<TrainingActionModel>() {
        @Override
        public TrainingActionModel createFromParcel(Parcel source) {
            return new TrainingActionModel(source);
        }

        @Override
        public TrainingActionModel[] newArray(int size) {
            return new TrainingActionModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
