package com.common.basecomponent.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.common.basecomponent.BaseApplication;

import java.io.File;
import java.util.HashMap;

public class SDCardManager {
    private final static String LOG_DIR = "/log/";
    private final static String BMP_DIR = "/bmp/";
    private final static String RECORD_DIR = "/record/";
    private static final String UPGRADE_DIR = "/upgrade/";
    private static final String ADVERTISEMENT_DIR = "/advertisement/";
    private static final String FZF_TEMPLATE_DIR = "/fzf_template/";
    private static HashMap<String, String> mMapUrlMD5 = new HashMap<>();
    private static SDCardManager sInstance;
    private final String mCachePath;
    private final String mFilesPath;

    private SDCardManager(String cachePath, String filesPath) {
        mCachePath = cachePath;
        mFilesPath = filesPath;
    }

    public static SDCardManager get() {
        if (sInstance == null) {
            synchronized (SDCardManager.class) {
                File cacheDir = FileUtil.getDiskCacheDir(BaseApplication.get());
                File diskFileDir = FileUtil.getDiskFileDir(BaseApplication.get());
                sInstance = new SDCardManager(cacheDir.getAbsolutePath(), diskFileDir.getAbsolutePath());
            }
        }
        return sInstance;
    }

    public String getLocalImagePath(String coverUrl) {
        String fileName = getUrlMD5(coverUrl);
        if (fileName == null) return null;
        return getImageFilePath() + fileName;
    }

    @Nullable
    public String getUrlMD5(String url) {
        if (TextUtils.isEmpty(url))
            return null;
        String fileName = mMapUrlMD5.get(url);
        if (fileName == null) {
            fileName = EncryptUtil.md5(url);
            mMapUrlMD5.put(url, fileName);
        }
        return fileName;
    }

    public String getCachePath() {
        return mCachePath;
    }

    public String getFilesPath() {
        return mFilesPath;
    }

    public String getLogFilePath() {
        mkDir(mFilesPath + LOG_DIR);
        return mFilesPath + LOG_DIR;
    }

    public String getRecordPath() {
        mkDir(mFilesPath + RECORD_DIR);
        return mFilesPath + RECORD_DIR;
    }

    public String getImageFilePath() {
        mkDir(mCachePath + BMP_DIR);
        return mCachePath + BMP_DIR;
    }

    public String getUpgradeFilePath() {
        mkDir(mFilesPath + UPGRADE_DIR);
        return mFilesPath + UPGRADE_DIR;
    }

    public String getAdvertisementPath() {
        mkDir(mFilesPath + ADVERTISEMENT_DIR);
        return mFilesPath + ADVERTISEMENT_DIR;
    }

    public String getFzfTemplatePath() {
        mkDir(mFilesPath + FZF_TEMPLATE_DIR);
        return mFilesPath + FZF_TEMPLATE_DIR;
    }

    private void mkDir(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }

    public void clearAllCacheImage() {
        FileUtil.deleteDir(new File(getImageFilePath()));
    }

    public void clearUpgradeFiles() {
        File upgradePath = new File(mFilesPath + UPGRADE_DIR);
        FileUtil.deleteDir(upgradePath);
    }
}
