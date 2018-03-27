package com.fullshare.basebusiness.downloader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.common.basecomponent.net.OkHttpHelper;
import com.common.basecomponent.util.ActivityUtil;
import com.fullshare.basebusiness.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;

public class UpdateDownloadService extends Service {

    public static final String TAG = UpdateDownloadService.class.getSimpleName();
    public static final int STATUS_PROGRESS = 1;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_COMPLETED = 3;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    /**
     * 用于生成通知对象
     */
    private Map<String, Notification> notifications = new HashMap<>();
    private Map<String, DownloadInfo> downloadInfos = new HashMap<>();
    private Map<String, WeakReference<RemoteViews>> remoteViewsMap = new HashMap<>();
    /**
     * 用于发送与取消通知
     */
    private NotificationManager mManager;
    private Context mContext;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DownloadInfo downloadInfo = (DownloadInfo) msg.obj;
            switch (msg.what) {
                case STATUS_PROGRESS:
                    if (downloadInfo != null && downloadInfo.isShowNotification()) {
                        Notification notification = notifications.get(downloadInfo.getUrl());
                        remoteViewsMap.get(downloadInfo.getUrl()).get().setProgressBar(R.id.update_notification_progressbar, 100, downloadInfo.getProgress(), false);
                        remoteViewsMap.get(downloadInfo.getUrl()).get().setTextViewText(R.id.update_notification_progresstext, downloadInfo.getProgress() + "%");
                        mManager.notify(downloadInfo.getNotifyId(), notification);
                    }
                    break;
                case STATUS_ERROR:
                    if (downloadInfo != null && downloadInfo.isShowNotification()) {
                        remoteViewsMap.get(downloadInfo.getUrl()).get().setTextViewText(R.id.update_notification_progresstext, "下载失败");
                        mManager.notify(downloadInfo.getNotifyId(), notifications.get(downloadInfo.getUrl()));
                        new File(downloadInfo.getFullFileName()).deleteOnExit();
                        downloadInfos.remove(downloadInfo.getUrl());
                    }
                    if (downloadInfos.size() == 0) {
                        if (mManager != null) {
                            mManager.cancelAll();
                        }
                        stopSelf();
                    }
                    break;
                case STATUS_COMPLETED:
                    downloadInfos.remove(downloadInfo.getUrl());
                    if (downloadInfo != null && downloadInfo.isShowNotification()) {
                        mManager.cancel(downloadInfo.getNotifyId());
                    }
                    if (downloadInfo.getAction() == DownloadManager.INSTALL_APK) {
                        ActivityUtil.installApk(mContext, downloadInfo.getFullFileName());
                    } else {
                        broadcast(downloadInfo.getTaskId(), msg.what);
                    }
                    if (downloadInfos.size() == 0) {
                        stopSelf();
                    }

                    break;
            }
        }
    };
    private int notificationId = Integer.MAX_VALUE - 10000;

    public void broadcast(String taskId, int status) {
        Intent intent = new Intent(DownloadManager.ACTION);
        intent.putExtra("taskId", taskId);
        intent.putExtra("status", status);
        sendBroadcast(intent);
    }

    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            DownloadInfo downloadInfo = intent.getParcelableExtra("download_info");
            if (downloadInfo != null && !downloadInfos.containsKey(downloadInfo.getUrl())) {
                if (downloadInfo.isShowNotification()) {
                    downloadInfo.setNotifyId(initNotification(downloadInfo));
                }
                downloadInfos.put(downloadInfo.getUrl(), downloadInfo);
                executorService.submit(new DownloadTask(downloadInfo));
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private int initNotification(DownloadInfo downloadInfo) {
        int notifyId = notificationId++;
        // 获得通知管理器
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 实例化Notification对象
        RemoteViews contentVeiw = new RemoteViews(getPackageName(),
                R.layout.layout_download_notification);
        remoteViewsMap.put(downloadInfo.getUrl(), new WeakReference<RemoteViews>(contentVeiw));
        Notification mNotification = new NotificationCompat.Builder(mContext)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_app_downloading))
                .setSmallIcon(R.drawable.ic_app_downloading)
                .setTicker("开始下载" + downloadInfo.getShowName())
                .setWhen(new Date().getTime())
                .setContentIntent(PendingIntent.getActivity(mContext, downloadInfo.getNotifyId()
                        , new Intent(),
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setCustomContentView(contentVeiw)
                .build();
//ActivityUtil.getAPkInstallIntent(mContext,downloadInfo.getFullFileName())
        // 设置进度条
        contentVeiw.setProgressBar(
                R.id.update_notification_progressbar, 100, 0, false);
        // 设置下载名
        contentVeiw.setTextViewText(R.id.download_name,
                downloadInfo.getShowName());
        // 设置图标
        contentVeiw.setImageViewResource(R.id.download_icon,
                R.drawable.ic_app_downloading);
        // 设置更新文字
        contentVeiw.setTextViewText(
                R.id.update_notification_progresstext, "0%");
        // 设置不允许清除
        mNotification.flags |= Notification.FLAG_NO_CLEAR;
        // 发送通知
        mManager.notify(notifyId,
                mNotification);
        notifications.put(downloadInfo.getUrl(), mNotification);
        return notifyId;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            executorService.shutdown();
        } catch (Exception e) {
        }
        handler.removeCallbacksAndMessages(null);
        if (mManager != null) {
            mManager.cancelAll();
        }
        downloadInfos.clear();

    }

    class DownloadTask implements Runnable {
        DownloadInfo downloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            download(downloadInfo);
        }

        private void download(DownloadInfo downloadInfo) {
            InputStream in = null;
            FileOutputStream out = null;
            try {
                ResponseBody responseBody = OkHttpHelper.download(downloadInfo.getUrl());
                if (responseBody == null) {
                    Message.obtain(handler, STATUS_ERROR, downloadInfo).sendToTarget();
                    return;
                }
                in = responseBody.byteStream();
                String targetName = downloadInfo.getFullFileName();
                File tmpFile = new File(targetName + ".tmp");
                out = new FileOutputStream(tmpFile);
                byte[] buf = new byte[1024 * 8];
                long total = responseBody.contentLength();
                int sum = 0;
                int len = -1;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                    sum += len;
                    int progress = (int) (sum * 100f / total);
                    if (progress != downloadInfo.getProgress() && progress % 2 == 0) {
                        downloadInfo.setProgress(progress);
                        Message.obtain(handler, STATUS_PROGRESS, downloadInfo).sendToTarget();
                    }
                }
                out.flush();
                tmpFile.renameTo(new File(targetName));
                Message.obtain(handler, STATUS_COMPLETED, downloadInfo).sendToTarget();
            } catch (IOException e) {
                Message.obtain(handler, STATUS_ERROR, downloadInfo).sendToTarget();
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
        }
    }
}
