package com.fullshare.basebusiness.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.flyco.tablayout.SlidingTabLayout;


/**
 * Created by wuxiaowei on 2017/7/21.
 */

public class ViewUtil {

    public static void initTab(Context mContext, SlidingTabLayout tablayout) {
        tablayout.setIndicatorHeight(2);
        tablayout.setIndicatorWidth(32);
        tablayout.setTextsize(15);
        tablayout.setTabPadding(13);
        tablayout.setUnderlineHeight(0.5f);
        tablayout.setUnderlineColor(ContextCompat.getColor(mContext, com.common.basecomponent.R.color.divider));
        tablayout.setIndicatorColor(ContextCompat.getColor(mContext, com.common.basecomponent.R.color.app_text_color));
        tablayout.setTextSelectColor(ContextCompat.getColor(mContext, com.common.basecomponent.R.color.common_text_color));
        tablayout.setTextUnselectColor(ContextCompat.getColor(mContext, com.common.basecomponent.R.color.common_text_grey_color));

    }
}
