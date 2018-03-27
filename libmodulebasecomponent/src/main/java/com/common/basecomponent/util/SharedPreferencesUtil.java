package com.common.basecomponent.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.common.basecomponent.BaseApplication;

import java.util.Map;

/**
 * User: wuxiaowei
 * Date: 2015-10-26
 * Time: 16:49
 */
public class SharedPreferencesUtil {

    public static final String GUIDE_FILE = "guide";
    private static final String KEY_IS_CRASHED = "isCrashed";
    public static String DEFAULT_FILE_NAME = "fullshare_pref_default";
    public static String FILE_NAME = DEFAULT_FILE_NAME;
    private static SharedPreferencesUtil sharedpreferencesUtil = new SharedPreferencesUtil();

    private static SharedPreferences sp;

    private SharedPreferencesUtil() {
    }

    private static SharedPreferences getSp(String filename) {
        return BaseApplication.get().getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getDefault() {
        FILE_NAME = DEFAULT_FILE_NAME;
        sp = getSp(FILE_NAME);
        return sharedpreferencesUtil;
    }

    public static SharedPreferencesUtil get(String fileName) {
        FILE_NAME = fileName;
        sp = getSp(FILE_NAME);
        return sharedpreferencesUtil;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public String put(String key, Object object) {
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        editor.commit();
        return key;
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key).commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }


    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }
}
