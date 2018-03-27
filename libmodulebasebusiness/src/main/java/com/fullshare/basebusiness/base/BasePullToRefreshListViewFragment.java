package com.fullshare.basebusiness.base;

import android.view.View;

import com.common.basecomponent.entity.BaseData;
import com.common.basecomponent.exception.ErrorType;
import com.common.basecomponent.fragment.PullToRefreshListViewFragmentEx;
import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.common.basecomponent.fragment.refresh.LoadErrorType;
import com.common.basecomponent.widget.BasePtrUIHandler;
import com.fullshare.basebusiness.R;
import com.fullshare.basebusiness.net.CommonHttpRequest;
import com.fullshare.basebusiness.net.HttpService;
import com.fullshare.basebusiness.net.OnResponseCallback;
import com.fullshare.basebusiness.net.ResponseStatus;
import com.fullshare.basebusiness.widget.CustomPtrHeader;
import com.fullshare.basebusiness.widget.LoadingLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wuxiaowei on 2017/3/21.
 */

public abstract class BasePullToRefreshListViewFragment extends PullToRefreshListViewFragmentEx {

    @Override
    protected void preInit(View v) {
        super.preInit(v);
        if (getToolBarEx() != null) {
            getToolBarEx().setDividerVisiblity(View.INVISIBLE);
            getToolBarEx().setTitleCenter(true).getBackImageView().setImageResource(R.drawable.btn_back_selector);
        }
    }

    @Override
    protected void loadData(final boolean isLoadingMore, boolean pullRefresh) {
        //开启了本地测试数据，不走http数据
        if (getSettingOptions().isTestDataEnable()) {
            refreshComplete();
            performTestData(isLoadingMore);
            return;
        }
        int page = 1;
        if (isLoadingMore) {
            page = getRefreshData().getCurrentPage() + 1;
        }
        final CommonHttpRequest request = new CommonHttpRequest.Builder()
                .fullUrl(getRefreshData().getPath() + "")
                .addbody("pageSize", getRefreshData().getPageNum() + "")
                .addbody("currentPage", page + "")
                .addBodyMap(addCustomParam())
                .build();
        OnResponseCallback callback = getRealCallback(isLoadingMore);
        HttpService.request(getActivity(), request, callback);
    }


    public OnResponseCallback getRealCallback(final boolean isLoadingMore) {
        OnResponseCallback callback = null;
        if (getSettingOptions().isShouldGetRealListData()) {
            callback = new OnResponseCallback<Object>(initGsonTypeRequired()) {

                @Override
                public void onStart() {
                    // 设置开始加载为true
                    if (!isLoadingMore && getSettingOptions().isPullRefreshEnable()) {
                        getLoadingViewController().showLoading();
                    }
                    getRefreshData().setLoading(true);
                }

                @Override
                public void onSuccess(final Object result) {
                    List<? extends BaseData> realListData = getRealListData(result);
                    BasePullToRefreshListViewFragment.this.onSuccess(realListData, isLoadingMore);
                }

                @Override
                public void onFinish(boolean isResponseSuccess, ResponseStatus responseStatus) {
                    if (!isResponseSuccess) {
                        if (responseStatus.getErrorType() == ErrorType.NETWORK_ERROR) {
                            getLoadingViewController().setErrorType(LoadErrorType.NETWORD_UNAVAILABLE);
                        } else {
                            getLoadingViewController().setErrorType(LoadErrorType.SERVER_ERROR);
                        }
                    }
                    BasePullToRefreshListViewFragment.this.onFinish(isResponseSuccess, isLoadingMore);
                }
            };
        } else {
            callback = new OnResponseCallback<List<BaseData>>(initGsonTypeRequired()) {

                @Override
                public void onStart() {
                    // 设置开始加载为true
                    getRefreshData().setLoading(true);
                    if (!isLoadingMore && !getSettingOptions().isPullRefreshEnable()) {
                        getLoadingViewController().showLoading();
                    }
                }

                @Override
                public void onSuccess(final List<BaseData> result) {
                    BasePullToRefreshListViewFragment.this.onSuccess(result, isLoadingMore);
                    onDataSuccess(isLoadingMore, result);
                }

                @Override
                public void onFinish(boolean isResponseSuccess, ResponseStatus responseStatus) {
                    if (!isResponseSuccess) {
                        if (responseStatus.getErrorType() == ErrorType.NETWORK_ERROR) {
                            getLoadingViewController().setErrorType(LoadErrorType.NETWORD_UNAVAILABLE);
                        } else {
                            getLoadingViewController().setErrorType(LoadErrorType.SERVER_ERROR);
                        }
                    }
                    BasePullToRefreshListViewFragment.this.onFinish(isResponseSuccess, isLoadingMore);
                }
            };
        }
        return callback;
    }

    @Override
    public HashMap<String, Object> addCustomParam() {
        return null;
    }

    @Override
    protected ALoadingView createLoadingView() {
        return new LoadingLayout(mContext);
    }

    @Override
    protected BasePtrUIHandler createCustomPtrHeader() {
        return new CustomPtrHeader(mContext);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getSettingOptions().isStatisticsEnable()) {
            MobclickAgent.onPageStart(getClass().getSimpleName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getSettingOptions().isStatisticsEnable()) {
            MobclickAgent.onPageEnd(getClass().getSimpleName());
        }
    }
}
