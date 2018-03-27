package com.common.basecomponent.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseViewPagerAdapter<Bean> extends PagerAdapter {
    protected LayoutInflater mInflater;
    private Context mContext;
    private List<Bean> mBeans;
    private int mLayoutId;

    public BaseViewPagerAdapter(Context context, List<Bean> beans, int itemLayout) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mBeans = beans;
        mLayoutId = itemLayout;
    }

    @Override
    public int getCount() {
        return mBeans != null ? mBeans.size() : 0;
    }

    public Bean getItem(int position) {
        return mBeans != null ? mBeans.get(position) : null;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public View createView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.createHolder(mInflater, mLayoutId, convertView, parent, position);
        Bean bean = getItem(position);
        renderItemView(holder, bean, position);
        return holder.getConvertView();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = createView(position, null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        View convertView = (View) view;
        container.removeView(convertView);
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

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public abstract void renderItemView(ViewHolder holder, Bean bean, int position);
}
