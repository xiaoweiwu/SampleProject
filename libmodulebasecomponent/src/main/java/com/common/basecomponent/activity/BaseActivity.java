package com.common.basecomponent.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.common.basecomponent.R;
import com.common.basecomponent.eventbus.BusProvider;
import com.common.basecomponent.fragment.LoadingViewController;
import com.common.basecomponent.fragment.refresh.ALoadingView;
import com.common.basecomponent.util.ActivityManageController;
import com.common.basecomponent.widget.ToolBarEx;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wuxiaowei on 2017/3/16.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    public static String TAG = BaseActivity.class.getSimpleName();
    protected Context mContext;
    protected Handler handler = new Handler();
    List<WeakReference<Runnable>> runnableList = new ArrayList<>();
    private ToolBarEx toolBarEx;
    private LoadingViewController loadingViewController;
    private SettingOptions settingOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityManageController.getInstance().pushActivity(this);
        settingOptions = new SettingOptions();
        loadingViewController = new LoadingViewController();
        loadingViewController.setActivity(this);
        initArguments();
        if (settingOptions.isEventEnable()) {
            BusProvider.getInstance().register(this);
        }
        setContentView(getContentLayoutId());
        init(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID != 0) {
            super.setContentView(layoutResID);
            ButterKnife.bind(this);
        }
        preInitView();
    }

    protected void initArguments() {

    }

    protected void preInitView() {
        toolBarEx = (ToolBarEx) findViewById(R.id.toolbar);
        initLoading();
    }

    private void initLoading() {
        ViewGroup content = (ViewGroup) findViewById(R.id.content_layout);
        if (getSettingOptions().isLoadingViewEnable() && content != null) {
            loadingViewController.setLoadingView(createLoadingView());
            loadingViewController.showLoading();
            content.addView(loadingViewController.getLoadingView());
        }
        loadingViewController.setLoadingDialog(createLoadingDialog());
    }


    public ToolBarEx getToolBarEx() {
        return toolBarEx;
    }

    public SettingOptions getSettingOptions() {
        return settingOptions;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManageController.getInstance().popActivity(this);
        for (WeakReference<Runnable> r : runnableList) {
            Runnable rs = r.get();
            if (rs != null) {
                handler.removeCallbacks(rs);
            }
        }
        runnableList.clear();
        if (getSettingOptions().isEventEnable()) {
            BusProvider.getInstance().unregister(this);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        startActAnimation();
    }

    @Override
    public void finish() {
        super.finish();
        finishActAnimation();
    }


    public LoadingViewController getLoadingViewController() {
        return loadingViewController;
    }

    public void postDelay(Runnable runnable, long delay) {
        runnableList.add(new WeakReference<Runnable>(runnable));
        handler.postDelayed(runnable, delay);
    }

    public void delayFinish(long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delay);
    }

    public void remove(Runnable runnable) {
        if (runnableList == null) {
            return;
        }
        Iterator iterator = runnableList.iterator();
        while (iterator.hasNext()) {
            WeakReference<Runnable> r = (WeakReference<Runnable>) iterator.next();
            Runnable rs = r.get();
            if (runnable == rs) {
                handler.removeCallbacks(rs);
                iterator.remove();
            }
        }
    }

    protected void startActAnimation() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    protected void finishActAnimation() {
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    protected abstract int getContentLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract ALoadingView createLoadingView();

    public abstract Dialog createLoadingDialog();


}
