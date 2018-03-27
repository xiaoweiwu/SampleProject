package com.fullshare.basebusiness.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.fullshare.basebusiness.R;


/**
 * @author fs_wuxiaowei
 * @time 2016/10/31 11:23
 * @desc
 */

public class PulltorefreshAnimationView extends View {

    int center;
    int radius;
    Paint paint;
    RectF rect;
    private float percent;
    private int fillColor;

    public PulltorefreshAnimationView(Context context) {
        this(context, null);
    }

    public PulltorefreshAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PulltorefreshAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fillColor = ContextCompat.getColor(context, R.color.common_button_color_normal);
        paint = new Paint();
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(fillColor);

    }

    public void updatePercent(float percent) {
        this.percent = percent;
        invalidate();
    }

    public float getPercent() {
        return percent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        center = size / 2;
        radius = size / 2;
        rect = new RectF(center - radius, center - radius, center
                + radius, center + radius);
        setMeasuredDimension(size, size);
    }

    @Override
    public void onDraw(Canvas canvas) {
        float startAngle01 = 270;
        float sweepAngle01 = -percent * 360;
        canvas.drawArc(rect, startAngle01, sweepAngle01, true, paint);
    }
}
