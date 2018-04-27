package com.fullshare.basebusiness.base;

import android.app.Dialog;

import com.common.basecomponent.activity.BaseActivity;
import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.fullshare.basebusiness.R;
import com.fullshare.basebusiness.widget.LoadingDialog;
import com.fullshare.basebusiness.widget.LoadingLayout;

import common.service.StatisticsManager;

//import common.service.StatisticsManager;

/**
 * author: wuxiaowei
 * date : 2017/3/28
 */

public abstract class CommonBaseActivity extends BaseActivity {

    @Override
    protected ALoadingView createLoadingView() {
        return new LoadingLayout(mContext);
    }

    @Override
    public Dialog createLoadingDialog() {
        return new LoadingDialog(mContext);
    }

    @Override
    protected void preInitView() {
//        PushAgent.getInstance(this).onAppStart();
        super.preInitView();
        if (getToolBarEx() != null) {
//            getToolBarEx().setDividerVisiblity(View.INVISIBLE);
            getToolBarEx().getBackImageView().setImageResource(R.drawable.btn_back_selector);
            getToolBarEx().setWhiteBackRes(R.drawable.btn_back_white_selector);
            getToolBarEx().setTitleCenter(true);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        TinkerUtil.setBackground(false);
        if (getSettingOptions().isStatisticsEnable()) {
            StatisticsManager.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        TinkerUtil.setBackground(true);
        if (getSettingOptions().isStatisticsEnable()) {
            StatisticsManager.onPause(this);
        }
    }

}
