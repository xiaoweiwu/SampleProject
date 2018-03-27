package com.common.basecomponent.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.basecomponent.R;
import com.common.basecomponent.adapter.recyclerview.BaseRecycleViewAdapter;
import com.common.basecomponent.adapter.recyclerview.BaseRecycleViewAdapter.OnItemClickListener;
import com.common.basecomponent.fragment.refresh.LoadMoreHelper;
import com.common.basecomponent.fragment.refresh.LoadMoreHelper.OnLoadMoreStateChangeListener;
import com.common.basecomponent.fragment.refresh.RecycleViewLoadMoreHelper;
import com.common.basecomponent.fragment.refresh.RecycleViewScrollHelper;
import com.common.basecomponent.fragment.refresh.RecyclerAdapterWithHF;
import com.common.basecomponent.util.L;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;


/**
 * 下拉刷新上拉加载RecycleView 公共组件
 * 使用步骤：
 */
public abstract class PullToRefreshRecycleFragmentEx extends PullToRefreshFragmentEx {
    protected RecyclerView mRecyclerView;
    protected BaseRecycleViewAdapter mAdapter;// RecycleView的Adapter
    protected LinearLayoutManager linearLayoutManager;
    protected OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            onItemClicked(v, position);
        }
    };
    boolean isScrollingTop;
    private int dividerHeight;
    private int dividerColor;
    private int scrollY;
    private int scrollX;

    @Override
    protected final void initViewsAndEvents(View rootView) {
        super.initViewsAndEvents(rootView);
        hideToolBar();
        initRecycleView(rootView);
    }

    private void initRecycleView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_recyclerview);
        //设置布局管理--垂直分布
        mRecyclerView.setLayoutManager(layoutManager());
        if (mAdapter == null) {
            mAdapter = initAdapter();
        }
        if (mAdapter != null && mAdapter.getOnItemClickListener() == null) {
            mAdapter.setOnItemClickListener(onItemClickListener);
        }
        //包装带有FootView的Adapter
        mRecyclerView.setAdapter(new RecyclerAdapterWithHF(mAdapter));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isScrollingTop = dy <= 0;
                scrollY -= dy;
                scrollX += dx;
                onScroll(scrollX, scrollY);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    if (isScrollingTop) {
                        scrollY = 0;
                    }
                    onScroll(scrollX, scrollY);
                }
            }
        });
