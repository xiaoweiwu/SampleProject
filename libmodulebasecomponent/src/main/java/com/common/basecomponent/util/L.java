/**
 *
 */
package com.common.basecomponent.util;

import com.common.basecomponent.BaseApplication;
import com.orhanobut.logger.Logger;

/**
 * @author WUXIAOWEI
 * @version 1.0
 */
public class L {

    public static final String TAG = "fullshare";

    public static void d(String message, Object... args) {
        if (BaseApplication.get().isLogDebug())
            Logger.d(message, args);
    }

    public static void d(Object object) {
        if (BaseApplication.get().isLogDebug())
            Logger.d(object);
    }

    public static void e(String message, Object... args) {
        if (BaseApplication.get().isLogDebug())
            Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (BaseApplication.get().isLogDebug())
            Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        if (BaseApplication.get().isLogDebug())
            Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        if (BaseApplication.get().isLogDebug())
            Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        if (BaseApplication.get().isLogDebug())
            Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        if (BaseApplication.get().isLogDebug())
            Logger.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        if (BaseApplication.get().isLogDebug())
            Logger.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        if (BaseApplication.get().isLogDebug())
            Logger.xml(xml);
    }
}
