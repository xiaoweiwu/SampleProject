
package com.fullshare.basebusiness.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.common.basecomponent.fragment.refresh.EmptyType;
import com.common.basecomponent.fragment.refresh.LoadErrorType;
import com.fullshare.basebusiness.R;


/**
 * Description：
 */
public class LoadingLayout extends ALoadingView {
    private ImageView ivStatus;
    private TextView txtStatus;
    private TextView txtStatus2;
    private TextView txtAction;
    private ImageView mAnimProgress;
    private AnimationDrawable animationDrawable;
    private OnClickListener listener;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setEnabled(false);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_load_state, this);
        ivStatus = (ImageView) view.findViewById(R.id.iv_status);
        txtStatus = (TextView) view.findViewById(R.id.txt_status);
        txtStatus2 = (TextView) view.findViewById(R.id.txt_status_2);
        txtAction = (TextView) view.findViewById(R.id.txt_action);
        mAnimProgress = (ImageView) view.findViewById(R.id.animProgress);
        mAnimProgress.setImageResource(R.drawable.pulltorefresh_header);
        animationDrawable = (AnimationDrawable) mAnimProgress.getDrawable();
        setFocusable(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animationDrawable.isRunning())
            animationDrawable.stop();
    }


    @Override
    public void showLoading() {
        showLoading(0);
    }

    @Override
    public void showLoading(@DrawableRes int drawableRes) {
        setEnabled(true);
        setVisibility(View.VISIBLE);
        mAnimProgress.setVisibility(View.VISIBLE);
        if (!animationDrawable.isRunning())
            animationDrawable.start();
        if (drawableRes > 0) {
            ivStatus.setVisibility(VISIBLE);
            ivStatus.setImageResource(drawableRes);
        } else {
            ivStatus.setVisibility(GONE);
        }
        txtStatus.setVisibility(GONE);
        txtAction.setVisibility(GONE);
    }

    @Override
    public void setLoadingActionListener(OnClickListener clickListener) {
        this.listener = clickListener;
    }

    @Override
    public void hideLoading() {
        setEnabled(false);
        setVisibility(View.GONE);
    }

    @Override
    public void showLoadingError(LoadErrorType errorType) {
        if (animationDrawable.isRunning())
            animationDrawable.stop();
        setEnabled(true);
        setVisibility(View.VISIBLE);
        mAnimProgress.setVisibility(View.GONE);
        if (errorType == LoadErrorType.NETWORD_UNAVAILABLE) {
            ivStatus.setImageResource(R.drawable.ic_nowifi);
            txtStatus.setText("网络不给力，点击刷新");
        } else if (errorType == LoadErrorType.SERVER_ERROR) {
            ivStatus.setImageResource(R.drawable.ic_loaderror);
            txtStatus.setText("加载出错");
        }
        ivStatus.setVisibility(VISIBLE);
        txtStatus.setVisibility(VISIBLE);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
    }

    @Override
    public void showNoData(EmptyType emptyType) {
        setEnabled(false);
        if (animationDrawable.isRunning())
            animationDrawable.stop();
        txtStatus.setVisibility(VISIBLE);
        ivStatus.setVisibility(VISIBLE);
        if (emptyType == EmptyType.COMMON) {
            txtStatus.setText("这里空空的什么都没有");
            ivStatus.setImageResource(R.drawable.ic_nothing);
        } else if (emptyType == EmptyType.SEARCH) {
            txtStatus.setText("没有匹配到搜索结果");
            ivStatus.setImageResource(R.drawable.ic_nosearch);
        } else if (emptyType == EmptyType.CONTENT_REMOVED) {
            txtStatus.setText("相关内容已下架");
            ivStatus.setImageResource(R.drawable.ic_nothing);
        } else if (emptyType == EmptyType.HEALTH_RANK) {
            txtStatus.setText("榜单未更新");
            ivStatus.setImageResource(R.drawable.ic_nothing);
        }
        setVisibility(VISIBLE);
        txtAction.setVisibility(GONE);
        mAnimProgress.setVisibility(GONE);
    }

    @Override
    public void showLoadingDialog(String message) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public boolean isShowingLoading() {
        return getVisibility() == VISIBLE && mAnimProgress != null && mAnimProgress.getVisibility() == VISIBLE;
    }
}
