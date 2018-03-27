package com.common.basecomponent.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author chenweibin
 * @time 2017/4/7.
 */

public final class ViewHolder {
    protected SparseArray<View> mViews;
    protected View mConvertView;
    private int mPosition;
    private int mLayoutId;

    private ViewHolder(LayoutInflater inflater, int layoutId, ViewGroup parent, int position) {
        mViews = new SparseArray<>();
        mConvertView = inflater.inflate(layoutId, parent, false);
        mConvertView.setTag(this);
        mPosition = position;
        mLayoutId = layoutId;
    }

    public static ViewHolder createHolder(LayoutInflater inflater, int resId, View convertView, ViewGroup parent, int position) {
        if (convertView == null) {
            return new ViewHolder(inflater, resId, parent, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null || holder.mLayoutId != resId)
                return new ViewHolder(inflater, resId, parent, position);
            holder.mPosition = position;
            return holder;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }
}
