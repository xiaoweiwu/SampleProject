package com.common.basecomponent.adapter.list;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuxiaowei
 * Date: 2015-09-23
 * Time: 17:16
 */
public abstract class BaseListAdapter<T, VH extends ListViewHolder> extends BaseAdapter {
    /**
     * 泛型数据集合
     */
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected Resources resources;

    protected BaseListAdapter(Context context, List<T> objects) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        resources = mContext.getResources();
        if (objects != null) {
            // 集合不为空
            this.mDatas = objects;
        } else {
            // 集合为空
            this.mDatas = new ArrayList<T>();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder;
        if (convertView == null) {
            holder = createViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public final VH createViewHolder(ViewGroup parent, int viewType) {
        final VH holder = onCreateViewHolder(parent, viewType);
        return holder;
    }

    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int viewType);

    public abstract void onBindViewHolder(VH viewHolder, int position);

    /**
     * 填充数据,此方法会清空以前的数据
     *
     * @param list 需要显示的数据
     */
    public void fillList(List<T> list) {
        mDatas.clear();
        mDatas.addAll(list);
    }

    public void notifyDataSetChanged(List<T> list) {
        fillList(list);
    }

    /**
     * 追加一个集合数据
     *
     * @param list 要追加的数据集合
     */
    public void append(List<T> list) {
        mDatas.addAll(list);
    }

    @Override
    public int getCount() {
        return this.mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return this.mDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
}


