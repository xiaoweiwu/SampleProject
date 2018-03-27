package com.common.basecomponent.fragment.refresh;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wuxiaowei on 2017/5/22.
 */

public abstract class ALoadingView extends FrameLayout implements IBaseLoadingView {
    public ALoadingView(@NonNull Context context) {
        super(context);
    }

    public ALoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ALoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