//        mRecyclerView.getAdapter().registerAdapterDataObserver(new AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                super.onItemRangeInserted(positionStart, itemCount);
//                L.e("onItemRangeInserted,"+positionStart+","+itemCount);
//            }
//
//            @Override
//            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
//                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
//                L.e("onItemRangeMoved,"+fromPosition+","+toPosition+","+itemCount);
//            }
//
//            @Override
//            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
//                super.onItemRangeChanged(positionStart, itemCount, payload);
//                L.e("onItemRangeChanged,"+positionStart+","+itemCount);
//            }
//
//            @Override
//            public void onItemRangeRemoved(int positionStart, int itemCount) {
//                super.onItemRangeRemoved(positionStart, itemCount);
//                L.e("onItemRangeRemoved,"+positionStart+","+itemCount);
//            }
//        });
        if (dividerHeight > 0) {
            mRecyclerView.addItemDecoration(getItemDecoration());
        }

        //默认开启上拉加载更多
        if (getSettingOptions().isLoadMoreEnable()) {
            LoadMoreHelper loadMoreHelper = getLoadMoreHelper();
            getLoadingViewController().setLoadMoreHelper(loadMoreHelper);
            loadMoreHelper.setOnLoadMoreStateChangeListenerr(new OnLoadMoreStateChangeListener() {
                @Override
                public void onLoadMore() {
                    PullToRefreshRecycleFragmentEx.this.onLoadMore();
                }

                @Override
                public void onScrollTop() {
                }
            });
            loadMoreHelper.enableLoadMore(true, false);
            RecycleViewScrollHelper recycleViewScrollHelper = ((RecycleViewLoadMoreHelper) loadMoreHelper).getScrollHelper();
            recycleViewScrollHelper.setOnScrollToShowBackTop(new RecycleViewScrollHelper.OnScrollToShowBackTop() {
                @Override
                public void showBackToTop(boolean isShow) {
//                if (isShow) {
//                    mIvBackToTop.setVisibility(View.VISIBLE);
//                } else {
//                    mIvBackToTop.setVisibility(View.GONE);
//                }
                }
            });
        }

    }

    protected void autoRefresh() {
        scrollToPosition(0);
        postDelay(new Runnable() {
            @Override
            public void run() {
                pullRefreshLoad();
            }
        }, 300);
    }

    protected void onScroll(int scrollX, int scrollY) {
    }

    @Override
    protected int getCount() {
        return getAdapter().getItemCount();
    }

    @Override
    protected final void append(List resultModels) {
        int count = getAdapter().getItemCount();
        getAdapter().appendList(resultModels);
        getAdapter().notifyItemRangeInserted(count, resultModels.size());
    }

    protected final void insert(List resultModels, int position) {
        getAdapter().insertList(resultModels, position);
        getAdapter().notifyItemRangeInserted(position, resultModels.size());
    }

    @Override
    protected final void notifyDataSetChanged(List resultModels) {
        getAdapter().fillList(resultModels);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_to_refresh_recycle;
    }

    public final BaseRecycleViewAdapter getAdapter() {
        return mAdapter;
    }

    protected abstract BaseRecycleViewAdapter initAdapter();

    private final RecycleViewLoadMoreHelper getLoadMoreHelper() {
        return new RecycleViewLoadMoreHelper(mRecyclerView, getActivity());
    }

    public LayoutManager layoutManager() {
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                    try {
                        super.onLayoutChildren(recycler, state);
                    } catch (IndexOutOfBoundsException e) {
                        L.e(e, "");
                        e.printStackTrace();
                    }
                }
            };
        }
        return linearLayoutManager;
    }

    /**
     * 添加headerview
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        getRecyclerAdapterWithHF().addHeader(headerView);
    }

    public void addHeaderViewBatch(View headerView) {
        getRecyclerAdapterWithHF().addHeaderBatch(headerView);
    }

    public void addHeaderView(@LayoutRes int resId) {
        addHeaderView(LayoutInflater.from(mContext).inflate(resId, null));
    }

    public void removeHeaderView(View headerView) {
        getRecyclerAdapterWithHF().removeHeader(headerView);
    }

    public View getHeaderView(int position) {
        return getRecyclerAdapterWithHF().getHeader(position);
    }

    public int getHeaderSize() {
        return getRecyclerAdapterWithHF().getHeadSize();
    }

    public void removeAllHeaderView() {
        getRecyclerAdapterWithHF().removeAllHeaders();
    }

    public void addParallaxHeader(View headerView, float rate, ParallaxWrapper.OnParallaxScroll onParallaxScroll) {
        ParallaxWrapper.CustomRelativeWrapper headerWrapper = new ParallaxWrapper.CustomRelativeWrapper(mContext);
        headerWrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerWrapper.addView(headerView);
        new ParallaxWrapper(mRecyclerView, headerWrapper, rate).setOnParallaxScroll(onParallaxScroll);
        getRecyclerAdapterWithHF().addHeader(headerWrapper);
    }

    public void addParallaxHeader(@LayoutRes int resId, float rate, ParallaxWrapper.OnParallaxScroll onParallaxScroll) {
        addParallaxHeader(LayoutInflater.from(mContext).inflate(resId, null), rate, onParallaxScroll);
    }

    public void addFooter(View footerView) {
        getRecyclerAdapterWithHF().addFooter(footerView);
    }

    private RecyclerAdapterWithHF getRecyclerAdapterWithHF() {
        return (RecyclerAdapterWithHF) mRecyclerView.getAdapter();
    }

    public void notifyDataFlowAndHeaders() {
        getRecyclerAdapterWithHF().notifyDataSetChanged();
    }

    public void setDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        //设置分割线--下面可以更改样式，这里应用了别人的分割线开发库
        return new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(dividerColor))
                .sizeResId(dividerHeight)
                .build();
    }

    public void scrollToPosition(int position) {
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(position);
        }
    }
}
