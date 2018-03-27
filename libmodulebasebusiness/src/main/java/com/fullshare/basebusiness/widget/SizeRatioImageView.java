package com.fullshare.basebusiness.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.fullshare.basebusiness.R;


/**
 * Created by wuxiaowei on 2017/1/5.
 */

public class SizeRatioImageView extends AppCompatImageView {
    public static final int BASE_W = 0;
    public static final int BASE_H = 1;
    private int sizeWidth;
    private int sizeHeight;
    private int base;
    private float rate;

    public SizeRatioImageView(Context context) {
        this(context, null);
    }

    public SizeRatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SizeRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SizeRatioImageView);
        base = typedArray.getInt(R.styleable.SizeRatioImageView_sizeBase, BASE_W);
        sizeWidth = typedArray.getInt(R.styleable.SizeRatioImageView_sizeWidth, -1);
        sizeHeight = typedArray.getInt(R.styleable.SizeRatioImageView_sizeHeight, -1);
        typedArray.recycle();

        if (sizeHeight == -1 || sizeWidth == -1) {
            rate = -1;
        }
    }

    public int getSizeWidth() {
        return sizeWidth;
    }

    public int getSizeHeight() {
        return sizeHeight;
    }

    public int getBase() {
        return base;
    }

    public void setSizeRate(int sizeWidth, int sizeHeight, int base) {
        this.sizeWidth = sizeWidth;
        this.sizeHeight = sizeHeight;
        this.base = base;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (rate == -1) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int specW = MeasureSpec.getSize(widthMeasureSpec);
        int specH = MeasureSpec.getSize(heightMeasureSpec);
        int w = 0;
        int h = 0;
        if (base == BASE_H) {
            h = specH;
            w = (int) (h * 1f * sizeWidth / sizeHeight);
        } else {
            w = specW;
            h = (int) (w * 1f * sizeHeight / sizeWidth);
        }
        setMeasuredDimension(w, h);
    }

    public float getRate() {
        return this.rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
