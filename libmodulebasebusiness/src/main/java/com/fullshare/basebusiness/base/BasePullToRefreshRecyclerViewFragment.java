package com.fullshare.basebusiness.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.basecomponent.entity.BaseData;
import com.common.basecomponent.exception.ErrorType;
import com.common.basecomponent.fragment.PullToRefreshRecycleFragmentEx;
import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.common.basecomponent.fragment.refresh.LoadErrorType;
import com.common.basecomponent.util.L;
import com.common.basecomponent.widget.BasePtrUIHandler;
import com.fullshare.basebusiness.R;
import com.fullshare.basebusiness.net.CommonHttpRequest;
import com.fullshare.basebusiness.net.HttpService;
import com.fullshare.basebusiness.net.OnResponseCallback;
import com.fullshare.basebusiness.net.ResponseStatus;
import com.fullshare.basebusiness.widget.CustomPtrHeader;
import com.fullshare.basebusiness.widget.LoadingLayout;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import common.service.StatisticsManager;

/**
 * Created by wuxiaowei on 2017/3/21.
 */

public abstract class BasePullToRefreshRecyclerViewFragment extends PullToRefreshRecycleFragmentEx {
    @Override
    protected void preInit(View v) {
        super.preInit(v);
        if (getToolBarEx() != null) {
            getToolBarEx().setDividerVisiblity(View.INVISIBLE);
            getToolBarEx().setTitleCenter(true).getBackImageView().setImageResource(R.drawable.btn_back_selector);
            getToolBarEx().setWhiteBackRes(R.drawable.btn_back_white_selector);
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
            page = getRefreshData().getNextPage();
        }
        final CommonHttpRequest request = new CommonHttpRequest.Builder()
                .fullUrl(getRefreshData().getPath() + "")
                .addbody("pageSize", getRefreshData().getPageNum() + "")
                .addbody("currentPage", page + "")
                .addBodyMap(addCustomParam())
                .build();
        OnResponseCallback callback = getRealCallback(isLoadingMore);
        HttpService.request(getActivity(), BasePullToRefreshRecyclerViewFragment.this, request, callback);
    }

    public OnResponseCallback getRealCallback(final boolean isLoadingMore) {
        OnResponseCallback callback = null;
        if (getSettingOptions().isShouldGetRealListData()) {
            callback = new OnResponseCallback<BaseData>(initGsonTypeRequired()) {

                @Override
                public void onStart() {
                    // 设置开始加载为true
                    getRefreshData().setLoading(true);
                    if (!isLoadingMore && !getSettingOptions().isPullRefreshEnable()) {
                        getLoadingViewController().showLoading();
                    }
                }

                @Override
                public void onSuccess(final BaseData result) {
                    List<? extends BaseData> realListData = getRealListData(result);
                    BasePullToRefreshRecyclerViewFragment.this.onSuccess(realListData, isLoadingMore);
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
                    BasePullToRefreshRecyclerViewFragment.this.onFinish(isResponseSuccess, isLoadingMore);
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
                    BasePullToRefreshRecyclerViewFragment.this.onSuccess(result, isLoadingMore);
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
                    BasePullToRefreshRecyclerViewFragment.this.onFinish(isResponseSuccess, isLoadingMore);
                }
            };
        }
        return callback;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_single_refresh_list2;
    }

    @Override
    public HashMap<String, Object> addCustomParam() {
        return null;
    }

    @Override
    protected Type initGsonTypeRequired() {
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

    public RecyclerView.LayoutManager layoutManager() {
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                    try {
                        super.onLayoutChildren(recycler, state);
                    } catch (IndexOutOfBoundsException e) {
                        L.e(e, "IndexOutOfBoundsException");
                        StatisticsManager.reportError(mContext, e);
                    }
                }
            };
        }
        return linearLayoutManager;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getSettingOptions().isStatisticsEnable()) {
            StatisticsManager.onPageStart(getClass().getSimpleName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getSettingOptions().isStatisticsEnable()) {
            StatisticsManager.onPageEnd(getClass().getSimpleName());
        }
    }
}
