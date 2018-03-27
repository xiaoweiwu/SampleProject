package com.fullshare.basebusiness.base;

import android.content.Context;
import android.content.Intent;


/**
 * Created by wuxiaowei on 2017/1/16.
 */

public class WebViewUtil {

    public static void loadUrl(Context context, Class clazz, String url, String title, int loadId) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(BaseBusinessWebViewActivity.LOAD_UR, url);
        intent.putExtra(BaseBusinessWebViewActivity.TITLE, title);
        intent.putExtra(BaseBusinessWebViewActivity.SHOW_TITLE, true);
        intent.putExtra(BaseBusinessWebViewActivity.LOAD_ID, loadId);
        context.startActivity(intent);
    }

//    public static void loadUrl(Context context, String url, String title,boolean showTitle) {
//        Intent intent = new Intent(context, CommonWebViewActivity.class);
//        intent.putExtra(BaseWebViewActivity.LOAD_UR, url);
//        intent.putExtra(BaseWebViewActivity.TITLE, title);
//        intent.putExtra(BaseWebViewActivity.SHOW_TITLE, showTitle);
//        context.startActivity(intent);
//    }
//    public static void loadUrl(Context context, Class clzz, String url, String title) {
//        Intent intent = new Intent(context, clzz);
//        intent.putExtra(BaseWebViewActivity.LOAD_UR, url);
//        intent.putExtra(BaseWebViewActivity.TITLE, title);
//        intent.putExtra(BaseWebViewActivity.SHOW_TITLE, true);
//        context.startActivity(intent);
//    }
//    public static void loadHtml(Context context, String html, String title) {
//        Intent intent = new Intent(context, CommonWebViewActivity.class);
//        intent.putExtra(BaseWebViewActivity.RICH_TEXT, html);
//        intent.putExtra(BaseWebViewActivity.TITLE, title);
//        intent.putExtra(BaseWebViewActivity.SHOW_TITLE, true);
//        intent.putExtra(BaseWebViewActivity.IS_RICH_TEXT, true);
//        context.startActivity(intent);
//    }


}
