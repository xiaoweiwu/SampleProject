package com.common.basecomponent.fragment.refresh;

import android.support.annotation.DrawableRes;
import android.view.View.OnClickListener;

/**
 * Created by wuxiaowei on 2017/1/14.
 */

public interface IBaseLoadingView {
    void showLoading();

    void showLoading(@DrawableRes int drawableRes);

    /**
     * 加载失败时的按钮事件
     *
     * @param clickListener
     */
    void setLoadingActionListener(OnClickListener clickListener);

    void hideLoading();

    void showLoadingError(LoadErrorType errorType);

    void showNoData(EmptyType emptyType);

    void showLoadingDialog(String message);

    void dismissLoadingDialog();

    boolean isShowingLoading();
}
