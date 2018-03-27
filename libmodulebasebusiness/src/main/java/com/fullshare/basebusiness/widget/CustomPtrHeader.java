package com.fullshare.basebusiness.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.common.basecomponent.util.UnitConvertUtil;
import com.common.basecomponent.widget.BasePtrUIHandler;
import com.fullshare.basebusiness.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author fs_wuxiaowei
 * @time 2016/10/31 10:12
 * @desc
 */

public class CustomPtrHeader extends BasePtrUIHandler {

    AnimationDrawable loadingDrawable;
    PulltorefreshAnimationView animationView;
    private ImageView imageView;

    public CustomPtrHeader(Context context) {
        super(context);
        initViews(null);
    }

    public CustomPtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public CustomPtrHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_pulltorefreshheader, this);
        animationView = (PulltorefreshAnimationView) header.findViewById(R.id.pull_animation_view);
        imageView = (ImageView) header.findViewById(R.id.iv_ani);
        imageView.setImageResource(R.drawable.pulltorefresh_header);
        loadingDrawable = (AnimationDrawable) imageView.getDrawable();
        resetView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void resetView() {
        imageView.setVisibility(INVISIBLE);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        imageView.setVisibility(INVISIBLE);
        animationView.setVisibility(VISIBLE);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        imageView.setVisibility(VISIBLE);
        animationView.setVisibility(INVISIBLE);
        loadingDrawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        animationView.setVisibility(INVISIBLE);
        imageView.setVisibility(INVISIBLE);
        loadingDrawable.stop();
    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        int aniStartPos = UnitConvertUtil.dip2px(getContext(), 10);
        if (currentPos > aniStartPos) {
            if (currentPos <= mOffsetToRefresh) {
                LayoutParams params = (LayoutParams) animationView.getLayoutParams();
                params.bottomMargin = (currentPos - aniStartPos) / 2;
                animationView.setLayoutParams(params);
            }
            int offset = currentPos - aniStartPos;
            int totalAniHeight = mOffsetToRefresh - aniStartPos;
            float percent = (float) offset / totalAniHeight;
            if (percent > 1) {
                percent = 1;

            }
            animationView.updatePercent(percent);

        }
    }

}
