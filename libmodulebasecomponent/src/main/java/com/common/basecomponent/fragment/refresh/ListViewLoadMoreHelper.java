package com.common.basecomponent.fragment.refresh;

import android.content.Context;
import android.widget.ListView;

/**
 * Created by wuxiaowei on 2017/2/9.
 */

public class ListViewLoadMoreHelper extends LoadMoreHelper {
    private ListView listView;

    public ListViewLoadMoreHelper(Context mContext, ListView listView) {
        super(mContext);
        this.listView = listView;
    }

    @Override
    public void removeFootView() {

    }

    @Override
    public void addFootView() {
        listView.addFooterView(getLoadMoreViewHelper().getFootView());
    }
}
