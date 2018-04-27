package com.common.basecomponent.fragment.base;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2017/1/14.
 */

public class BaseRefreshData implements BaseData {
    public static final Creator<BaseRefreshData> CREATOR = new Creator<BaseRefreshData>() {
        @Override
        public BaseRefreshData createFromParcel(Parcel source) {
            return new BaseRefreshData(source);
        }

        @Override
        public BaseRefreshData[] newArray(int size) {
            return new BaseRefreshData[size];
        }
    };
    protected boolean forceNoPage;
    /**
     * 最后更新时间
     */
    private String lastUpdateTime;
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 分页加载条数
     */
    private int pageNum;
    /**
     * 是否正在加载
     */
    private boolean isLoading;
    /**
     * 列表是否已无更多数据，即到达最底部
     */
    private boolean isNoMoreData;
    /**
     * 接口访问路径
     */
    private String path;

    public BaseRefreshData() {
    }


    protected BaseRefreshData(Parcel in) {
        this.lastUpdateTime = in.readString();
        this.currentPage = in.readInt();
        this.pageNum = in.readInt();
        this.isLoading = in.readByte() != 0;
        this.isNoMoreData = in.readByte() != 0;
        this.path = in.readString();
        this.forceNoPage = in.readByte() != 0;
    }

    public boolean isForceNoPage() {
        return forceNoPage;
    }

    public void setForceNoPage(boolean forceNoPage) {
        this.forceNoPage = forceNoPage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return currentPage + 1;
    }

    public void addPage() {
        currentPage++;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isNoMoreData() {
        return isNoMoreData;
    }

    public void setNoMoreData(boolean isNoMoreData) {
        this.isNoMoreData = isNoMoreData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lastUpdateTime);
        dest.writeInt(this.currentPage);
        dest.writeInt(this.pageNum);
        dest.writeByte(this.isLoading ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNoMoreData ? (byte) 1 : (byte) 0);
        dest.writeString(this.path);
        dest.writeByte(this.forceNoPage ? (byte) 1 : (byte) 0);
    }

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
