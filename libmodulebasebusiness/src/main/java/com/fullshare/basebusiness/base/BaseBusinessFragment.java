package com.fullshare.basebusiness.base;

import com.common.basecomponent.fragment.BaseFragment;
import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.fullshare.basebusiness.widget.LoadingLayout;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by wuxiaowei on 2017/5/12.
 */

public abstract class BaseBusinessFragment extends BaseFragment {


    @Override
    protected ALoadingView createLoadingView() {
        return new LoadingLayout(mContext);
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
