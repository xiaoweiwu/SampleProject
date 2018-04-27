package com.common.basecomponent.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.common.basecomponent.R;
import com.common.basecomponent.adapter.list.BaseListAdapter;
import com.common.basecomponent.fragment.refresh.ListViewLoadMoreHelper;
import com.common.basecomponent.fragment.refresh.LoadMoreHelper;
import com.common.basecomponent.fragment.refresh.LoadMoreHelper.OnLoadMoreStateChangeListener;
import com.common.basecomponent.imagedisplay.ImageDisplay;

import java.util.List;


/**
 * create by wuxiaowei
 * 下拉刷新上拉加载RecycleView 公共组件
 * 使用步骤：
 */
public abstract class PullToRefreshListViewFragmentEx extends PullToRefreshFragmentEx {
    protected ListView listView;
    //    private ImageView mIvBackToTop;
    protected BaseListAdapter mAdapter;
    private int mCurrentScrollState;

    @Override
    protected void initViewsAndEvents(View rootView) {
        super.initViewsAndEvents(rootView);
        if (getToolBarEx() != null)
            getToolBarEx().hide();
        initRecycleView(rootView);
    }

    private void initRecycleView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.id_listView);
//        mIvBackToTop = (ImageView) rootView.findViewById(R.id.iv_back_to_top);

//        mIvBackToTop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listView.smoothScrollToPosition(0);
//            }
//        });

        if (mAdapter == null) {
            mAdapter = initAdapter();
        }
        final LoadMoreHelper loadMoreHelper = new ListViewLoadMoreHelper(mContext, listView);
        getLoadingViewController().setLoadMoreHelper(loadMoreHelper);
        loadMoreHelper.enableLoadMore(true, false);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PullToRefreshListViewFragmentEx.this.onItemClicked(view, position,mAdapter.getItem(position));
            }
        });

        listView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mCurrentScrollState = scrollState;
                if (scrollState == SCROLL_STATE_IDLE) {
                    ImageDisplay.resumeRequests();
                } else if (scrollState == SCROLL_STATE_FLING) {
                    ImageDisplay.pauseRequests();
                } else if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    ImageDisplay.resumeRequests();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
                if (getSettingOptions().isLoadMoreEnable() && !loadMore)
                    return;
                if (visibleItemCount == totalItemCount) {
                    return;
                }
                if (getSettingOptions().isLoadMoreEnable() && loadMore && mCurrentScrollState != SCROLL_STATE_IDLE) {
                    loadMoreHelper.loadMore();
                }

            }
        });
        loadMoreHelper.setOnLoadMoreStateChangeListenerr(new OnLoadMoreStateChangeListener() {
            @Override
            public void onLoadMore() {
                PullToRefreshListViewFragmentEx.this.onLoadMore();
            }

            @Override
            public void onScrollTop() {

            }
        });
    }

    @Override
    protected final int getCount() {
        return getAdapter().getCount();
    }

    @Override
    protected final void append(List resultModels) {
        getAdapter().append(resultModels);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    protected final void notifyDataSetChanged(List resultModels) {
        getAdapter().notifyDataSetChanged(resultModels);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_to_refresh_listview;
    }

    public final BaseListAdapter getAdapter() {
        return mAdapter;
    }

    protected abstract BaseListAdapter initAdapter();
}
