package com.common.basecomponent.fragment.base;

import java.util.HashMap;

/**
 * Created by wuxiaowei on 2017/1/14.
 */

public interface IRequestRefreshList {
    /**
     * 返回刷新对象，封装请求路径、分页加载数等信息
     *
     * @return 刷新对象
     */
    BaseRefreshData getRefreshData();

    /**
     * 添加的自定义参数
     */
     void addCustomParam(HashMap<String, Object>param);

}
