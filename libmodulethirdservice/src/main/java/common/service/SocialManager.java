package common.service;

import android.content.Context;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

/**
 * Created by wuxiaowei on 2018/3/23.
 */

public class SocialManager {

    private SocialManager() {
    }

    public static SocialManager init(Context context, boolean debug) {
        SocialManager socialManager = new SocialManager();
        UMShareAPI.get(context);
        Config.DEBUG = debug;
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(context).setShareConfig(config);
        return socialManager;
    }

    public SocialManager setWeixin(String id, String secret) {
        PlatformConfig.setWeixin(id,
                secret);
        return this;
    }

    public SocialManager setSinaWeibo(String id, String secret) {
        PlatformConfig.setSinaWeibo(id,
                secret, "http://sns.whalecloud.com/sina2/callback");
        return this;
    }

    public SocialManager setQQZone(String id, String secret) {
        PlatformConfig.setQQZone(id,
                secret);
        return this;
    }
}
