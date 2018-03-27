package com.common.basecomponent.adapter.list;

import android.view.View;

import butterknife.ButterKnife;

/**
 * User: wuxiaowei
 * Date: 2016-01-15
 * Time: 11:56
 */
public class ListViewHolder {
    public final View itemView;

    public ListViewHolder(View view) {
        itemView = view;
        ButterKnife.bind(this, view);
    }
}
