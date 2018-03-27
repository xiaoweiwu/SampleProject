package com.fullshare.basebusiness.downloader;

import android.content.Context;
import android.content.Intent;

import com.common.basecomponent.util.EncryptUtil;

/**
 * Created by wuxiaowei on 2017/4/19.
 */

public class DownloadManager {
    public static final String ACTION = "com.fullshare.fsb.download";
    public static final int INSTALL_APK = 1;
    public static final int SAVE = 2;
    private Context context;
    private DownloadInfo downloadInfo;

    public DownloadManager(Context context) {
        this.context = context;
        downloadInfo = new DownloadInfo();
    }

    public DownloadManager setSaveName(String saveName) {
        downloadInfo.setSaveName(saveName);
        return this;
    }

    public DownloadManager setSavePath(String savePath) {
        downloadInfo.setSavePath(savePath);
        return this;
    }


    public DownloadManager setShowName(String showName) {
        downloadInfo.setShowName(showName);
        return this;
    }

    public DownloadManager setUrl(String url) {
        downloadInfo.setUrl(url);
        return this;
    }

    public DownloadManager setShowNotification(boolean showNotification) {
        downloadInfo.setShowNotification(showNotification);
        return this;
    }

    public DownloadManager setAction(int action) {
        downloadInfo.setAction(action);
        return this;
    }

    public String start() {
        downloadInfo.setTaskId(EncryptUtil.md5(downloadInfo.getUrl()));
        Intent intent = new Intent(context, UpdateDownloadService.class);
        intent.putExtra("download_info", downloadInfo);
        context.startService(intent);
        return downloadInfo.getTaskId();
    }
}
