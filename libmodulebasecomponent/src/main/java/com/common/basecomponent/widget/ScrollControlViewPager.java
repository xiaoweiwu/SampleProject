package com.common.basecomponent.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author: wuxiaowei
 * date : 2017/3/23
 */

public class ScrollControlViewPager extends ViewPager {
    private boolean scrollEnable = true;

    public ScrollControlViewPager(Context context) {
        super(context);
    }

    public ScrollControlViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollEnable(boolean scrollEnable) {
        this.scrollEnable = scrollEnable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!scrollEnable) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollEnable) {
            return false;
        }
        return super.onTouchEvent(ev);
    }
}
