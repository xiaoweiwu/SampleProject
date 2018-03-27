package com.common.basecomponent.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.common.basecomponent.R;
import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.common.basecomponent.fragment.refresh.EmptyType;
import com.common.basecomponent.fragment.refresh.LoadErrorType;
import com.common.basecomponent.fragment.refresh.LoadMoreFootType;
import com.common.basecomponent.fragment.refresh.LoadMoreHelper;

/**
 * Created by wuxiaowei on 2018/3/23.
 */

public class LoadingViewController {

    private boolean needLoadingView;

    private ALoadingView loadingView;

    private Activity activity;

    private EmptyType emptyType = EmptyType.COMMON;

    private LoadErrorType errorType = LoadErrorType.NETWORD_UNAVAILABLE;

    private Dialog loadingDialog;

    private View maskView;

    private LoadMoreFootType footType = LoadMoreFootType.DEFAULT;

    private LoadMoreHelper loadMoreHelper; // 上拉加载更多帮助支持类


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setLoadingDialog(Dialog loadingDialog) {
        this.loadingDialog = loadingDialog;
    }

    public ALoadingView getLoadingView() {
        return loadingView;
    }

    public void setLoadingView(ALoadingView loadingView) {
        this.loadingView = loadingView;
    }

    public boolean hasLoadingView() {
        return loadingView != null;
    }


    public void setMaskView(View maskView) {
        this.maskView = maskView;
    }

    public void release() {
        loadingView = null;
        activity = null;
        maskView = null;
    }

    public EmptyType getEmptyType() {
        return emptyType;
    }

    public void setEmptyType(EmptyType emptyType) {
        this.emptyType = emptyType;
    }

    public LoadErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(LoadErrorType errorType) {
        this.errorType = errorType;
    }

    /**
     * 加载失败时的按钮事件
     *
     * @param clickListener
     */
    public void setLoadingActionListener(View.OnClickListener clickListener) {
        if (loadingView != null) {
            loadingView.setLoadingActionListener(clickListener);
        }
    }

    public void hideLoading() {
        if (loadingView != null) {
            loadingView.hideLoading();
        }
    }

    public void showLoading() {
        if (loadingView != null) {
            loadingView.showLoading();
        }
    }

    public void showLoading(@DrawableRes int drawableRes) {
        if (loadingView != null) {
            loadingView.showLoading(drawableRes);
        }
    }

    public void showLoadingError() {
        if (loadingView != null) {
            loadingView.showLoadingError(errorType);
        }
    }

    public void showNoData() {
        if (loadingView != null) {
            loadingView.showNoData(emptyType);
        }
    }


    public boolean isShowingLoading() {
        return loadingView != null && loadingView.isShowingLoading();
    }

    public void showLoadingDialog(String message) {
        if (loadingDialog != null && !loadingDialog.isShowing() && !activity.isFinishing()) {
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void initGreyMask() {
        if (maskView == null) {
            maskView = new FrameLayout(activity);
            maskView.setBackgroundColor(activity.getResources().getColor(R.color.black_color_50));
            hideMaskBackground(0);
            activity.addContentView(maskView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public void showMaskBackground(long deayMillis) {
        initGreyMask();
        maskView.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(maskView, "alpha", 0, 1)
                .setDuration(deayMillis);
        objectAnimator.start();
    }

    public void hideMaskBackground(long deayMillis) {
        initGreyMask();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(maskView, "alpha", 1, 0).setDuration(deayMillis);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                maskView.setVisibility(View.GONE);
            }
        });
        objectAnimator.start();
    }

    public boolean isNeedLoadingView() {
        return needLoadingView;
    }

    public void setNeedLoadingView(boolean needLoadingView) {
        this.needLoadingView = needLoadingView;
    }


    public LoadMoreHelper getLoadMoreHelper() {
        return loadMoreHelper;
    }

    public void setLoadMoreHelper(LoadMoreHelper loadMoreHelper) {
        this.loadMoreHelper = loadMoreHelper;
    }

    public LoadMoreFootType getFootType() {
        return footType;
    }

    public void setFootType(LoadMoreFootType footType) {
        this.footType = footType;
    }
}
