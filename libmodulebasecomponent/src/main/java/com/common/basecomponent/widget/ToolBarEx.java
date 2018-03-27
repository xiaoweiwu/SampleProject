package com.common.basecomponent.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.basecomponent.R;


/**
 * @author fs_wuxiaowei
 * @time 2016/10/20 19:04
 */
public class ToolBarEx extends FrameLayout {
    private ImageView ivBack;
    private Button btnLeft;
    private ImageView ivRight;
    private Button btnRight;
    private ImageView ivRight2;
    private Button btnRight2;
    private ImageView ivRight3;
    private Button btnRight3;
    private TextView tvTitle;
    private View rlTitle;
    //    private Toolbar toolbar;
    private FrameLayout flCustom;
    private View divider;
    private int whiteBackRes;

    public ToolBarEx(Context context) {
        this(context, null);
    }

    public ToolBarEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolBarEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this);
        initView();
        initAction();
    }
//    AppBarLayout appBarLayout;

//    public ToolBarEx(Activity activity) {
//        this.activity = activity;
//        initViewAndData();
//        initAction();
//    }
//
//    public ToolBarEx(View contentView, Activity activity) {
//        this.activity = activity;
//        this.contentView = contentView;
//        initViewAndData();
//        initAction();
//    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivBack = (ImageView) findViewById(R.id.ivback);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        btnRight2 = (Button) findViewById(R.id.btnRight2);
        ivRight2 = (ImageView) findViewById(R.id.ivRight2);
        btnRight3 = (Button) findViewById(R.id.btnRight3);
        ivRight3 = (ImageView) findViewById(R.id.ivRight3);
        rlTitle = findViewById(R.id.rlTitle);
        flCustom = (FrameLayout) findViewById(R.id.fl_custom);
        divider = findViewById(R.id.divider);

//       toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
//        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
    }

    /**
     * 标题栏右上角加文字按钮，最多加三个
     *
     * @param position 从右到左依次为0,1,2
     * @param text
     * @param listener
     * @return
     */
    public ToolBarEx addTextAction(final int position, String text, final OnMenuClickListener listener) {
        Button menu = null;
        switch (position) {

            case 0:
                menu = btnRight;
                break;
            case 1:
                menu = btnRight2;
                break;

            case 2:
                menu = btnRight3;
                break;
            case 3:
                menu = btnLeft;
                hideLeftAction();
                break;
        }
        if (menu != null) {
            menu.setVisibility(View.VISIBLE);
            menu.setText(text);
            final Button finalImageView = menu;
            menu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMenuClick(position, finalImageView);
                }
            });
        }

        return this;
    }

    public ToolBarEx addTextAction(String text, final OnMenuClickListener listener) {
        return addTextAction(0, text, listener);
    }

    public Button getTextMenu(int position) {
        Button menu = null;
        switch (position) {
            case 0:
                menu = btnRight;
                break;
            case 1:
                menu = btnRight2;
                break;

            case 2:
                menu = btnRight3;
                break;
            case 3:
                menu = btnLeft;
                hideLeftAction();
                break;
        }
        return menu;
    }

    public ImageView getBackImageView() {
        return getImageMenu(3);
    }

    public ImageView getImageMenu(int position) {
        ImageView menu = null;
        switch (position) {
            case 0:
                menu = ivRight;
                break;
            case 1:
                menu = ivRight2;
                break;
            case 2:
                menu = ivRight3;
                break;
            case 3:
                menu = ivBack;
                break;
        }
        return menu;
    }


    /**
     * 标题栏右上角加图标按钮，最多加三个
     *
     * @param position 从右到左依次为0,1,2
     * @param resId
     * @param listener
     * @return
     */
    public ToolBarEx addIconAction(final int position, int resId, final OnMenuClickListener listener) {
        ImageView menu = null;
        switch (position) {

            case 0:
                menu = ivRight;
                break;
            case 1:
                menu = ivRight2;
                break;
            case 2:
                menu = ivRight3;
                break;
            case 3:
                menu = ivBack;
                break;
        }
        if (menu != null) {
            menu.setVisibility(View.VISIBLE);
            menu.setImageResource(resId);
            final ImageView finalImageView = menu;
            menu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMenuClick(position, finalImageView);
                }
            });
        }
        return this;
    }

    public ToolBarEx addIconAction(int resId, final OnMenuClickListener listener) {
        return addIconAction(0, resId, listener);
    }

    private void initAction() {
        setLeftAction(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.onBackPressed();
                ((Activity) getContext()).onBackPressed();
            }

        });
    }

    public ToolBarEx setLeftAction(OnClickListener onClickListener) {
        if (ivBack != null) {
            ivBack.setOnClickListener(onClickListener);
        }
        return this;
    }

