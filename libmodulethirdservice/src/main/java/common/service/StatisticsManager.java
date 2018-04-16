package common.service;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by wuxiaowei on 2018/3/23.
 */

public class StatisticsManager {
    public static final boolean ENABLE = false;
    public static void init(Context context, String appKey, String channel) {
        if(ENABLE) {
            MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
            MobclickAgent.openActivityDurationTrack(false);
            MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(context, appKey, channel));
        }
    }

    public static void onAppExit(Context context) {
        if(ENABLE) {
            MobclickAgent.onKillProcess(context);
        }
    }

    public static void reportError(Context context,String msg){
        if(ENABLE) {
            MobclickAgent.reportError(context, msg);
        }
    }

    public static void reportError(Context context,Throwable throwable){
        if(ENABLE) {
            MobclickAgent.reportError(context, throwable);
        }
    }

    public static void onResume(Context context){
        if(ENABLE) {
            MobclickAgent.onResume(context);
        }
    }

    public static void onPause(Context context){
        if(ENABLE) {
            MobclickAgent.onPause(context);
        }
    }

    public static void onPageStart(String pageId){
        if(ENABLE) {
            MobclickAgent.onPageStart(pageId);
        }
    }

    public static void onPageEnd(String pageId){
        if(ENABLE) {
            MobclickAgent.onPageEnd(pageId);
        }
    }

    public static void onEvent(Context context, String eventId, HashMap extras){
        if(ENABLE) {
            MobclickAgent.onEvent(context,eventId,extras);
        }
    }
}
