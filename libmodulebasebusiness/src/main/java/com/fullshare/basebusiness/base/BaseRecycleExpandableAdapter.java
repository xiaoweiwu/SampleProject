package com.fullshare.basebusiness.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.common.basecomponent.adapter.recyclerview.BaseRecycleHolder;
import com.common.basecomponent.adapter.recyclerview.BaseRecycleViewAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wuxiaowei on 2017/9/7.
 */

public abstract class BaseRecycleExpandableAdapter<DATA, GVH extends BaseRecycleHolder, CVH extends BaseRecycleHolder> extends BaseRecycleViewAdapter<DATA, BaseRecycleHolder> {

    public static final int GROUP_VIEW_TYPE = 1000;

    public static final int CHILD_VIEW_TYPE = 2000;
    protected Context context;
    protected LayoutInflater inflater;
    protected OnExpandableItemClickListener expandableItemClickListener;
    private Set<Integer> groupSet = new HashSet<>();
    private Map<Integer, Integer> position2childPositionMap = new HashMap<>();
    private Map<Integer, Integer> position2GroupPositionMap = new HashMap<>();


    public BaseRecycleExpandableAdapter(Context context) {
        this(context, null, null);
    }

    public BaseRecycleExpandableAdapter(Context context, List<DATA> datas, OnExpandableItemClickListener expandableItemClickListener) {
        super(context, datas, null);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.expandableItemClickListener = expandableItemClickListener;
    }

    @Override
    public final void onBindViewHolder(BaseRecycleHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (isGroupViewType(viewType)) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableItemClickListener != null) {
                        expandableItemClickListener.onGroupClick(getGroup(position2GroupPositionMap.get(position)));
                    }
                }
            });
            onBindGroupViewHolder((GVH) holder, position2GroupPositionMap.get(position), viewType);
        } else if (isChildViewType(viewType)) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableItemClickListener != null) {
                        expandableItemClickListener.onChildClick(getChild(position2GroupPositionMap.get(position), position2childPositionMap.get(position)));
                    }
                }
            });
            onBindChildViewHolder((CVH) holder, position2GroupPositionMap.get(position), position2childPositionMap.get(position), viewType);
        }
    }

    @Override
    public BaseRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isGroupViewType(viewType)) {
            return onCreateGroupViewHolder(parent, viewType);
        } else if (isChildViewType(viewType)) {
            return onCreateChildViewHolder(parent, viewType);
        }
        return null;
    }

    public boolean isGroupViewType(int viewType) {
        return viewType / 1000 == 1;
    }


    public boolean isChildViewType(int viewType) {
        return viewType / 1000 == 2;
    }


    @Override
    public final int getItemViewType(int position) {
        if (groupSet.contains(position)) {
            return getGroupViewType(position2GroupPositionMap.get(position));
        }
        return getChildViewType(position2GroupPositionMap.get(position), position2childPositionMap.get(position));
    }

    public int getChildViewType(int groupPosition, int position) {
        return 2000;
    }

    public int getGroupViewType(int groupPosition) {
        return 1000;
    }

    public abstract GVH onCreateGroupViewHolder(ViewGroup parent, int viewType);

    public abstract CVH onCreateChildViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindGroupViewHolder(GVH holder, int groupPosition, int viewType);

    public abstract void onBindChildViewHolder(CVH holder, int groupPosition, int position, int viewType);


    @Override
    public int getItemCount() {
        groupSet.clear();
        int count = 0;
        int gc = getGroupCount();
        for (int i = 0; i < gc; i++) {
            groupSet.add(count);
            position2GroupPositionMap.put(count, i);
            count++;
            int childCount = getChildrenCount(i);
            for (int j = 0; j < childCount; j++) {
                position2childPositionMap.put(count, j);
                position2GroupPositionMap.put(count, i);
                count++;
            }
        }
        return count;
    }

    @Override
    protected BaseRecycleHolder onCreateHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
        return null;
    }

    @Override
    protected void onBindData(BaseRecycleHolder holder, int position) {

    }


    public abstract int getGroupCount();

    public abstract int getChildrenCount(int group);

    public abstract Object getGroup(int groupPosition);

    public abstract Object getChild(int groupPosition, int childPosition);

    public interface OnExpandableItemClickListener<GROUP, CHILD> {

        void onChildClick(CHILD child);

        void onGroupClick(GROUP group);
    }
}
