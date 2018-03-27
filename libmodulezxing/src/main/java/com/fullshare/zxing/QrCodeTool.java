package com.fullshare.zxing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.fullshare.zxing.camera.CameraManager;
import com.fullshare.zxing.camera.PlanarYUVLuminanceSource;
import com.fullshare.zxing.decoding.CaptureActivityHandler;
import com.fullshare.zxing.decoding.DecodeCallback;
import com.fullshare.zxing.decoding.InactivityTimer;
import com.fullshare.zxing.scan.ScanResultBase;
import com.fullshare.zxing.view.ScannerView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.Vector;

/**
 * Created by wuxiaowei on 2017/10/27.
 */

public class QrCodeTool implements SurfaceHolder.Callback,
        ActivityCompat.OnRequestPermissionsResultCallback, DecodeCallback {

    public static final int PERMISSIONS_REQUEST_CAMERA = 1000;
    public static final int SELECT_IMAGE_REQUEST_CODE = 10001;
    protected ScannerView mScannerView;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;

    private ScanResultBase mResultHandle;
    private SurfaceView mSurfaceView;

    private Activity activity;
    private OnScanResultListener onScanResultListener;

    public QrCodeTool(Activity activity) {
        this.activity = activity;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, QrCodeTool.PERMISSIONS_REQUEST_CAMERA);
        }
    }

    public void init(SurfaceView surfaceView, ScannerView scannerView) {
        this.mSurfaceView = surfaceView;
        this.mScannerView = scannerView;
        CameraManager.init(activity);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(activity);
    }

    private void initSurface() {
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
        }
        Vector<BarcodeFormat> barcodeFormats = new Vector<>(1);
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        decodeFormats = barcodeFormats;
        characterSet = null;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            Rect rect = new Rect();
            mScannerView.getWindowVisibleDisplayFrame(rect);
            CameraManager.get().openDriver(surfaceHolder, rect.width(), rect.height());
        } catch (Exception e) {
            System.out.println("initCamera = " + e);
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(activity, this, decodeFormats, characterSet);
        }
        mSurfaceView.post(new Runnable() {
            @Override
            public void run() {
                mSurfaceView.setBackgroundResource(0);
            }
        });

    }


    public void stopScan() {
        CameraManager.get().stopPreview();
    }


    public void restartScan() {
        CameraManager.get().startPreview();
        handler.sendEmptyMessage(com.fullshare.zxing.R.id.restart_preview);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initSurface();
                } else {
                    activity.setResult(Activity.RESULT_CANCELED);
                    activity.finish();
                }
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            mSurfaceView.post(new Runnable() {
                @Override
                public void run() {
                    initCamera(mSurfaceView.getHolder());
                }
            });
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void setOnScanResultListener(OnScanResultListener onScanResultListener) {
        this.onScanResultListener = onScanResultListener;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public ScannerView getScannerView() {
        return mScannerView;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void drawViewfinder() {
        mScannerView.drawScanner();
    }

    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        stopScan();
        inactivityTimer.onActivity();

        if (mResultHandle == null)
            mResultHandle = new ScanResultBase(activity, this);
        mResultHandle.doResult(result);
        if (onScanResultListener != null) {
            onScanResultListener.onScanResult(result, barcode);
        }


    }

    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == QrCodeTool.SELECT_IMAGE_REQUEST_CODE) {
                scanImage(data.getData());
            }
        }
    }

    public void onResume() {
        initSurface();
    }

    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    public void onDestroy() {
        stopScan();
        inactivityTimer.shutdown();
        if (mResultHandle != null)
            mResultHandle.destroy();
    }

    public void scanImage(final Uri uri) {
        try {
            new AsyncTask<Uri, Integer, Result>() {
                @Override
                protected Result doInBackground(Uri[] params) {
                    Bitmap scanBitmap = null;
                    try {
                        scanBitmap = QRCodeUtil.getSzieBitmapFromUri(activity, params[0], 400);
                        LuminanceSource source1 = new PlanarYUVLuminanceSource(
                                rgb2YUV(scanBitmap), scanBitmap.getWidth(),
                                scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(),
                                scanBitmap.getHeight());
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                                source1));
                        MultiFormatReader reader1 = new MultiFormatReader();
                        return reader1.decode(binaryBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Result result) {
                    super.onPostExecute(result);
                    if (onScanResultListener != null) {
                        onScanResultListener.onScanResult(result, null);
                    }
                }
            }.execute(uri);

        } catch (Exception e) {
            e.printStackTrace();
            if (onScanResultListener != null) {
                onScanResultListener.onScanResult(null, null);
            }
        }
    }

    public byte[] rgb2YUV(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int len = width * height;
        byte[] yuv = new byte[len * 3 / 2];
        int y, u, v;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = pixels[i * width + j] & 0x00FFFFFF;

                int r = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb >> 16) & 0xFF;

                y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
                u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
                v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

                y = y < 16 ? 16 : (y > 255 ? 255 : y);
                u = u < 0 ? 0 : (u > 255 ? 255 : u);
                v = v < 0 ? 0 : (v > 255 ? 255 : v);

                yuv[i * width + j] = (byte) y;
//                yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
//                yuv[len + (i >> 1) * width + (j & ~1) + 1] = (byte) v;
            }
        }
        return yuv;
    }


    public interface OnScanResultListener {
        void onScanResult(Result result, Bitmap barcode);
    }
}
