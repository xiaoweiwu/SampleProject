package com.common.basecomponent.imagedisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.common.basecomponent.BaseApplication;
import com.common.basecomponent.util.UnitConvertUtil;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by fs_wuxiaowei on 2016/9/12.
 */
public class ImageDisplay {

    public static void display(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }

    /**
     * load normal  for img
     *
     * @param url
     * @param erroImg
     * @param emptyImg
     * @param iv
     */
    public static void display(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        Glide.with(context)
                .load(url)
                .placeholder(emptyImg)
                .error(erroImg)
                .centerCrop()
                .dontAnimate()
                .into(iv);
    }

    /**
     * 显示本地资源图片
     *
     * @param context
     * @param resId
     * @param erroImg
     * @param emptyImg
     * @param iv
     * @param type
     * @param radius
     */
    public static void display(Context context, int resId, int erroImg, int emptyImg, ImageView iv, int type, int radius) {
        Transformation transformation = null;
        if (type == 0) {
            transformation = new CropCircleTransformation(context);
        } else if (type == 1) {
            transformation = new RoundedCornersTransformation(context, UnitConvertUtil.dip2px(context, radius), 0);
        }
        DrawableTypeRequest request = Glide.with(context)
                .load(resId);
        DrawableRequestBuilder builder = null;
        if (emptyImg > 0) {
            builder = request.placeholder(erroImg);
        }
        if (erroImg > 0) {
            if (builder == null) {
                builder = request.error(erroImg);
            } else {
                builder = builder.error(erroImg);
            }
        }
        if (transformation != null) {
            if (builder == null) {
                builder = request.bitmapTransform(transformation);
            } else {
                builder = builder.bitmapTransform(transformation);
            }
        }
        if (builder == null) {
            request.dontAnimate().into(iv);
        } else {
            builder.dontAnimate()
                    .into(iv);
        }
    }

    public static void display(Context context, String url, int erroImg, int emptyImg, ImageView iv, int type, int radius) {
        display(context, url
                , erroImg, emptyImg, iv, type, radius, false);
    }

    /**
     * 显示图片
     *
     * @param url
     * @param erroImg
     * @param emptyImg
     * @param iv
     * @param type     0 圆形 1 圆角,2正常
     */
    public static void display(Context context, String url, int erroImg, int emptyImg, ImageView iv, int type, int radius, boolean isGif) {
        if (isGif) {
            Glide.with(context).load(url).asGif()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(emptyImg)
                    .error(erroImg)
                    .dontAnimate()
                    .into(iv);
        } else {
            Transformation transformation = null;
            if (type == 0) {
                transformation = new CropCircleTransformation(context);
            } else if (type == 1) {
                transformation = new RoundedCornersTransformation(context, UnitConvertUtil.dip2px(context, radius), 0);
            }
            DrawableTypeRequest request = Glide.with(context)
                    .load(url);

            DrawableRequestBuilder builder = null;
            builder = request.diskCacheStrategy(DiskCacheStrategy.RESULT);
            if (emptyImg > 0) {
                builder = request.placeholder(erroImg);
            }
            if (erroImg > 0) {
                builder = builder.error(erroImg);
            }
            if (transformation != null) {
                builder = builder.bitmapTransform(transformation);
            }
            builder.dontAnimate()
                    .into(iv);
        }

    }

    public static void display(Context context, String url, int erroImg, int emptyImg, ImageView iv, int type) {
        display(context, url, erroImg, emptyImg, iv, type, 0);
    }

    public static void display(Context context, String url, int erroImg, int emptyImg, ImageView iv, Transformation<Bitmap>... bitmapTransformations) {
        Glide.with(context)
                .load(url)
                .placeholder(emptyImg)
                .error(erroImg)
                .bitmapTransform(bitmapTransformations)
                .dontAnimate().into(iv);
    }

    public static void display(Context context, String url, int erroImg, int emptyImg, GlideDrawableImageViewTarget iv, Transformation<Bitmap>... bitmapTransformations) {
        Glide.with(context)
                .load(url)
                .placeholder(emptyImg)
                .error(erroImg)
                .bitmapTransform(bitmapTransformations)
                .dontAnimate().into(iv);
    }

    public static void pauseRequests() {
        Glide.with(BaseApplication.get().getApplicationContext()).pauseRequests();
    }

    public static void resumeRequests() {
        Glide.with(BaseApplication.get().getApplicationContext()).resumeRequests();
    }

}
