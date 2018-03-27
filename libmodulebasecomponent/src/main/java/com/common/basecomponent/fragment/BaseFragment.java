package com.common.basecomponent.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.basecomponent.R;
import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.common.basecomponent.widget.ToolBarEx;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fs_wuxiaowei on 2016/9/9.
 */
public abstract class BaseFragment extends CommonBaseFragment {
    public static final String POSITION = "position";
    public static final String SUPER_POSITION = "super_position";
    public static final int INVALID_POSITION = -1;
    protected int position = INVALID_POSITION;
    protected int superPosition = INVALID_POSITION;
    List<WeakReference<Runnable>> runnableList = new ArrayList<>();
    private ToolBarEx toolBarEx;
    private LoadingViewController loadingViewController;
    private ImageView ivBack;
    private Handler handler = new Handler();

    @Override
    protected void initArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt(POSITION, INVALID_POSITION);
            superPosition = bundle.getInt(SUPER_POSITION, INVALID_POSITION);
        }
        loadingViewController = new LoadingViewController();
    }

    @Override
    protected void preInit(View v) {
        initTitle();
        initLoadingView();
    }

    protected void initTitle() {
        toolBarEx = (ToolBarEx) getView().findViewById(R.id.toolbar);
        ivBack = (ImageView) getView().findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }
    }

    private void initLoadingView() {
        ViewGroup content = getView().findViewById(R.id.content_id);
        loadingViewController.setActivity(getActivity());
        if (loadingViewController.isNeedLoadingView() && content != null) {
            if (!loadingViewController.hasLoadingView()) {
                loadingViewController.setLoadingView(createLoadingView());
                content.addView(loadingViewController.getLoadingView());
            }
        }
    }

    protected abstract ALoadingView createLoadingView();

    @Override
    protected void initViewsAndEvents(View rootView) {

    }

    public LoadingViewController getLoadingViewController() {
        return loadingViewController;
    }

    public void postDelay(Runnable runnable, long delay) {
        runnableList.add(new WeakReference<Runnable>(runnable));
        handler.postDelayed(runnable, delay);
    }

    public ToolBarEx getToolBarEx() {
        return toolBarEx;
    }


    public void showToolBar() {
        if (toolBarEx != null) {
            toolBarEx.show();
        }
    }

    public void hideToolBar() {
        if (toolBarEx != null) {
            toolBarEx.hide();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadingViewController.release();
        removeAllCallback();
    }

    public void removeAllCallback() {
        for (WeakReference<Runnable> r : runnableList) {
            Runnable rs = r.get();
            if (rs != null) {
                handler.removeCallbacks(rs);
            }
        }
        runnableList.clear();
    }


}
