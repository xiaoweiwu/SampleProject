package com.fullshare.basebusiness.base;


import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.common.basecomponent.adapter.CommonFragmentPagerAdapter;
import com.common.basecomponent.adapter.CommonFragmentStatePagerAdapter;
import com.common.basecomponent.adapter.FragmentHolder;
import com.common.basecomponent.util.UnitConvertUtil;
import com.common.basecomponent.widget.ScrollControlViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.fullshare.basebusiness.R;
import com.fullshare.basebusiness.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaowei on 2017/5/15.
 */

public abstract class BaseTabFragment extends BaseBusinessFragment {
    protected ViewPager viewPager;
    protected SlidingTabLayout tablayout;
    protected List<String> titles = new ArrayList<>();
    protected List<FragmentHolder> fragmentHolders = new ArrayList<>();
    protected List<Fragment> fragments = new ArrayList<>();
    protected PagerAdapter adapter = null;
    private View rlTablayout;
    private boolean recycleFragmentPage = true;

    @Override
    protected void preInit(View view) {
        super.preInit(view);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tablayout = (SlidingTabLayout) view.findViewById(R.id.tablayout);
        rlTablayout = view.findViewById(R.id.rl_tablayout);
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setUpFragments() {
        if (recycleFragmentPage) {
            adapter = new CommonFragmentStatePagerAdapter(getChildFragmentManager(), mContext, fragmentHolders, titles);
        } else {
            adapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), titles, fragments);
        }
        viewPager.setAdapter(adapter);
        ViewUtil.initTab(mContext, tablayout);
        tablayout.setViewPager(viewPager);
        View container = tablayout.getChildAt(0);
        container.setPadding(0, 0, UnitConvertUtil.dip2px(mContext, 70), 0);
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_tab;
    }

}
