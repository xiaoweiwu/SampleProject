package com.common.basecomponent.fragment.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.basecomponent.R;


/**
 * 2017/3/16 wuxiaowei
 * 构建footview帮助类
 */
public class LoadMoreViewHelper {

    protected View mFootView;
    protected TextView footerTv;
    protected ProgressBar footerBar;
    protected ImageView ivFooter;
    protected View.OnClickListener onClickRefreshListener;

    public LoadMoreViewHelper(Context context, OnClickListener clickListener) {
        mFootView = LayoutInflater.from(context).inflate(R.layout.loadmore_default_footer, null);
        footerTv = (TextView) mFootView.findViewById(R.id.loadmore_default_footer_tv);
        footerBar = (ProgressBar) mFootView.findViewById(R.id.loadmore_default_footer_progressbar);
        ivFooter = (ImageView) mFootView.findViewById(R.id.iv_footer_img);
        this.onClickRefreshListener = clickListener;
        showNormal();
    }

    public View getFootView() {
        return mFootView;
    }

    public void showNormal() {
        footerTv.setVisibility(View.INVISIBLE);
//        footerTv.setText("点击加载更多");
        footerBar.setVisibility(View.GONE);
        ivFooter.setVisibility(View.GONE);
//        mFootView.setOnClickListener(onClickRefreshListener);
    }

    public void showLoading() {
        footerTv.setVisibility(View.VISIBLE);
        footerTv.setText("正在努力加载...");
        footerBar.setVisibility(View.VISIBLE);
        mFootView.setOnClickListener(null);
    }

    public void showFail() {
        footerTv.setVisibility(View.VISIBLE);
        footerTv.setText("加载失败，请稍后再试");
        footerBar.setVisibility(View.GONE);
        mFootView.setOnClickListener(onClickRefreshListener);
    }

    public void showNoMore() {
        showNoMore(LoadMoreFootType.DEFAULT);
    }

    public void showNoMore(LoadMoreFootType type) {

        if (type == LoadMoreFootType.GOOD_WORDS || type == LoadMoreFootType.SUBJECT) {
            footerTv.setText("");
            footerTv.setVisibility(View.GONE);
            footerBar.setVisibility(View.GONE);
//            ivFooter.setImageResource(R.drawable.fengsheng_footer);
            ivFooter.setVisibility(View.VISIBLE);
        } else if (type == LoadMoreFootType.DEFAULT) {
            footerTv.setVisibility(View.VISIBLE);
            footerTv.setText("已经加载完毕");
            footerTv.setVisibility(View.GONE);
            footerBar.setVisibility(View.GONE);
            ivFooter.setVisibility(View.VISIBLE);
//            ivFooter.setImageResource(R.drawable.icon_fsblogo_gray);
            ivFooter.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
//            ((MarginLayoutParams)ivFooter.getLayoutParams()).topMargin = DataTools.dip2px(ivFooter.getContext(),20);
//            ((MarginLayoutParams)ivFooter.getLayoutParams()).bottomMargin = DataTools.dip2px(ivFooter.getContext(),20);
//            mFootView.setOnClickListener(onClickRefreshListener);
        } else if (type == LoadMoreFootType.NONE) {
            footerTv.setText("");
            footerTv.setVisibility(View.GONE);
            footerBar.setVisibility(View.GONE);
            ivFooter.setVisibility(View.GONE);
            mFootView.setVisibility(View.GONE);
        }

    }

    public void showNormalClick() {
        footerTv.setVisibility(View.VISIBLE);
        footerTv.setText("点击加载更多");
        footerBar.setVisibility(View.GONE);
        mFootView.setOnClickListener(onClickRefreshListener);
    }
}
