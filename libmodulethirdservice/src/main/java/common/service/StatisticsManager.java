package common.service;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by wuxiaowei on 2018/3/23.
 */

public class StatisticsManager {
    public static void init(Context context, String appKey, String channel) {
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(context, appKey, channel));
    }
}
