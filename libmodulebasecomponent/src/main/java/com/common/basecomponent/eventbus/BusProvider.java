package com.common.basecomponent.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by fs_wuxiaowei on 2016/9/12.
 */
public class BusProvider {

    private static Bus bus = new Bus();
    private static BusProvider busProvider = new BusProvider();

    private BusProvider() {
    }

    public static BusProvider getInstance() {
        return busProvider;
    }

    /**
     * 注册
     *
     * @param obj
     */
    public void register(Object obj) {
        bus.register(obj);
    }

    /**
     * 取消注册
     *
     * @param obj
     */
    public void unregister(Object obj) {
        bus.unregister(obj);
    }

    /**
     * 发送数据
     *
     * @param data
     */
    public void post(Object data) {
        bus.post(data);
    }
}
