package com.common.basecomponent.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.common.basecomponent.R;


/**
 * @author fs_wuxiaowei
 * @time 2016/10/14 14:17
 * @desc
 */

public class DividerLine extends LinearLayout {
    private static final int MODE_LINE = 0;
    private static final int MODE_LINE_BLANK_LIINE = 1;
    private static final int MODE_LINE_BLANK = 2;
    private static final int MODE_BLANK_LINE = 3;
    private static final int MODE_BLANK = 4;
    View dividerLineTop;
    View dividerBlank;
    View dividerLineBottom;
    int dividerColor;
    private int mode = MODE_LINE;

    public DividerLine(Context context) {
        this(context, null);
    }

    public DividerLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DividerLine);
        mode = ta.getInt(R.styleable.DividerLine_mode, MODE_LINE);
        dividerColor = ta.getInt(R.styleable.DividerLine_lineColor, getResources().getColor(R.color.divider));
        LayoutInflater.from(context).inflate(R.layout.divider_line_layout, this);
        dividerBlank = findViewById(R.id.divider_blank);
        dividerLineTop = findViewById(R.id.divider_line_top);
        dividerLineBottom = findViewById(R.id.divider_line_bottom);
        setLineColor(dividerColor);
        if (mode == MODE_LINE) {
            dividerLineTop.setVisibility(VISIBLE);
            dividerBlank.setVisibility(GONE);
            dividerLineBottom.setVisibility(GONE);
        } else if (mode == MODE_LINE_BLANK_LIINE) {
            dividerLineTop.setVisibility(VISIBLE);
            dividerBlank.setVisibility(VISIBLE);
            dividerLineBottom.setVisibility(VISIBLE);

        } else if (mode == MODE_LINE_BLANK) {
            dividerLineTop.setVisibility(VISIBLE);
            dividerBlank.setVisibility(VISIBLE);
            dividerLineBottom.setVisibility(GONE);
        } else if (mode == MODE_BLANK_LINE) {
            dividerLineTop.setVisibility(GONE);
            dividerBlank.setVisibility(VISIBLE);
            dividerLineBottom.setVisibility(VISIBLE);
        } else if (mode == MODE_BLANK) {
            dividerLineTop.setVisibility(GONE);
            dividerBlank.setVisibility(VISIBLE);
            dividerLineBottom.setVisibility(GONE);
        }
        ta.recycle();
    }

    public void setLineColor(@ColorInt int color) {
        dividerLineTop.setBackgroundColor(color);
        dividerLineBottom.setBackgroundColor(color);
    }
}
