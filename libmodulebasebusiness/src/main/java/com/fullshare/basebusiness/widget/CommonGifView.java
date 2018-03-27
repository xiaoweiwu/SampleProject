package com.fullshare.basebusiness.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

/**
 * Created by wuxiaowei on 2017/9/14.
 */

public class CommonGifView extends View {
    private Resources mResources;
    private Movie mMovie;
    private long startTime = 0;
    private float widthRatio;
    private float heightRatio;

    public CommonGifView(Context context) {
        this(context, null);
    }

    public CommonGifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonGifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = context.getResources();
    }

    /**
     * 为View设置gif格式图片背景
     *
     * @description：
     * @author ldm
     * @date 2016-2-18 上午9:21:16
     */
    public void setGifViewBg(int src_id) {
        if (src_id == -1) {
            return;
        }
        // 获取对应资源文件的输入流
        InputStream is = mResources.openRawResource(src_id);
        mMovie = Movie.decodeStream(is);// 解码输入流为Movie对象
        requestLayout();
    }

    /*
     * 这个方法供Activity中使用
     */
    public void setGifStream(InputStream is) {
        mMovie = Movie.decodeStream(is);
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        long now = SystemClock.uptimeMillis();
        if (startTime == 0) { // 如果第一帧，记录起始时间
            startTime = now;
        }
        if (mMovie != null) {// 如果返回值不等于null，就说明这是一个GIF图片
            int duration = mMovie.duration();// 取出动画的时长
            if (duration == 0) {
                duration = 1000;
            }
            int currentTime = (int) ((now - startTime) % duration);// 算出需要显示第几帧
            mMovie.setTime(currentTime);
            // mMovie.draw(canvas, getWidth() - mMovie.width(), getHeight() - mMovie.height());
            float scale = Math.min(widthRatio, heightRatio);
            canvas.scale(scale, scale);
            mMovie.draw(canvas, 0, 0);
            invalidate();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMovie != null) {// 如果返回值不等于null，就说明这是一个GIF图片
            int w = mMovie.width();//宽度
            int h = mMovie.height();//高度
            if (w <= 0) {
                w = 1;
            }
            if (h <= 0) {
                h = 1;
            }
            int left = getPaddingLeft();
            int right = getPaddingRight();
            int top = getPaddingTop();
            int bottom = getPaddingBottom();
            int widthSize, heightSize;
            w += left + right;
            h += top + bottom;
            w = Math.max(w, getSuggestedMinimumWidth());
            h = Math.max(h, getSuggestedMinimumHeight());
            widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);//根据你提供的大小和MeasureSpec，返回你想要的大小值
            heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
            widthRatio = (float) widthSize / w;
            heightRatio = (float) heightSize / h;
            setMeasuredDimension(widthSize, heightSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
