package com.common.basecomponent.util;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: wuxiaowei
 * date : 2017/3/27
 */

public class StringUtil {

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText());
    }

    /**
     * 获取京东的跳转链接
     *
     * @param url
     * @return
     */
    public static String getJDJumpUrl(String url) {
        String productId = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        String baseUrl = "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"productDetail\",\"skuId\":\"%s\",\"sourceType\":\"JSHOP_SOURCE_TYPE\",\"sourceValue\":\"JSHOP_SOURCE_VALUE\"}";
        return String.format(baseUrl, productId);
    }

    /**
     * 手机号账号344格式格式化
     *
     * @param s
     * @return String
     */
    public static String format343(String s) {
        return (s != null && s.length() >= 6) ? s.substring(0, 3) + "*****" + s.substring(s.length() - 3, s.length()) : s;
    }

    /**
     * 手机号码格式判断
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNumber(String phone) {
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(phone);
        return m.find();
    }

    public static boolean isPhoneNumber(TextView phone) {
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(phone.getText().toString());
        return m.find();
    }

    public static String phoneHideDisplay(String phone) {
        return (phone != null && phone.length() == 11) ? phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2") : phone;
    }

    public static boolean textLenValid(TextView textView, int len) {
        return textView.getText().toString().length() >= len;
    }

    public static String getFormatTime(int seconds) {
        int ss = seconds % 60;
        int mm = seconds / 60;

        return String.format("%02d:%02d", mm, ss);
    }

    public static String getFormatNum(int number) {
        if (number > 10000000) {
            return String.format("%d千万+", number / 10000000);
        }
        if (number > 10000) {
            return String.format("%d万", number / 10000);
        }
        if (number > 1000) {
            return String.format("%.1fK", number / 1000.f);
        }
        return String.valueOf(number);
    }
}
