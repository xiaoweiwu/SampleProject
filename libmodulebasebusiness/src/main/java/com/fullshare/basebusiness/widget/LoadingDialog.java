package com.fullshare.basebusiness.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.fullshare.basebusiness.R;

/**
 * Created by wuxiaowei on 2017/5/22.
 */

public class LoadingDialog extends Dialog {
    ImageView imageView;
    private AnimationDrawable animationDrawable;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.loadingDialog);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_loading_dialog, null);
        imageView = (ImageView) view.findViewById(R.id.animProgress);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        setContentView(view);
    }


    @Override
    public void setOnDismissListener(@Nullable final OnDismissListener listener) {
        super.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss(dialog);
                if (animationDrawable.isRunning()) {
                    animationDrawable.stop();
                }
            }
        });
    }

    @Override
    public void show() {
        if (animationDrawable!=null&&!animationDrawable.isRunning()) {
            animationDrawable.start();
        }
        super.show();
    }

    @Override
    public void setOnShowListener(@Nullable final OnShowListener listener) {
        super.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                listener.onShow(dialog);
                if (animationDrawable!=null&&!animationDrawable.isRunning()) {
                    animationDrawable.start();
                }
            }
        });
    }


}
