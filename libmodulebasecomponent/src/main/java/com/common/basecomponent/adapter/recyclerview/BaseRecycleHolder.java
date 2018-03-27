package com.common.basecomponent.adapter.recyclerview;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by fs_wuxiaowei on 2016/9/18.
 */
public class BaseRecycleHolder extends ViewHolder {
    public BaseRecycleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, this.itemView);
    }
}
