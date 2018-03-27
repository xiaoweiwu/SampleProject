package com.common.basecomponent.fragment.refresh;

import android.content.Context;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * @author fs_wuxiaowei
 * @time 2016/10/31 13:46
 * @desc
 */

public class PulltoRefreshUtils {
    public static void init(Context context, PtrClassicFrameLayout mPtrFrameLayout) {
        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(200);
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setEnabledNextPtrAtOnce(true);
        mPtrFrameLayout.setPullToRefresh(false);
    }
}
