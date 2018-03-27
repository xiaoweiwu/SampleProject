package com.common.basecomponent.adapter;

import android.os.Bundle;

/**
 * Created by wuxiaowei on 2017/7/3.
 */

public class FragmentHolder {
    private Class fragmentClass;
    private Bundle args;

    public FragmentHolder(Class fragment, Bundle args) {
        this.fragmentClass = fragment;
        this.args = args;
    }

    public Bundle getArgs() {
        return args;
    }

    public Class getFragmentClass() {
        return fragmentClass;
    }
}
