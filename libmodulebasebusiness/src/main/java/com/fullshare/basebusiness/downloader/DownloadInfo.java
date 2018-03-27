package com.fullshare.basebusiness.downloader;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuxiaowei on 2017/4/18.
 */

class DownloadInfo implements Parcelable {
    public static final Creator<DownloadInfo> CREATOR = new Creator<DownloadInfo>() {
        @Override
        public DownloadInfo createFromParcel(Parcel in) {
            return new DownloadInfo(in);
        }

        @Override
        public DownloadInfo[] newArray(int size) {
            return new DownloadInfo[size];
        }
    };
    private String url;
    private boolean showNotification;
    private int notifyType;
    private int notifyId;
    private int progress;
    private String showName;
    private String saveName;
    private String savePath;
    private String fullFileName;
    private int action;
    private String taskId;

    public DownloadInfo() {
    }

    protected DownloadInfo(Parcel in) {
        url = in.readString();
        showNotification = in.readByte() != 0;
        notifyType = in.readInt();
        notifyId = in.readInt();
        progress = in.readInt();
        showName = in.readString();
        savePath = in.readString();
        fullFileName = in.readString();
        saveName = in.readString();
        action = in.readInt();
        taskId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeByte((byte) (showNotification ? 1 : 0));
        dest.writeInt(notifyType);
        dest.writeInt(notifyId);
        dest.writeInt(progress);
        dest.writeString(showName);
        dest.writeString(savePath);
        dest.writeString(fullFileName);
        dest.writeString(saveName);
        dest.writeInt(action);
        dest.writeString(taskId);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getShowName() {
        return showName == null ? "" : showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getFullFileName() {
        fullFileName = savePath + saveName;
        return fullFileName;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public void setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
    }

}
