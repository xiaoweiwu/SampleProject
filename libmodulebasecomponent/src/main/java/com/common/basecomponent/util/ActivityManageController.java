package com.common.basecomponent.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用工具类，用于Activity的弹栈与入栈
 */
public class ActivityManageController {
    public static final String TAG = ActivityManageController.class.getSimpleName();
    /**
     * 实例
     */
    private static ActivityManageController instance;
    /**
     * Activity栈集合
     */
    private List<Activity> mActivities = new ArrayList<Activity>();

    /**
     * 构造方法私有
     */
    private ActivityManageController() {
    }

    /**
     * 返回实例，单例实现
     *
     * @return 实例
     */
    public static ActivityManageController getInstance() {
        if (instance == null) {
            synchronized (ActivityManageController.class) {
                if (instance == null) {
                    instance = new ActivityManageController();
                }
            }
        }
        return instance;
    }

    /**
     * Activity入栈
     *
     * @param activity 即将入栈的activity
     */
    public void pushActivity(Activity activity) {
        if (activity != null && !mActivities.contains(activity)) {
            // activity集合中未包含该栈
            mActivities.add(activity);
        }
    }

    /**
     * Activity弹栈
     *
     * @param activity 即将弹栈的activity
     */
    public void popActivity(Activity activity) {
        if (activity != null && mActivities.contains(activity)) {
            // activity集合中已包含该栈
            mActivities.remove(activity);
        }
    }

    /**
     * 清除所有的Activity
     */
    public void finishAllActivities() {
        for (Activity activity : mActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public int getActivityCount() {
        return mActivities.size();
    }
}
