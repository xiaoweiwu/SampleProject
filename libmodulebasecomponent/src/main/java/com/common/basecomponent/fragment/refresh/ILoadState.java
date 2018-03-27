package com.common.basecomponent.fragment.refresh;

import java.util.List;

/**
 * Created by wuxiaowei on 2017/1/14.
 */

public interface ILoadState {

    /**
     *
     */
    void onLoadComplete(boolean hasMore);

    /**
     * 请求失败
     */
    void onLoadFailed();

    /**
     * @param hasMore 是否还有数据
     */
    void onLoadMoreComplete(boolean hasMore);

    /**
     * 加载更多失败
     */
    void onLoadMoreFailed();

    void notifyResult(List resultModels, boolean loadMore);
}
