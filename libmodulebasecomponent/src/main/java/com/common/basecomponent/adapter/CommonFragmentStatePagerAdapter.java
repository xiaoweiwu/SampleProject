package com.common.basecomponent.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by wuxiaowei on 2017/1/14.
 */

public class CommonFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<FragmentHolder> fragmentHolders;
    private List<String> titles;
    private Context context;

    public CommonFragmentStatePagerAdapter(FragmentManager fm, Context context, List<FragmentHolder> fragmentHolders, List<String> titles) {
        super(fm);
        this.titles = titles;
        this.fragmentHolders = fragmentHolders;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context, fragmentHolders.get(position).getFragmentClass().getName(), fragmentHolders.get(position).getArgs());
    }

    @Override
    public int getCount() {
        return fragmentHolders == null ? 0 : fragmentHolders.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
