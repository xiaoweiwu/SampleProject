package com.common.basecomponent.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.view.ViewGroup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 位图对象工具类
 */
public class BitmapUtil {
    /**
     * 从字节数组中解析出位图对象
     *
     * @param data 字体数组
     * @return 位图对象
     */
    public static Bitmap getBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * 从字节数组中解析出位图对象，指定缩小为原图的几分之一
     *
     * @param data  字节数组
     * @param scale 宽高均缩小为原来的1/scale
     * @return 位图对象
     */
    public static Bitmap getBitmap(byte[] data, int scale) {
        Options opts = new Options();
        opts.inSampleSize = scale;
        return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
    }

    /**
     * 从字节数组中解析出位图对象，指定宽高
     *
     * @param data   字节数组
     * @param width  宽
     * @param height 高
     * @return 位图对象
     */
    public static Bitmap getBitmap(byte[] data, int width, int height) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        int x = opts.outWidth / width;
        int y = opts.outHeight / height;
        int scale = x > y ? x : y;
        return getBitmap(data, scale);
    }


    public static Bitmap getSzieBitmapFromUri(Context context, Uri uri, int size) throws FileNotFoundException {
        int insampleSize = decodeBitmapRatio(context.getContentResolver().openInputStream(uri), size);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = insampleSize;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
    }

    /**
     * 质量压缩
     *
     * @param bitmap  原始位图
     * @param format  可选PNG与JPEG
     * @param quality 大于0小于100，数值越高质量越好
     * @return 位图对象
     */
    public static Bitmap compressBitmap(Bitmap bitmap,
                                        Bitmap.CompressFormat format, int quality) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(format, 100, out);
        if (out.toByteArray().length / 1024 > 1024) {
            // 图片大于1M
            out.reset();
            bitmap.compress(format, quality, out);
        }
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        out.reset();
        out.close();
        Options opts = new Options();
        Bitmap compressImage = BitmapFactory.decodeStream(in, null, opts);
        in.close();
        return compressImage;
    }

    /**
     * 宽度压缩
     *
     * @param bitmap  原始位图
     * @param format  可选PNG与JPEG
     * @param quality 大于0小于100，数值越高质量越好
     * @param width   目标宽度
     * @param height  目标高度
     * @return 位图对象
     * @throws IOException 异常信息
     */
    public static Bitmap scaleBitmap(Bitmap bitmap,
                                     Bitmap.CompressFormat format, int quality, int width, int height)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(format, 100, out);
        if (out.toByteArray().length / 1024 > 1024) {
            // 图片大于1M
            out.reset();
            bitmap.compress(format, quality, out);
        }
        out.close();
        return getBitmap(out.toByteArray(), width, height);
    }

    /**
     * 将Drawable对象缩放至指定大小
     *
     * @param drawable 原绘图对象
     * @param w        目标宽度
     * @param h        目标高度
     * @return 目标绘图对象
     */
    public static Drawable getDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oriBitmap = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap desBitmap = Bitmap.createBitmap(oriBitmap, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, desBitmap);
    }

    /**
     * 将Drawable对象转Bitmap类型
     *
     * @param drawable 绘图对象
     * @return 位图对象
     */
    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String bitmapUriToBase64(Context context, Uri imgUri, int size) throws FileNotFoundException {
        return bitmapToBase64(getSzieBitmapFromUri(context, imgUri, size));
    }

    /**
     * 根据uri获取图片bitmap
     *
     * @param uri
     * @param size
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Bitmap getThumbnail(Uri uri, int size, ContentResolver cr) throws IOException {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = cr.openInputStream(uri);
            int ratio = decodeBitmapRatio(in, size);

            in = cr.openInputStream(uri);
            bitmap = decodeBitmap(in, ratio);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
        return bitmap;
    }


    /**
     * 描述：通过文件的本地地址从SD卡读取图片.
     *
     * @return Bitmap 图片
     * @paramfile the file
     */
    public static Bitmap getBitmapFromSD(String filepath, int size) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            //SD卡是否存在
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {
                return null;
            }
            File file = new File(filepath);
            //文件是否存在
            if (!file.exists()) {
                return null;
            }
            //文件存在
            in = new FileInputStream(file);
            int ratio = decodeBitmapRatio(in, size);

            in = new FileInputStream(file);
            bitmap = decodeBitmap(in, ratio);
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
        return bitmap;
    }


    /**
     * 读取图片预期大小比例
     *
     * @param input
     * @param size
     * @return
     */
    private static int decodeBitmapRatio(InputStream input, int size) {
        Options onlyBoundsOptions = new Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;// optional
        onlyBoundsOptions.inPreferredConfig = Config.RGB_565;// optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return 1;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight
                : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0)
            k = 1;
        return k;
    }

    /**
     * 根据预期比例读取图片
     *
     * @param input
     * @return
     * @paramsize
     */
    private static Bitmap decodeBitmap(InputStream input, int ratio) {
        Options bitmapOptions = new Options();
        bitmapOptions.inSampleSize = ratio;
        bitmapOptions.inDither = true;// optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        return bitmap;
    }

    public static Bitmap getViewGroupChildBitmap(ViewGroup viewGroup) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            h += viewGroup.getChildAt(i).getMeasuredHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(viewGroup.getWidth(), h, Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        viewGroup.draw(canvas);
        return bitmap;
    }

    public static Bitmap getViewGroupBitmap(ViewGroup viewGroup) {
        return getViewGroupBitmap(viewGroup, Config.RGB_565);
    }

    public static Bitmap getViewGroupBitmap(ViewGroup viewGroup, Config config) {
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), config);
        final Canvas canvas = new Canvas(bitmap);
        viewGroup.draw(canvas);
        return bitmap;
    }

    public static Bitmap filterMaxViewBitmap(Bitmap src) {
        if (src == null)
            return null;
        int height = src.getHeight();
        int width = src.getWidth();
        int max = height > width ? height : width;
        int limitSize = 4000;

        if (max > limitSize) {
            float scale = 1.0f * limitSize / max;
            width = (int) (width * scale);
            height = (int) (height * scale);
            return Bitmap.createScaledBitmap(src, width, height, false);
        } else {
            return src;
        }
    }

    public static String getViewGroupBitmapPath(ViewGroup viewGroup) {
        Bitmap bitmap = getViewGroupChildBitmap(viewGroup);
        if (bitmap == null)
            return null;
        String imagePath = FileUtil.saveImage(viewGroup.getContext(), bitmap);
        bitmap.recycle();
        return imagePath;
    }
}
