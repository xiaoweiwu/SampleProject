package com.fullshare.basebusiness.ImageLoader;

import android.content.Context;
import android.widget.ImageView;

import com.common.basecomponent.imagedisplay.ImageDisplay;
import com.fullshare.basebusiness.R;

/**
 * author: wuxiaowei
 * date : 2017/3/28
 */

public class ImageLoader {

    private static void display(Context context, ImageView iv, String url, int defaultRes, DisplayType displayType, int radius, int width, int height) {
        if (url != null && url.startsWith("http")) {
            url = url + "?x-oss-process=image/format,webp";////"?x-oss-process=image/resize,w_720,h_720/quality,q_100";
            if (width > 0 && height > 0) {
//                if(width>1080){
//                    width/=2;
//                    height/=2;
//                }
                String suffix = "/resize,w_" + width + ",h_" + height;
                url += suffix;
            }
        }

        if (displayType == DisplayType.CIRLCE) {
            ImageDisplay.display(context, url, defaultRes, defaultRes, iv, 0);
        } else if (displayType == DisplayType.ROUND) {
            ImageDisplay.display(context, url, defaultRes, defaultRes, iv, 1, radius);
        } else if (displayType == DisplayType.NORMAL) {
            ImageDisplay.display(context, url, defaultRes, defaultRes, iv, 2, 0);
        }
    }

    public static void display(Context context, ImageView iv, String url, int defaultRes, int width, int height) {
        if (defaultRes == 0) {
            defaultRes = R.drawable.bg_loading;
        }
        display(context, iv, url, defaultRes, DisplayType.NORMAL, 0, width, height);
    }

    public static void display(Context context, ImageView iv, String url, int defaultRes) {
        if (defaultRes == 0) {
            defaultRes = R.drawable.bg_loading;
        }
        display(context, iv, url, defaultRes, DisplayType.NORMAL, 0, -1, -1);
    }

    public static void displayCircle(Context context, ImageView iv, String url, int defaultRes, int width, int height) {
        display(context, iv, url, defaultRes, DisplayType.CIRLCE, 0, width, height);
    }

    public static void displayCircle(Context context, ImageView iv, String url, int defaultRes) {
        display(context, iv, url, defaultRes, DisplayType.CIRLCE, 0, -1, -1);
    }

    public static void displayRound(Context context, ImageView iv, String url, int defaultRes, int raduis, int width, int height) {
        display(context, iv, url, defaultRes, DisplayType.ROUND, raduis, width, height);
    }

    public static void displayRound(Context context, ImageView iv, String url, int defaultRes, int raduis) {
        display(context, iv, url, defaultRes, DisplayType.ROUND, raduis, -1, -1);
    }

    /**
     * 显示资源文件图
     *
     * @param context
     * @param iv
     * @param resId
     * @param defaultRes
     */
    public static void display(Context context, ImageView iv, int resId, int defaultRes) {
        display(context, iv, resId, defaultRes, DisplayType.NORMAL);
    }

    /**
     * 显示资源文件图圆角
     *
     * @param context
     * @param iv
     * @param resId
     * @param defaultRes
     */
    public static void displayRound(Context context, ImageView iv, int resId, int defaultRes, int radius) {
        display(context, iv, resId, defaultRes, DisplayType.ROUND, radius);
    }

    /**
     * 显示资源文件图圆形
     *
     * @param context
     * @param iv
     * @param resId
     * @param defaultRes
     */
    public static void displayCircle(Context context, ImageView iv, int resId, int defaultRes) {
        display(context, iv, resId, defaultRes, DisplayType.CIRLCE);
    }

    private static void display(Context context, ImageView iv, int resId, int defaultRes, DisplayType displayType) {
        display(context, iv, resId, defaultRes, displayType, 0);
    }

    private static void display(Context context, ImageView iv, int resId, int defaultRes, DisplayType displayType, int radius) {
        if (displayType == DisplayType.CIRLCE) {
            ImageDisplay.display(context, resId, defaultRes, defaultRes, iv, 0, radius);
        } else if (displayType == DisplayType.ROUND) {
            ImageDisplay.display(context, resId, defaultRes, defaultRes, iv, 1, radius);
        } else if (displayType == DisplayType.NORMAL) {
            ImageDisplay.display(context, resId, defaultRes, defaultRes, iv, 2, radius);
        }
    }


    public enum DisplayType {
        NORMAL, CIRLCE, ROUND
    }
}
