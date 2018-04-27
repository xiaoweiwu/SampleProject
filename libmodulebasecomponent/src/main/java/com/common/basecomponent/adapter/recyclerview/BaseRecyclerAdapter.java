package com.common.basecomponent.adapter.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<Item> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder> {
    private List<Item> mItems;
    private LayoutInflater mInflater;
    private int mItemLayout;
    private Context mContext;

    public BaseRecyclerAdapter(Context context, List<Item> items, int itemLayout) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mItems = items;
        mItemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(mItemLayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item bookInfo = getItem(position);
        renderItemView(holder, bookInfo);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<Item> getItems() {
        return mItems;
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

    public void updateItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void removeItem(Item item) {
        int index = mItems.indexOf(item);
        if (index >= 0) {
            mItems.remove(item);
            notifyItemRemoved(index);
        }
    }

    public void addItem(Item item, int position) {
        mItems.add(position, item);
        notifyItemRemoved(position);
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;

        public ViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }

    public abstract void renderItemView(ViewHolder holder, Item item);
}
