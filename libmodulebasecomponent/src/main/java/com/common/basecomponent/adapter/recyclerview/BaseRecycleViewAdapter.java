package com.common.basecomponent.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fs_wuxiaowei on 2016/9/18.
 */
public abstract class BaseRecycleViewAdapter<D, VH extends BaseRecycleHolder> extends Adapter<VH> {

    protected OnItemClickListener onItemClickListener;

    protected OnItemLongClickListener onItemLongClickListener;

    protected LayoutInflater mInflater;

    protected List<D> itemDatas;

    protected Context mContext;

    protected Activity activity;
    public BaseRecycleViewAdapter(Context context, List<D> itemDatas, OnItemClickListener onItemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.activity = (Activity) context;
        if (itemDatas == null) {
            this.itemDatas = new ArrayList<>();
        } else {
            this.itemDatas = itemDatas;
        }
        this.onItemClickListener = onItemClickListener;
    }

    public BaseRecycleViewAdapter(Context context, List<D> itemDatas) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        if (itemDatas == null) {
            this.itemDatas = new ArrayList<>();
        } else {
            this.itemDatas = itemDatas;
        }
    }

    public BaseRecycleViewAdapter(Context context, OnItemClickListener onItemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.itemDatas = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    public BaseRecycleViewAdapter(Context context, List<D> itemDatas, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this(context, itemDatas, onItemClickListener);
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType, mInflater);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position,getItem(position));
                }
            });
        }
        if (onItemLongClickListener != null) {
            holder.itemView.setLongClickable(true);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClick(view, position,getItem(position));
                    return false;
                }
            });
        }

        onBindData(holder, position);
    }

    @Override
    public int getItemCount() {
        return itemDatas.size();
    }

    /**
     * 更新数据
     *
     * @param holder item对应的holder
     * @param data   item的数据
     */
    public void updateItem(VH holder, D data) {
        itemDatas.set(holder.getLayoutPosition(), data);
    }


    /**
     * 获取一条数据
     *
     * @param position item的位置
     * @return item对应的数据
     */
    public D getItem(int position) {
        return itemDatas.get(position);
    }

    /**
     * 追加一条数据
     *
     * @param data 追加的数据
     */
    public void appendItem(D data) {
        itemDatas.add(data);
    }

    /**
     * 追加一条数据
     *
     * @param data 追加的数据
     */
    public void appendItem(D data, int position) {
        itemDatas.add(position, data);
    }

    /**
     * 追加一个集合数据
     *
     * @param list 要追加的数据集合
     */
    public void appendList(List<D> list) {
        itemDatas.addAll(list);
    }


    /**
     * 填充数据,此方法会清空以前的数据
     *
     * @param list 需要显示的数据
     */
    public void fillList(List<D> list) {
        itemDatas.clear();
        itemDatas.addAll(list);
    }

    public void clearList() {
        itemDatas.clear();
    }

    public void insertList(List<D> list, int postion) {
        itemDatas.addAll(postion, list);
    }

    public void removeList(List<D> list) {
        itemDatas.removeAll(list);
    }

    public void remove(int position) {
        itemDatas.remove(position);
    }

    protected abstract VH onCreateHolder(ViewGroup parent, int viewType, LayoutInflater inflater);


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public List<D> getItemDatas() {
        return itemDatas;
    }

    protected abstract void onBindData(VH holder, int position);

    public interface OnItemClickListener<D> {
        void onItemClick(View v, int position,D d);
    }

    public interface OnItemLongClickListener<D> {
        void onItemLongClick(View v, int position,D d);
    }
}
