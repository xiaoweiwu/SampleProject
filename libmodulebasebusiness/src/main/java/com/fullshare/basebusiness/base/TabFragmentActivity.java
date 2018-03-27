package com.fullshare.basebusiness.base;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.common.basecomponent.adapter.CommonFragmentPagerAdapter;
import com.common.basecomponent.adapter.CommonFragmentStatePagerAdapter;
import com.common.basecomponent.adapter.FragmentHolder;
import com.common.basecomponent.widget.ScrollControlViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.fullshare.basebusiness.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wuxiaowei on 2017/2/17.
 */

public abstract class TabFragmentActivity extends CommonBaseActivity {
    protected ViewPager viewPager;
    protected SlidingTabLayout tablayout;
    protected List<String> titles = new ArrayList<>();
    protected List<FragmentHolder> fragmentHolders = new ArrayList<>();
    protected List<Fragment> fragments = new ArrayList<>();
    private View rlTablayout;
    private boolean recycleFragmentPage = true;

    public void setUpFragments() {
        PagerAdapter adapter = null;
        if (recycleFragmentPage) {
            adapter = new CommonFragmentStatePagerAdapter(getSupportFragmentManager(), mContext, fragmentHolders, titles);
        } else {
            adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        }
        viewPager.setAdapter(adapter);
        ViewUtil.initTab(mContext, tablayout);
        tablayout.setViewPager(viewPager);
    }

    public void setViewPagerScrollEnable(boolean enable) {
        if (viewPager != null) {
            if (viewPager instanceof ScrollControlViewPager) {
                ((ScrollControlViewPager) viewPager).setScrollEnable(enable);
            }
        }
    }

    public void setTablayoutVisible(boolean show) {
        rlTablayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setRecycleFragmentPage(boolean recycleFragmentPage) {
        this.recycleFragmentPage = recycleFragmentPage;
    }

}
