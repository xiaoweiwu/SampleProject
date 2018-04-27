package com.fullshare.zxing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;

import com.fullshare.zxing.R;
import com.fullshare.zxing.camera.CameraManager;
import com.google.zxing.ResultPoint;

public class ScannerView extends View {
    private static final int FRAME_CORNER = 15;
    private static final int FRAME_HEIGHT = 200;

    /**
     * 刷新界面的时间
     */
    private static final int ANIMATION_DELAY = 20;
    private static final int DURATION = 2000;

    private final float dip;

    private Rect mFrame;
    private Paint paint;
    private Paint cornerPaint;

    private Path path;
    private Scroller mScanScroller;
    private Drawable mScanLine;

//    private final int resultPointColor;
//    private Collection<ResultPoint> possibleResultPoints;
//    private Collection<ResultPoint> lastPossibleResultPoints;

    public ScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);

        dip = getResources().getDisplayMetrics().density;
//        resultPointColor = getResources().getColor(R.color.possible_result_points);

        mScanLine = ContextCompat.getDrawable(context, R.drawable.scaner_line);

        paint = new Paint();

        cornerPaint = new Paint();
        cornerPaint.setColor(getResources().getColor(R.color.corner));
        cornerPaint.setStyle(Paint.Style.STROKE);
        cornerPaint.setStrokeWidth(2 * dip);

        path = new Path();

//        possibleResultPoints = new HashSet<ResultPoint>(5);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();

        if (mFrame == null && width > 0 && height > 0) {
            mFrame = new Rect();
            if(getPaddingTop()==0){
                mFrame.top = (height-(width-getPaddingLeft()-getPaddingRight()))/2;
            }else {
                mFrame.top = getPaddingTop();
            }
            mFrame.left = getPaddingLeft();
            mFrame.right = width - getPaddingRight();
            mFrame.bottom = mFrame.width() + mFrame.top;

            if (CameraManager.get() != null) {
                CameraManager.get().setFramingRect(mFrame);
            }
        }

        paint.setColor(0x99000000);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, mFrame.top, paint);
        canvas.drawRect(0, mFrame.top, mFrame.left, mFrame.bottom + 1, paint);
        canvas.drawRect(mFrame.right + 1, mFrame.top, width, mFrame.bottom + 1, paint);
        canvas.drawRect(0, mFrame.bottom + 1, width, height, paint);

        paint.setColor(0xffffffff);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mFrame, paint);

        float left = mFrame.left - 4 * dip;
        float top = mFrame.top - 4 * dip;
        float right = mFrame.right + 4 * dip;
        float bottom = mFrame.bottom + 4 * dip;
        float len = FRAME_CORNER * dip;

        path.reset();
        path.moveTo(left, top + len);
        path.lineTo(left, top);
        path.lineTo(left + len, top);
        canvas.drawPath(path, cornerPaint);

        path.reset();
        path.moveTo(right - len, top);
        path.lineTo(right, top);
        path.lineTo(right, top + len);
        canvas.drawPath(path, cornerPaint);

        path.reset();
        path.moveTo(right, bottom - len);
        path.lineTo(right, bottom);
        path.lineTo(right - len, bottom);
        canvas.drawPath(path, cornerPaint);

        path.reset();
        path.moveTo(left, bottom - len);
        path.lineTo(left, bottom);
        path.lineTo(left + len, bottom);
        canvas.drawPath(path, cornerPaint);

        // 绘制中间的线,每次刷新界面，中间的线往下移动
        if (mScanScroller == null) {
            mScanScroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
            mScanScroller.startScroll(0, mFrame.top, 0, mFrame.height(), DURATION);
        }
        if (mScanScroller.computeScrollOffset()) {
            int animY = mScanScroller.getCurrY();
            mScanLine.setBounds(mFrame.left, animY, mFrame.right, (int) (animY + 2 * dip));
            mScanLine.draw(canvas);
        } else {
            mScanScroller.abortAnimation();
            mScanScroller.startScroll(0, mFrame.top, 0, mFrame.height(), DURATION);
        }

//        Collection<ResultPoint> currentPossible = possibleResultPoints;
//        Collection<ResultPoint> currentLast = lastPossibleResultPoints;
//        if (currentPossible.isEmpty()) {
//            lastPossibleResultPoints = null;
//        } else {
//            possibleResultPoints = new HashSet<ResultPoint>(5);
//            lastPossibleResultPoints = currentPossible;
//            paint.setColor(resultPointColor);
//            for (ResultPoint point : currentPossible) {
//                canvas.drawCircle(mFrame.left + point.getX(), mFrame.top + point.getY(), 6.0f, paint);
//            }
//        }
//        if (currentLast != null) {
//            paint.setColor(resultPointColor);
//            for (ResultPoint point : currentLast) {
//                canvas.drawCircle(mFrame.left + point.getX(), mFrame.top + point.getY(), 3.0f, paint);
//            }
//        }

        postInvalidate(mFrame.left, mFrame.top, mFrame.right, mFrame.bottom);
    }

    public void drawScanner() {
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
//        possibleResultPoints.add(point);
    }
}
