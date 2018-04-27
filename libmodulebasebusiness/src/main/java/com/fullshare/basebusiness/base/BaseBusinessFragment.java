package com.fullshare.basebusiness.base;

import android.view.View;

import com.common.basecomponent.fragment.BaseFragment;
import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.fullshare.basebusiness.R;
import com.fullshare.basebusiness.widget.LoadingLayout;

import common.service.StatisticsManager;

/**
 * Created by wuxiaowei on 2017/5/12.
 */

public abstract class BaseBusinessFragment extends BaseFragment {

    @Override
    protected void preInit(View v) {
        super.preInit(v);
        if (getToolBarEx() != null) {
//            getToolBarEx().setDividerVisiblity(View.INVISIBLE);
            getToolBarEx().setTitleCenter(true).getBackImageView()
                    .setImageResource(R.drawable.btn_back_selector);
            getToolBarEx().setWhiteBackRes(R.drawable.btn_back_white_selector);
        }
    }
    @Override
    protected ALoadingView createLoadingView() {
        return new LoadingLayout(mContext);
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
