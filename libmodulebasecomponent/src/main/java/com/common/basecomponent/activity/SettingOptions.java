package com.common.basecomponent.activity;

/**
 * Created by wuxiaowei on 2017/10/30.
 */

public class SettingOptions {

    private boolean pullRefreshEnable;

    private boolean loadMoreEnable;

    private boolean autoLoadDataOnPullRefreshEnable;

    private boolean testDataEnable;

    private boolean shouldGetRealListData;

    private boolean eventEnable;

    private boolean backToHome;

    private boolean loadingViewEnable;

    private boolean statisticsEnable;


    public SettingOptions() {
        pullRefreshEnable = true;
        loadMoreEnable = true;
        loadingViewEnable = true;
        statisticsEnable = true;
    }

    public boolean isPullRefreshEnable() {
        return pullRefreshEnable;
    }

    public SettingOptions setPullRefreshEnable(boolean pullRefreshEnable) {
        this.pullRefreshEnable = pullRefreshEnable;
        return this;
    }

    public boolean isLoadMoreEnable() {
        return loadMoreEnable;
    }

    public SettingOptions setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
        return this;
    }

    public boolean isAutoLoadDataOnPullRefreshEnable() {
        return autoLoadDataOnPullRefreshEnable;
    }

    public SettingOptions setAutoLoadDataOnPullRefreshEnable(boolean autoLoadDataOnPullRefreshEnable) {
        this.autoLoadDataOnPullRefreshEnable = autoLoadDataOnPullRefreshEnable;
        return this;
    }

    public boolean isTestDataEnable() {
        return testDataEnable;
    }

    public SettingOptions setTestDataEnable(boolean testDataEnable) {
        this.testDataEnable = testDataEnable;
        return this;
    }

    public boolean isShouldGetRealListData() {
        return shouldGetRealListData;
    }

    public SettingOptions setShouldGetRealListData(boolean shouldGetRealListData) {
        this.shouldGetRealListData = shouldGetRealListData;
        return this;
    }

    public boolean isStatisticsEnable() {
        return statisticsEnable;
    }

    public SettingOptions setStatisticsEnable(boolean statisticsEnable) {
        this.statisticsEnable = statisticsEnable;
        return this;
    }

    public boolean isEventEnable() {
        return eventEnable;
    }

    public SettingOptions setEventEnable(boolean eventEnable) {
        this.eventEnable = eventEnable;
        return this;
    }


    public boolean isLoadingViewEnable() {
        return loadingViewEnable;
    }

    public SettingOptions setLoadingViewEnable(boolean loadingViewEnable) {
        this.loadingViewEnable = loadingViewEnable;
        return this;
    }
}
