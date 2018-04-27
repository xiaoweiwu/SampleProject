package com.fullshare.basebusiness.widget.quickssidebar;

/**
 * Created by wuxiaowei on 2018/4/25.
 */

public interface OnQuickSideBarTouchListener {
    void onLetterChanged(String letter,int position,float y);
    void onLetterTouching(boolean touching);
}
