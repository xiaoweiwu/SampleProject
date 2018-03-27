package com.common.basecomponent.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<Bean> extends BaseAdapter {
    protected LayoutInflater mInflater;
    private Context mContext;
    private List<Bean> mBeans;
    private int mLayoutId;

    public CommonAdapter(Context context, List<Bean> beans, int itemLayout) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mBeans = beans;
        mLayoutId = itemLayout;
    }

    @Override
    public int getCount() {
        return mBeans != null ? mBeans.size() : 0;
    }

    @Override
    public Bean getItem(int position) {
        return mBeans != null ? mBeans.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.createHolder(mInflater, mLayoutId, convertView, parent, position);
        Bean bean = getItem(position);
        renderItemView(holder, bean);
        return holder.getConvertView();
    }

    public void updateItems(List<Bean> beans) {
        mBeans = beans;
        notifyDataSetChanged();
    }

    public List<Bean> getItems() {
        return mBeans;
    }

    public Resources getResources() {
        return mContext.getResources();
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void renderItemView(ViewHolder holder, Bean bean);
}
