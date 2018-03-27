package com.fullshare.basebusiness.data;

import android.content.Context;

import com.common.basecomponent.BaseApplication;

/**
 * author: chenweibin
 * date: 2017/4/26
 */

public class SearchHistoryManager extends HistoryManagerBase<String> {
    private static final int MAX_SIZE = 12;
    private static SearchHistoryManager sInstance;

    private SearchHistoryManager(Context context) {
        super(context.getDir(DataCacheConstant.DIR_SEARCH, Context.MODE_PRIVATE).getAbsolutePath() + DataCacheConstant.SEARCH_HISTORY, MAX_SIZE);
    }

    public static SearchHistoryManager get() {
        if (sInstance == null) {
            synchronized (SearchHistoryManager.class) {
                sInstance = new SearchHistoryManager(BaseApplication.get());
            }
        }
        return sInstance;
    }
}
