package com.fullshare.basebusiness.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.common.basecomponent.net.OkHttpHelper;
import com.common.basecomponent.util.SDCardManager;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * author: wuxiaowei
 * date : 2017/3/27
 */

public class GlideCacheModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int cacheSize = 100 * 1024 * 1024;
        builder.setDiskCache(new DiskLruCacheFactory(SDCardManager.get().getImageFilePath(), cacheSize));
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 16;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        OkHttpClient client = OkHttpHelper.getClient();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
}
