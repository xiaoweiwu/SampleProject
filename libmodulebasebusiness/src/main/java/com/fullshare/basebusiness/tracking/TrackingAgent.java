package com.fullshare.basebusiness.tracking;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * author: wuxiaowei
 * date : 2017/4/14
 */

public class TrackingAgent {

    public static void onAppExit(Context context) {
        MobclickAgent.onKillProcess(context);
    }

    public static void onEvent(Context context, String data) {
        addUMEvent(context, data, null);
        TrackingAgent.onEvent(context, data, null);
    }

    public static void onEvent(Context context, String data, HashMap<String, String> extras) {
        addUMEvent(context, data, extras);
    }

    public static void onPageStart(Context context, String data) {
        addUMPageId(data);
        onPageStart(context, data, null);
    }

    public static void onPageStart(Context context, String data, HashMap extras) {
        addUMPageId(data);
    }

    public static void onPageEnd(Context context, String pageId) {
        addUMPageEnd(context, pageId);
    }

    public static void onPageEnd(Context context, HashMap extras, String pageId) {
        addUMPageEnd(context, pageId);
    }

    private static void addUMPageId(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            MobclickAgent.onPageStart(jsonObject.getString("page_id"));
        } catch (JSONException e) {
        }
    }

    private static void addUMPageEnd(Context context, String pageId) {
        MobclickAgent.onPageEnd(pageId);
    }

    private static void addUMEvent(Context context, String data, HashMap<String, String> extras) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String eventId = jsonObject.getString("event_id");
            HashMap map = new HashMap();
            Iterator<String> sIterator = jsonObject.keys();
            while (sIterator.hasNext()) {
                String key = sIterator.next();
                String value = jsonObject.getString(key);
                map.put(key, value);
            }
            if (extras != null) {
                map.putAll(extras);
            }
            MobclickAgent.onEvent(context, eventId, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
