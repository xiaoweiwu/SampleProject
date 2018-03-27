package com.fullshare.basebusiness.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.basecomponent.widget.ShSwitchView;
import com.common.basecomponent.widget.ShSwitchView.OnSwitchStateChangeListener;
import com.fullshare.basebusiness.R;

/**
 * Created by wuxiaowei on 2016/12/27.
 */

public class ListItemLayout extends FrameLayout {
    public static final int TEXT = 0;
    public static final int SWITCH = 1;
    public static final int ARROW = 2;
    TextView tvTitle;
    ImageView ivArrow;
    ImageView ivIcon;
    ImageView ivRightIcon;
    TextView tvValue;
    TextView tvValueLeft;
    ImageView ivRightReddot;
    View divider;
    ShSwitchView switchView;
    int type = ARROW;
    OnSwitchChangeListener onSwitchChangeListener;
    private CharSequence title;
    private boolean showDivider = true;

    public ListItemLayout(Context context) {
        this(context, null);
    }

    public ListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ListItemLayout);
        showDivider = ta.getBoolean(R.styleable.ListItemLayout_LiShowDivider, true);
        title = ta.getText(R.styleable.ListItemLayout_LiTitle);
        type = ta.getInt(R.styleable.ListItemLayout_LiType, type);
        int dividerMarginLR = ta.getDimensionPixelSize(R.styleable.ListItemLayout_LiDividerMarginLR, getResources().getDimensionPixelSize(R.dimen.horizontal_margin));
        int contentLeft = ta.getDimensionPixelSize(R.styleable.ListItemLayout_LiContentMarginLeft, getResources().getDimensionPixelSize(R.dimen.horizontal_margin));
        int contentRight = ta.getDimensionPixelSize(R.styleable.ListItemLayout_LiContentMarginRight, getResources().getDimensionPixelSize(R.dimen.dp_5));
        ta.recycle();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_setting_list_item, this);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvValue = (TextView) view.findViewById(R.id.tv_value);
        tvValueLeft = (TextView) view.findViewById(R.id.tv_value_left);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        ivRightIcon = (ImageView) view.findViewById(R.id.iv_right_value);
        ivRightReddot = (ImageView) view.findViewById(R.id.iv_right_reddot);
        divider = view.findViewById(R.id.divider);
        MarginLayoutParams params = (MarginLayoutParams) divider.getLayoutParams();
        params.leftMargin = dividerMarginLR;
        params.rightMargin = dividerMarginLR;
        View llRoot = view.findViewById(R.id.ll_root);
        params = (MarginLayoutParams) llRoot.getLayoutParams();
        params.leftMargin = contentLeft;
        params.rightMargin = contentRight;
        switchView = (ShSwitchView) findViewById(R.id.switch_view);
        switchView.setOn(true);
        switchView.setOnSwitchStateChangeListener(new OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                if (onSwitchChangeListener != null) {
                    onSwitchChangeListener.onSwitchChange(isOn);
                }
            }
        });
        tvTitle.setText(title);
        setType(type);
        showDivider(showDivider);
    }


    public ListItemLayout setType(int type) {
        this.type = type;
        if (type == TEXT) {
            tvValue.setVisibility(VISIBLE);
            switchView.setVisibility(GONE);
            ivArrow.setVisibility(GONE);
        } else if (type == SWITCH) {
            switchView.setVisibility(VISIBLE);
            tvValue.setVisibility(GONE);
            ivArrow.setVisibility(GONE);
        } else if (type == ARROW) {
            switchView.setVisibility(GONE);
            tvValue.setVisibility(VISIBLE);
            ivArrow.setVisibility(VISIBLE);
        }
        return this;
    }

    public TextView getTvValue() {
        return tvValue;
    }

    public ListItemLayout setValueText(String value) {
        tvValue.setText(value);
        tvValue.setVisibility(VISIBLE);
        return this;
    }

    public ListItemLayout setLeftValueText(String value) {
        tvValueLeft.setText(value);
        tvValueLeft.setVisibility(VISIBLE);
        return this;
    }

    public ListItemLayout setTitleText(String title) {
        tvTitle.setText(title);
        return this;
    }

    public ListItemLayout showDivider(boolean show) {
        divider.setVisibility(show ? VISIBLE : INVISIBLE);
        return this;
    }

    public ListItemLayout setRightIcon(@DrawableRes int res) {
        ivRightIcon.setImageResource(res);
        ivRightIcon.setVisibility(VISIBLE);
        return this;
    }

    public ImageView getIvRightIcon() {
        return ivRightIcon;
    }

    public ListItemLayout setLeftImage(@DrawableRes int res) {
        ivIcon.setImageResource(res);
        ivIcon.setVisibility(VISIBLE);
        return this;
    }

    public void showRightNewMessage(boolean show) {
        ivRightReddot.setVisibility(show ? VISIBLE : GONE);
    }

    public void setOnSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener) {
        this.onSwitchChangeListener = onSwitchChangeListener;
    }

    public interface OnSwitchChangeListener {
        void onSwitchChange(boolean isOn);
    }
}
