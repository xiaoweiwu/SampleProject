package com.common.basecomponent.fragment.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;


/**
 * Created by Vv
 * 2016/10/12 0012.
 * ApiVersion 1.0
 * Description：
 */

public class RecycleViewLoadMoreHelper extends LoadMoreHelper {


    private RecyclerView mRecyclerView;

    private RecycleViewScrollHelper mScrollHelper; //RecycleView的滚动事件辅助类

    public RecycleViewLoadMoreHelper(RecyclerView mRecyclerView, Context context) {
        super(context);
        this.mRecyclerView = mRecyclerView;
        init();
    }

    private void init() {
        mScrollHelper = new RecycleViewScrollHelper(new RecycleViewScrollHelper.OnScrollPositionChangedListener() {
            /**
             * 滑动到顶部的回调事件
             */
            @Override
            public void onScrollToTop() {

            }

            /**
             * 滑动到底部的回调事件
             */
            @Override
            public void onScrollToBottom() {
                loadMore();
            }

            /**
             * 滑动到未知位置的回调事件
             *
             * @param isTopViewVisible    当前位置顶部第一个itemView是否可见,这里是指adapter中的最后一个itemView
             * @param isBottomViewVisible 当前位置底部最后一个itemView是否可见,这里是指adapter中的最后一个itemView
             */
            @Override
            public void onScrollToUnknown(boolean isTopViewVisible, boolean isBottomViewVisible) {

            }
        });
        //开启item是否满屏检查
        mScrollHelper.setCheckIfItemViewFullRecycleViewForBottom(false);
        //把滑动辅助类关联到RevycleView
        mScrollHelper.attachToRecycleView(mRecyclerView);


    }

    @Override
    public void addFootView() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        RecyclerAdapterWithHF adapterHF;
        if (adapter instanceof RecyclerAdapterWithHF) {
            adapterHF = (RecyclerAdapterWithHF) mRecyclerView.getAdapter();
            adapterHF.addFooter(getLoadMoreViewHelper().getFootView());
        }
    }

    @Override
    public void removeFootView() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        RecyclerAdapterWithHF adapterHF;
        if (adapter instanceof RecyclerAdapterWithHF) {
            adapterHF = (RecyclerAdapterWithHF) mRecyclerView.getAdapter();
            if (adapterHF.getFootSize() != 0) {
                adapterHF.removeFooter(getLoadMoreViewHelper().getFootView());
            }
        }
    }

    public RecycleViewScrollHelper getScrollHelper() {
        return mScrollHelper;
    }
}
