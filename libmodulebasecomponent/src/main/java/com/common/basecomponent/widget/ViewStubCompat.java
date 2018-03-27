package com.common.basecomponent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewStubCompat extends View {

    public ViewStubCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewStubCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setVisibility(GONE);
        setWillNotDraw(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    public View inflate(View view) {
        final ViewParent viewParent = getParent();
        final ViewParent giveParent = view.getParent();

        if (viewParent != null && viewParent instanceof ViewGroup) {
            final ViewGroup parent = (ViewGroup) viewParent;

            if (giveParent != null && giveParent instanceof ViewGroup) {
                final ViewGroup gParent = (ViewGroup) giveParent;
                gParent.removeView(view);
            }

            view.setId(getId());

            final int index = parent.indexOfChild(this);
            parent.removeViewInLayout(this);

            final ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams != null) {
                parent.addView(view, index, layoutParams);
            } else {
                parent.addView(view, index);
            }
            setVisibility(VISIBLE);
            return view;
        }
        return null;
    }
}