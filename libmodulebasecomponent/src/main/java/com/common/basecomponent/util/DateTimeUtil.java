package com.common.basecomponent.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wuxiaowei on 2017/7/7.
 */

public class DateTimeUtil {

    public static String getFormatDateTimeString() {
        return getFormatDateTimeString(new Date());
    }

    public static String getFormatDateTimeString(Date date) {
        return getFormatDateTimeString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatDateTimeString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getDisplayString(long timestamp) {
        if (timestamp == 0) {
            return "";
        }
        L.e("时间间隔：" + (System.currentTimeMillis() - timestamp));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));
        Calendar current = Calendar.getInstance();

        if (calendar.get(Calendar.YEAR) < current.get(Calendar.YEAR)) {
            return getFormatDateTimeString(calendar.getTime(), "yyyy-MM-dd HH:mm");
        } else {
            if (current.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR) > 1) {
                return getFormatDateTimeString(calendar.getTime(), "MM-dd HH:mm");
            } else if (current.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR) == 1) {
                return getFormatDateTimeString(calendar.getTime(), "昨天HH:mm");
            } else if (current.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY) >= 1) {
                return getFormatDateTimeString(calendar.getTime(), "HH:mm");
            } else if (current.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE) >= 1) {
                return current.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE) + "分钟前";
            } else {
                return "刚刚";
            }
        }

    }
}
