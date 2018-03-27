package com.common.basecomponent.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * Created by wuxiaowei on 2017/5/22.
 */

public abstract class BasePtrUIHandler extends FrameLayout implements PtrUIHandler {
    public BasePtrUIHandler(@NonNull Context context) {
        super(context);
    }

    public BasePtrUIHandler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePtrUIHandler(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