//    public Toolbar getToolbar() {
//        return toolbar;
//    }

//    public AppBarLayout getAppBarLayout() {
//        return appBarLayout;
//    }

    public void hide() {
//        if (appBarLayout != null) {
//            appBarLayout.setVisibility(View.GONE);
//        }
        setVisibility(View.GONE);
    }

    public void show() {
//        if (appBarLayout != null) {
//            appBarLayout.setVisibility(View.VISIBLE);
//        }
        setVisibility(View.VISIBLE);
    }

    public ToolBarEx setTitle(int res) {
        if (tvTitle != null)
            tvTitle.setText(res);
        return this;
    }

    public ToolBarEx setTitle(String title) {
        if (tvTitle != null)
            tvTitle.setText(title);
        return this;
    }

    public TextView getTitleTextView() {
        return tvTitle;
    }

    public ToolBarEx setTitleCenter(boolean center) {
        if (tvTitle != null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(tvTitle.getLayoutParams());
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.LEFT_OF, 0);
            params.leftMargin = 0;
            this.tvTitle.setLayoutParams(params);
//            this.tvTitle.setGravity(center ? Gravity.CENTER : Gravity.CENTER_VERTICAL);
//            View view = findViewById(R.id.flLeft2);
//            if(center)
//                view.getLayoutParams().width = 0;
        }
        return this;
    }


    public View getContentLayout() {
        return rlTitle;
    }

    public ToolBarEx hideLeftAction() {
        if (ivBack != null) {
            ivBack.setEnabled(false);
            ivBack.setVisibility(View.GONE);
        }
        return this;
    }

    public ToolBarEx setDividerVisiblity(int visiblity) {
        divider.setVisibility(visiblity);
        return this;
    }

    public ToolBarEx setbackgroundTransparent() {
        if (rlTitle != null) {
            rlTitle.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        }
        setDividerVisiblity(INVISIBLE);
        return this;
    }

    public ToolBarEx setDeepBackgroundMode() {
        if (rlTitle != null) {
            rlTitle.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        }
        if (whiteBackRes != 0) {
            getBackImageView().setImageResource(whiteBackRes);
        }
        setDividerVisiblity(INVISIBLE);
        getTitleTextView().setTextColor(getContext().getResources().getColor(R.color.white));
        getTextMenu(0).setTextColor(getContext().getResources().getColor(R.color.white));
        getTextMenu(1).setTextColor(getContext().getResources().getColor(R.color.white));
        return this;
    }

    public void setWhiteBackRes(int whiteBackRes) {
        this.whiteBackRes = whiteBackRes;
    }

    public interface OnMenuClickListener {
        void onMenuClick(int position, View menu);
    }

    //    public void hideAllAction(){
//        AnimationSet exit = (AnimationSet) AnimationUtils.loadAnimation(activity, R.anim.alpha_exit);
//        setAnimationForChannelViews(exit);
//        setVisibilityForChannelViews(View.INVISIBLE);
//
//    }
//
//    public void showAllAction(){
//        AnimationSet enter = (AnimationSet) AnimationUtils.loadAnimation(activity, R.anim.alpha_enter);
//        setAnimationForChannelViews(enter);
//        setVisibilityForChannelViews(View.VISIBLE);
//
//    }

//    private void setAnimationForChannelViews(AnimationSet animation){
//        ivBack.setAnimation(animation);
//        ivRight.setAnimation(animation);
//        ivRight2.setAnimation(animation);
//
//    }
//
//    private void setVisibilityForChannelViews(int visibility){
//        ivBack.setVisibility(visibility);
//        ivRight.setVisibility(visibility);
//        ivRight2.setVisibility(visibility);
//    }
}
