/**
 *
 */
package com.common.basecomponent.util;

import com.common.basecomponent.SystemInfo;
import com.orhanobut.logger.Logger;

/**
 * @author WUXIAOWEI
 * @version 1.0
 */
public class L {

    public static final String TAG = "GYM";

    public static void d(String message, Object... args) {
        if (logEnable())
            Logger.d(message, args);
    }

    public static void d(Object object) {
        if (logEnable())
            Logger.d(object);
    }

    public static void e(String message, Object... args) {
        if (logEnable())
            Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (logEnable())
            Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        if (logEnable())
            Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        if (logEnable())
            Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        if (logEnable())
            Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        if (logEnable())
            Logger.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        if (logEnable())
            Logger.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        if (logEnable())
            Logger.xml(xml);
    }
    
    public static boolean logEnable(){
        return SystemInfo.getSystemInfo().isLogDebug();
    }
}
