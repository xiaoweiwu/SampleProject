package com.fullshare.zxing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.fullshare.zxing.camera.CameraManager;
import com.fullshare.zxing.decoding.CaptureActivityHandler;
import com.fullshare.zxing.decoding.DecodeCallback;
import com.fullshare.zxing.decoding.InactivityTimer;
import com.fullshare.zxing.scan.ScanResultBase;
import com.fullshare.zxing.view.ScannerView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Vector;

/**
 * Initial the camera
 */
public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback,
        OnRequestPermissionsResultCallback, DecodeCallback {
    public static final String SCAN_TYPE = "scan_type";

    public static final int SCAN_ISBN = 0;
    public static final int SCAN_QR_CODE = 1;
    public static final int SCAN_ALL = 2;

    private static final int PERMISSIONS_REQUEST_CAMERA = 42;
    protected ScannerView mScannerView;
    protected SurfaceView mSurfaceView;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private ScanResultBase mResultHandle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setContentView(R.layout.activity_capture);
        CameraManager.init(getApplication());

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initView();
    }

    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initSurface();
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

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        if (mResultHandle != null)
            mResultHandle.destroy();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     */
    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        stopScan();
        inactivityTimer.onActivity();

        if (mResultHandle == null)
            mResultHandle = new ScanResultBase(this, this);
        mResultHandle.doResult(result);
        //// TODO: 2017/9/20
    }

    public void restartScan() {
        CameraManager.get().startPreview();
        handler.sendEmptyMessage(R.id.restart_preview);
    }

    public void stopScan() {
        CameraManager.get().stopPreview();
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
            handler = new CaptureActivityHandler(this, this, decodeFormats, characterSet);
        }
        mSurfaceView.post(new Runnable() {
            @Override
            public void run() {
                mSurfaceView.setBackgroundResource(0);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    initSurface();
                } else {
                    showRequestPermissionDialog(this, Manifest.permission.RECORD_AUDIO, new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    });
                }
            }
        }
    }

    private void showRequestPermissionDialog(CaptureActivity captureActivity, String recordAudio, Runnable runnable, Runnable runnable1) {
        //// TODO: 2017/9/20
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

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
}