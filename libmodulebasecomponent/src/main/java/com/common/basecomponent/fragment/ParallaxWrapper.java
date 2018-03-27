package com.common.basecomponent.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by wuxiaowei on 2017/4/23.
 */

public class ParallaxWrapper {
    public static final float RATE = 0.5f;
    private float mScrollMultiplier;
    private CustomRelativeWrapper mHeader;
    private RecyclerView mRecyclerView;

    private int mTotalYScrolled;
    private OnParallaxScroll mParallaxScroll;


    public ParallaxWrapper(RecyclerView recyclerView, CustomRelativeWrapper header, float rate) {
        this.mRecyclerView = recyclerView;
        this.mHeader = header;
        this.mScrollMultiplier = rate;
        init();
    }

    public ParallaxWrapper(RecyclerView recyclerView, CustomRelativeWrapper header) {
        this(recyclerView, header, RATE);
    }

    private void init() {
        RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeader != null) {
                    mTotalYScrolled += dy;
                    translateHeader(mTotalYScrolled);
                }
            }
        };

        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    public void translateHeader(float of) {
        float ofCalculated = of * mScrollMultiplier;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && of < mHeader.getHeight()) {
            mHeader.setTranslationY(ofCalculated);
        } else if (of < mHeader.getHeight()) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, ofCalculated, ofCalculated);
            anim.setFillAfter(true);
            anim.setDuration(0);
            mHeader.startAnimation(anim);
        }
        mHeader.setClipY(Math.round(ofCalculated));
        if (mParallaxScroll != null) {
            final RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(0);
            float left;
            if (holder != null) {
                left = Math.min(1, ((ofCalculated) / (mHeader.getHeight() * mScrollMultiplier)));
            } else {
                left = 1;
            }
            mParallaxScroll.onParallaxScroll(left, of, mHeader);
        }
    }


    public void setOnParallaxScroll(OnParallaxScroll mParallaxScroll) {
        this.mParallaxScroll = mParallaxScroll;
    }

    public interface OnParallaxScroll {
        void onParallaxScroll(float percentage, float offset, View parallax);
    }

    public static class CustomRelativeWrapper extends RelativeLayout {

        private int mOffset;

        public CustomRelativeWrapper(Context context) {
            super(context);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + mOffset));
            super.dispatchDraw(canvas);
        }


        public void setClipY(int offset) {
            mOffset = offset;
            invalidate();
        }

    }
}
