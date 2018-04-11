package com.common.basecomponent.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.common.basecomponent.net.OkHttpHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by wuxiaowei on 2017/1/11.
 */

public class FileUtil {

    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrDirSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormatFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormatFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormatFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    public static File createTmpFile(Context context) {
        String cacheDir = SDCardManager.get().getCachePath();
        return new File(cacheDir, System.currentTimeMillis() + "temp_image.jpg");
    }

    public static void asyncSaveBitmap(final Context context, final Bitmap bitmap, final String fileName, final OnSaveFileListener saveFileListener) {
        if (saveFileListener != null) {
            saveFileListener.onStart();
        }
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try {
                    Bitmap bitmap1 = null;
                    if (saveFileListener != null) {
                        bitmap1 = Bitmap.createBitmap(bitmap);
                    }
                    String file = saveBitmapFile(context, bitmap, fileName);
                    if (saveFileListener != null) {
                        saveFileListener.onAfterSave(file, bitmap1);
                    }
                    emitter.onNext(file);
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (saveFileListener != null) {
                            saveFileListener.onSuccess(fileName);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (saveFileListener != null) {
                            saveFileListener.onFailed(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static boolean downloadTemplateImage(String url, String filename) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            ResponseBody responseBody = OkHttpHelper.download(url);
            if (responseBody == null) {
                return false;
            }
            in = responseBody.byteStream();
            String targetName = filename;
            File tmpFile = new File(targetName + ".tmp");
            out = new FileOutputStream(tmpFile);
            byte[] buf = new byte[1024 * 8];
            int len = -1;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
            tmpFile.renameTo(new File(targetName));
            return true;

        } catch (IOException e) {
            L.e(e, "");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static String getSaveImageFile(String name) {
        if (FileUtil.externalMemoryAvailable()) {
            File saveDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/丰盛榜/");
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/丰盛榜/" + name;
        }
        return null;
    }


    public static String saveBitmapFile(Context context, Bitmap bitmap, String fileName) throws Exception {
        if (FileUtil.externalMemoryAvailable()) {
            File saveDir = null;
            if (TextUtils.isEmpty(fileName)) {
                saveDir = context.getExternalCacheDir();
            } else {
                saveDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/丰盛榜/");
            }

            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            boolean broadcast = true;
            if (TextUtils.isEmpty(fileName)) {
                fileName = System.currentTimeMillis() + ".jpg";
                broadcast = false;
            }
            File saveFile = new File(saveDir, fileName);
            if (!saveFile.exists()) {
                saveFile.createNewFile();
                FileUtil.saveImage(saveFile, bitmap);
                if (broadcast) {
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(saveFile)));
                }
                return saveFile.getAbsolutePath();
            } else {
                return saveFile.getAbsolutePath();
            }
        }
        return null;
    }

    public static String saveImage(Context context, Bitmap bmp) {
        File file = createTmpFile(context);
        return saveImage(file, bmp);
    }

    private static String saveImage(File file, Bitmap bmp) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    public static String readStringFromInputStream(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    public static void deleteDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static void deleteFiles(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file); // 递规的方式删除文件夹
        }
    }

    public static File getDiskCacheDir(Context context) {
        File cachePath = null;
        if (externalMemoryAvailable()) {
            cachePath = context.getExternalCacheDir();
        }
        if (cachePath == null) {
            cachePath = context.getCacheDir();
        }
        return cachePath;
    }

    public static boolean externalMemoryAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                ;
    }

    public static File getDiskFileDir(Context context) {
        File file = null;
        if (externalMemoryAvailable()) {
            file = context.getExternalFilesDir(null);
        }
        if (file == null) {
            file = context.getFilesDir();
        }
        return file;
    }

    /**
     * 获取SDCARD剩余存储空间
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    public static String openStringFile(String path) throws IOException {
        return openStringFile(new File(path));
    }

    public static String openStringFile(File file) throws IOException {
        FileInputStream iStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(iStream, "utf-8");
        StringBuilder sb = new StringBuilder("");
        char[] buf = new char[1024];
        int amt = reader.read(buf);
        while (amt > 0) {
            sb.append(buf, 0, amt);
            amt = reader.read(buf);
        }
        reader.close();
        return sb.toString();
    }

    public static void saveStringFile(String path, String data) throws IOException {
        saveStringFile(new File(path), data);
    }

    public static void saveStringFile(File file, String data) throws IOException {
        file.getParentFile().mkdirs();
        FileOutputStream oStream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(oStream, "utf-8");
        writer.write(data);
        writer.close();
    }

    public static void saveFile(String path, InputStream is) throws IOException {
        saveFile(new File(path), is);
    }

    public static void saveFile(File file, InputStream is) throws IOException {
        file.getParentFile().mkdirs();
        FileOutputStream oStream = new FileOutputStream(file);
        byte[] buff = new byte[1024];
        int len;
        while ((len = is.read(buff)) != -1) {
            oStream.write(buff, 0, len);
        }
        oStream.close();
        is.close();
    }


    public static abstract class OnSaveFileListener {
        //IO线程
        public void onAfterSave(String file, Bitmap bitmap) {
        }

        public abstract void onStart();

        public abstract void onSuccess(String fileName);

        public abstract void onFailed(Throwable throwable);
    }
}
