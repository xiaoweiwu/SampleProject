package com.common.basecomponent.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

/**
 * author: wuxiaowei
 * date : 2017/4/1
 */

public class ActivityUtil {

    public static void setStickFullScreen(Window window, boolean isFullScreen) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = window.getDecorView();
            v.setSystemUiVisibility(isFullScreen ? View.GONE : View.VISIBLE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= 21) {
                if (isFullScreen) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                            | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
//                window.setStatusBarColor(Color.TRANSPARENT);
            }
            View decorView = window.getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (!isFullScreen) {
                uiOptions = decorView.getSystemUiVisibility() ^ uiOptions;
            }
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void setStickFullScreen2(Window window) {
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.requestFeature(Window.FEATURE_NO_TITLE);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void installApk(Context context, String fileName) {
        Intent intent = getAPkInstallIntent(context, fileName);
        context.startActivity(intent);
    }

    public static Intent getAPkInstallIntent(Context context, String fileName) {
        L.d(fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true); //表明不是未知来源
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.fullshare.fsb.provider", new File(fileName));
            intent.setDataAndType(contentUri, type);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(fileName)), type);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    public static void startActionView(Context context, String url) {
        String realUrl = url;
        if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
            if (realUrl != null) {
                Intent in = new Intent(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(realUrl);
                in.setData(content_url);
                context.startActivity(in);
            }
        }
    }

    public static boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static boolean isWechatInstalled(Context context) {
        return isAppInstalled(context, "com.tencent.mm");
    }

    public static void makePhoneCall(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    public static void openWeChat(Context context) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);
    }

    public static void getAppDetailSettingIntent(Context context) {
        PermissionJumper.goToPermissionSetting((Activity) context);
    }
}
