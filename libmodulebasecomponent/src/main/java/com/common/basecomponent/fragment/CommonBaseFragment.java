package com.common.basecomponent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.basecomponent.activity.SettingOptions;
import com.common.basecomponent.eventbus.BusProvider;
import com.common.basecomponent.eventbus.EventModel;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by fs_wuxiaowei on 2016/9/22.
 */
public abstract class CommonBaseFragment extends RxFragment implements FragmentUserVisibleController.UserVisibleCallback {

    public static final String USE_CACHE = "use_cache";
    /**
     * Log tag
     */
    protected static String TAG_LOG = CommonBaseFragment.class.getSimpleName();
    //根布局
    protected View mRootView;
    /**
     * 表示View是否被初始化
     */
    protected boolean isViewInitiated;
    /**
     * 表示对用户是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 表示数据是否初始化
     */
    protected boolean isDataInitiated;
    protected Context mContext;
    protected LayoutInflater mInflater;
    private Unbinder unbinder;
    private boolean recycleAble;
    private FragmentUserVisibleController userVisibleController;

    private boolean useCache;

    private Bundle savedInstanceState;

    private Bundle cacheBundle;

    private SettingOptions settingOptions;

    public CommonBaseFragment() {
        userVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingOptions = new SettingOptions();
        settingOptions.setStatisticsEnable(false);
        cacheBundle = new Bundle();
        this.savedInstanceState = savedInstanceState;
        if (savedInstanceState != null) {
            useCache = savedInstanceState.getBoolean(USE_CACHE, false);
        }
        initArguments();
        if (settingOptions.isEventEnable()) {
            BusProvider.getInstance().register(this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        if (recycleAble) {
            return inflater.inflate(getLayoutId(), null);
        } else {
            if (mRootView == null) {
                mRootView = inflater.inflate(getLayoutId(), null);
            } else {
                //缓存的mRootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mRootView已经有parent的错误。
                ViewGroup parent = (ViewGroup) mRootView.getParent();
                if (parent != null) {
                    parent.removeView(mRootView);
                }
            }
            return mRootView;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        onCreateView(view);
        isViewInitiated = true;
    }

    private void onCreateView(View view) {
        preInit(view);
        initViewsAndEvents(view);
        initViewAndData(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userVisibleController.activityCreated();
        prepareFetchData();
        if (useCache) {
            if (savedInstanceState != null) {
                cacheBundle.putAll(savedInstanceState);
            }
            onCacheCome(savedInstanceState);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        userVisibleController.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    private boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    /***
     * @param forceUpdate 表示是否在界面可见的时候是否强制刷新数据
     * @return
     */
    private boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            if (!useCache) {
                onLoadData();
            }
            forceUpdate = false;
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    public SettingOptions getSettingOptions() {
        return settingOptions;
    }

    /**
     * 当Fragment可见时调用该方法
     */
    protected void onLoadData() {

    }

    protected void onSaveCache() {

    }

    protected void onCacheCome(Bundle bundle) {

    }

    public void setUseCache(boolean useCache) {
        getCacheBundle().putBoolean(USE_CACHE, useCache);
    }

    public Bundle getCacheBundle() {
        return cacheBundle;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(cacheBundle);
    }

    public boolean isRecycleAble() {
        return recycleAble;
    }

    public void setRecycleAble(boolean recycleAble) {
        this.recycleAble = recycleAble;
    }

    protected abstract void preInit(View v);

    protected abstract void initArguments();

    protected abstract void initViewAndData(View view);

    /**
     * initViewAndData all views and add events
     */
    protected abstract void initViewsAndEvents(View rootView);

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getLayoutId();

    /**
     * when event comming
     *
     * @param eventModel
     */
    public void onMainEvent(EventModel eventModel) {

    }

    @Override
    public boolean isWaitingShowToUser() {
        return userVisibleController.isWaitingShowToUser();
    }

    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        userVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isVisibleToUser() {
        return userVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }


    @Override
    public void onResume() {
        super.onResume();
        userVisibleController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        userVisibleController.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (settingOptions.isEventEnable()) {
            BusProvider.getInstance().unregister(this);
        }
    }


}
