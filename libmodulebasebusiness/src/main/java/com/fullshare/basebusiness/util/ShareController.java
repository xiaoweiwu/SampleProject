package com.fullshare.basebusiness.util;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.common.basecomponent.util.PermissionsUtil;
import com.fullshare.basebusiness.R;
import com.fullshare.basebusiness.api.ApiConstant;
import com.fullshare.basebusiness.constants.AppConfig;
import com.fullshare.basebusiness.tracking.TrackingAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;

import java.io.File;

/**
 * Created by wuxiaowei on 2017/9/15.
 */

public class ShareController {
    public static final int TYPE_SHARE_APP = 1;
    public static final int TYPE_SHARE_URL = 2;
    public static final int TYPE_SHARE_IMG = 3;
    public static final int TYPE_SHARE_WXAPP = 4;

    private int shareType;
    private String title;
    private String subTitle;
    private String url;
    private String path;
    private Object image;
    private Object thumb;

    public boolean showToast = true;
    protected OnUserActionListener onUserActionListener;
    private Context mContext;

    protected ShareController(Builder builder) {
        this.mContext = builder.context;
        this.shareType = builder.shareType;
        this.title = builder.title;
        this.subTitle = builder.subTitle;
        this.url = builder.url;
        this.path = builder.path;
        this.image = builder.image;
        this.thumb = builder.thumb;
        this.onUserActionListener = builder.onUserActionListener;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getThumb() {
        return thumb;
    }

    public void setThumb(Object thumb) {
        this.thumb = thumb;
    }

    public OnUserActionListener getOnUserActionListener() {
        return onUserActionListener;
    }

    public void setOnUserActionListener(OnUserActionListener onUserActionListener) {
        this.onUserActionListener = onUserActionListener;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void doShare(int position, SHARE_MEDIA shareMedia) {
        if (shareMedia == null) {
            return;
        }
        if (onUserActionListener != null) {
            onUserActionListener.onUserAction(position, shareMedia);
        }
        final ShareAction shareAction = new ShareAction((Activity) mContext).setPlatform(shareMedia);
        if (shareType == TYPE_SHARE_URL) {
            onShareUrl(shareAction, shareMedia);
        } else if (shareType == TYPE_SHARE_APP) {
            onHandleAppShare(shareAction, shareMedia);
        } else if (shareType == TYPE_SHARE_IMG) {
            onShareImage(shareAction, shareMedia);
        } else if (shareType == TYPE_SHARE_WXAPP) {
            onShareUMMin(shareAction, shareMedia);
        }
        shareAction.setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                if (onUserActionListener != null) {
                    onUserActionListener.onStart(share_media);
                }
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                if (onUserActionListener != null) {
                    onUserActionListener.onResult(share_media);
                }

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                if (onUserActionListener != null) {
                    onUserActionListener.onError(share_media, throwable);
                }

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                if (onUserActionListener != null) {
                    onUserActionListener.onCancel(share_media);
                }
            }
        });
        if (shareMedia == SHARE_MEDIA.QQ) {
            new PermissionsUtil((Activity) mContext)
                    .setPermissons(permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE)
                    .setMessage(mContext.getString(R.string.share_qq_no_permission))
                    .setOnPermissionGrantedListener(new PermissionsUtil.OnPermissionGrantedListener() {
                        @Override
                        public void onPermissionGranted(boolean granted, boolean userCancel) {
                            if (granted) {
                                shareAction.share();
                            }
                        }
                    }).startReqestPermissions();
        } else {
            shareAction.share();
        }
    }

    private void onShareUrl(ShareAction shareAction, SHARE_MEDIA shareMedia) {
        UMImage shareImage = getImage(thumb);
        if (shareMedia == SHARE_MEDIA.SINA) {
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(title)) {
                sb.append(title);
            }
            if (!TextUtils.isEmpty(subTitle)) {
                sb.append(subTitle);
            }
            if (!TextUtils.isEmpty(url)) {
                sb.append(url);
            }
            if (sb.length() > 0) {
                shareAction.withText(sb.toString());
            }
            shareAction.withMedia(shareImage);

        } else if (shareMedia == SHARE_MEDIA.WEIXIN && !TextUtils.isEmpty(path)) {
            onShareUMMin(shareAction, null);
        } else {
            shareAction.withMedia(new UMWeb(
                    url,
                    title,
                    subTitle,
                    shareImage));
        }
    }

    private void onShareImage(ShareAction shareAction, SHARE_MEDIA shareMedia) {
        if (!TextUtils.isEmpty(title)) {
            shareAction.withText(title);
        }
        UMImage shareImage = getImage(image);
        if (shareImage != null) {
            shareAction.withMedia(shareImage);
        }
    }

    private void onShareUMMin(ShareAction shareAction, SHARE_MEDIA shareMedia) {
        UMMin umMin = new UMMin(url);
        umMin.setThumb(getImage(thumb));
        umMin.setTitle(title);
        umMin.setDescription(subTitle);
        umMin.setPath(path);
        umMin.setUserName(AppConfig.WX_APP_NAME);
        shareAction.withMedia(umMin);
    }

    public UMImage getImage(Object imageSource) {
        UMImage shareImage = null;
        if (imageSource != null) {
            if (imageSource instanceof File) {
                shareImage = new UMImage(mContext, (File) imageSource);
            } else if (imageSource instanceof String) {
                shareImage = new UMImage(mContext, (String) imageSource);
            } else if (imageSource instanceof Integer) {
                shareImage = new UMImage(mContext, (Integer) imageSource);
            } else if (imageSource instanceof Bitmap) {
                shareImage = new UMImage(mContext, (Bitmap) imageSource);
            } else {
                shareImage = new UMImage(mContext, R.drawable.ic_share_logo_normal);
            }
        } else {
            shareImage = new UMImage(mContext, R.drawable.ic_share_logo_normal);
        }
        return shareImage;
    }

    private void onHandleAppShare(ShareAction shareAction, SHARE_MEDIA shareMedia) {
        if (shareMedia == SHARE_MEDIA.SINA) {
            TrackingAgent.onEvent(mContext, "{\"event_id\":104006,\"event_name\":\"选择新浪微博\",\"action_type\":\"点击\"}");
        } else if (shareMedia == SHARE_MEDIA.WEIXIN) {
            TrackingAgent.onEvent(mContext, "{\"event_id\":104007,\"event_name\":\"选择微信\",\"action_type\":\"点击\"}");
        } else if (shareMedia == SHARE_MEDIA.WEIXIN_CIRCLE) {
            TrackingAgent.onEvent(mContext, "{\"event_id\":104008,\"event_name\":\"选择微信朋友圈\",\"action_type\":\"点击\"}");
        } else if (shareMedia == SHARE_MEDIA.QQ) {
            TrackingAgent.onEvent(mContext, "{\"event_id\":104009,\"event_name\":\"选择QQ\",\"action_type\":\"点击\"}");
        }

        if (shareMedia == SHARE_MEDIA.SINA) {
            shareAction.withText(title + subTitle + ApiConstant.APP_SHARE_URL)
                    .withMedia(new UMImage(mContext, R.drawable.ic_share_logo_normal));
        } else {
            shareAction.withMedia(new UMWeb(
                    url,
                    title,
                    subTitle,
                    new UMImage(mContext, R.drawable.ic_share_logo_normal)));
        }
    }

    public static class OnUserActionListener {
        public void onUserAction(int position, SHARE_MEDIA shareMedia) {

        }

        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onResult(SHARE_MEDIA share_media) {
            onDone(share_media);
        }

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            onDone(share_media);
        }

        public void onCancel(SHARE_MEDIA share_media) {
            onDone(share_media);
        }

        public void onDone(SHARE_MEDIA share_media) {

        }
    }

    public static class Builder {
        protected int shareType;
        protected String title;
        private String subTitle;
        protected String url;
        protected String path;
        private Object image;
        private Object thumb;
        protected Context context;
        protected OnUserActionListener onUserActionListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setThumb(Object thumb) {
            this.thumb = thumb;
            return this;
        }

        public Builder setImage(Object image) {
            this.image = image;
            return this;
        }

        public Builder setShareType(int shareType) {
            this.shareType = shareType;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setShareInfo(final String title, String subTitle, final String url) {
            this.title = title;
            this.subTitle = subTitle;
            this.url = url;
            return this;
        }

        public Builder setOnUserActionListener(OnUserActionListener onUserActionListener) {
            this.onUserActionListener = onUserActionListener;
            return this;
        }

        public ShareController build() {
            return new ShareController(this);
        }
    }
}
