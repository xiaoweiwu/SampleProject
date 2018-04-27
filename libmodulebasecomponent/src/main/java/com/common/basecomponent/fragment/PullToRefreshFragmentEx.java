package com.common.basecomponent.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.common.basecomponent.R;
import com.common.basecomponent.constant.BaseConstant;
import com.common.basecomponent.entity.BaseData;
import com.common.basecomponent.fragment.base.BaseRefreshData;
import com.common.basecomponent.fragment.base.IRequestRefreshList;
import com.common.basecomponent.fragment.refresh.ILoadActiom;
import com.common.basecomponent.fragment.refresh.ILoadState;
import com.common.basecomponent.fragment.refresh.LoadMoreHelper;
import com.common.basecomponent.fragment.refresh.PulltoRefreshUtils;
import com.common.basecomponent.widget.BasePtrUIHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Vv
 * 2016/10/20 0020.
 * ApiVersion 1.0
 * Description：
 */

public abstract class PullToRefreshFragmentEx extends BaseFragment
        implements IRequestRefreshList, ILoadState, ILoadActiom {

    public static final int LIST_PAGE_SIZE = 10;
    //几屏后显示回到顶部
    public static final int SHOWTOP_BY_SCRENN = 3;

    protected PtrClassicFrameLayout mPtrFrameLayout; //下拉刷新控件
    /**
     * 刷新数据的对象
     */
    private BaseRefreshData mRefreshData;


    @Override
    protected void initViewsAndEvents(View rootView) {
        initRefreshView(rootView);
        initLoadingView(rootView);
        getLoadingViewController().showLoading();
    }

    private void initLoadingView(View rootView) {
        getLoadingViewController().setLoadingActionListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onReload();
            }
        });
    }


    private void initRefreshView(View rootView) {
        mPtrFrameLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.id_ptrframe);
        if (mPtrFrameLayout != null) {
            PulltoRefreshUtils.init(getActivity(), mPtrFrameLayout);
            BasePtrUIHandler ptrUIHandler = createCustomPtrHeader();
            if (ptrUIHandler != null) {
                mPtrFrameLayout.setHeaderView(ptrUIHandler);
                mPtrFrameLayout.addPtrUIHandler(ptrUIHandler);
            }
            //处理ViewPager滑动冲突:
            mPtrFrameLayout.disableWhenHorizontalMove(true);
            mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    if (getSettingOptions().isPullRefreshEnable()) {
                        loadData(false, true);
                    }
                }

                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    if (getSettingOptions().isPullRefreshEnable()) {
                        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                    } else {
                        return false;
                    }
                }
            });
        }
    }
    @Override
    public void addCustomParam(HashMap<String, Object> param) {

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mRefreshData = new BaseRefreshData();
        // 设置当前页数
        mRefreshData.setCurrentPage(BaseConstant.FIRST_PAGE_INDEX);
        // 设置最后更新时间
        mRefreshData.setLastUpdateTime("");
        // 设置正在加载为false
        mRefreshData.setLoading(false);
        // 设置还有更多数据
        mRefreshData.setNoMoreData(false);
        // 设置分页加载数
        mRefreshData.setPageNum(BaseConstant.PAGE_NUM);

    }

    @Override
    protected void onSaveCache() {
        super.onSaveCache();
        getCacheBundle().putParcelable("refreshData", getRefreshData());
    }

    @Override
    protected void onCacheCome(Bundle bundle) {
        super.onCacheCome(bundle);
        mRefreshData = bundle.getParcelable("refreshData");
    }

    @Override
    protected final void onLoadData() {
        if (getSettingOptions().isAutoLoadDataOnPullRefreshEnable() && getSettingOptions().isPullRefreshEnable()) {
            pullRefreshLoad();
        } else {
            loadData(false, false);
        }
    }

    @Override
    public void pullRefreshLoad() {
        if (mPtrFrameLayout != null) {
            long now = SystemClock.currentThreadTimeMillis();
            mPtrFrameLayout.setEnabled(true);
            MotionEvent obtain = MotionEvent.obtain(now, now, MotionEvent.ACTION_CANCEL, 0, 0, 0);
            mPtrFrameLayout.dispatchTouchEvent(obtain);
            mPtrFrameLayout.autoRefresh();
        }
    }

    @Override
    public void onLoadMore() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                loadData(true, false);
            }
        }, 1000);

    }

    @Override
    public void onReload() {
        onLoadData();
    }

    protected void loadData(final boolean loadingMore, boolean pullRefresh) {

    }

    public void onItemClicked(View view, int position,Object o) {

    }

    @Override
    public void notifyResult(List resultModels, boolean loadMore) {
        if (resultModels == null) {
            resultModels = new ArrayList();
        }
        if (loadMore) {
            append(resultModels);
        } else {
            notifyDataSetChanged(resultModels);
        }
    }

    @Override
    public void onLoadComplete(boolean hasMore) {
        if (getCount() == 0) {
            getLoadingViewController().showNoData();
        } else {
            getLoadingViewController().hideLoading();
            if (getSettingOptions().isLoadMoreEnable()) {
                onLoadMoreComplete(hasMore);
            }
        }

    }

    @Override
    public void onLoadFailed() {
        if (getCount() == 0) {
            getLoadingViewController().showLoadingError();
        } else {
            showRefreshErrorNotify();
        }
    }

    /**
     * 显示下拉失败的错误信息
     */
    protected void showRefreshErrorNotify() {

    }

    @Override
    public void onLoadMoreComplete(boolean hasMore) {
        if (hasMore) {
            getLoadingViewController().getLoadMoreHelper().setPullUpLoading(LoadMoreHelper.PULL_UP_STATE_NORMAL, getLoadingViewController().getFootType());
        } else {
            getLoadingViewController().getLoadMoreHelper().setPullUpLoading(LoadMoreHelper.PULL_UP_STATE_NOMORE, getLoadingViewController().getFootType());
        }
    }


    @Override
    public void onLoadMoreFailed() {
        getLoadingViewController().getLoadMoreHelper().setPullUpLoading(LoadMoreHelper.PULL_UP_STATE_EEROR);
    }


    protected void refreshComplete() {
        if (mPtrFrameLayout != null) {
            mPtrFrameLayout.refreshComplete();
            long now = SystemClock.currentThreadTimeMillis();
            MotionEvent obtain = MotionEvent.obtain(now, now, MotionEvent.ACTION_CANCEL, 0, 0, 0);
            mPtrFrameLayout.dispatchTouchEvent(obtain);
        }
    }

    @Override
    public BaseRefreshData getRefreshData() {
        return mRefreshData;
    }

    protected BasePtrUIHandler createCustomPtrHeader() {
        return null;
    }

    protected List<? extends BaseData> getRealListData(Object datas) {
        return null;
    }

    protected void performTestData(boolean isLoadingMore) {
        List result = initTestData(isLoadingMore);
        onSuccess(result, isLoadingMore);
    }

    protected final void updatePageData(boolean isLoadingMore) {
        if (isLoadingMore) {
            getRefreshData().addPage();
        } else {
            getRefreshData().setCurrentPage(1);
        }
    }


    protected void onSuccess(final List<? extends BaseData> result, boolean isLoadingMore) {
        notifyResult(result, isLoadingMore);
        updatePageData(isLoadingMore);
        if (isLoadingMore) {
            if (!getRefreshData().isForceNoPage() && hasMorePage(result)) {
                onLoadMoreComplete(true);
            } else {
                onLoadMoreComplete(false);
                getRefreshData().setNoMoreData(true);
            }
        } else {

            postDelay(new Runnable() {
                @Override
                public void run() {
                    onLoadComplete(hasMorePage(result));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshComplete();
                        }
                    }, 100);

                }
            }, 500);

        }
    }

    protected void onFinish(boolean isResponseSuccess, boolean isLoadingMore) {
        refreshComplete();
        if (isLoadingMore) {
            if (!isResponseSuccess) {
                onLoadMoreFailed();
            }
        } else {
            if (!isResponseSuccess) {
                onLoadFailed();
            } else {

            }
        }
        // 设置加载完成
        getRefreshData().setLoading(false);
    }

    protected abstract int getCount();

    protected abstract void append(List resultModels);

    protected abstract void notifyDataSetChanged(List resultModels);

    /**
     * 由于Gson存在泛型擦除，需要自己返回type;
     * http://www.w2bc.com/Article/22805
     * http://stackoverflow.com/questions/20773850/gson-typetoken-with-dynamic-arraylist-item-type
     *
     * @return
     */
    protected abstract Type initGsonTypeRequired();

    protected boolean hasMorePage(List result) {
//        return result!=null&&result.size() >= getRefreshData().getPageNum();
        return result != null && result.size() > 0;
    }

    /**
     * 想要添加本地测试数据，重写此方法和testOpen方法，则请求不会走http返回的数据
     *
     * @return
     */
    public List initTestData(boolean isLoadingMore) {
        return null;
    }

    /**
     * 测试数据开启与initTestData配合使用
     *
     * @return
     */

    protected void onDataSuccess(boolean loadingMore, List<? extends BaseData> result) {
    }
}
