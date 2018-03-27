package com.fullshare.zxing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.fullshare.zxing.view.ScannerView;
import com.google.zxing.Result;

/**
 * Created by wuxiaowei on 2018/3/23.
 */

public class SampleActivity extends Activity {
    QrCodeTool qrCodeTool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        qrCodeTool = new QrCodeTool(this);
        setContentView(R.layout.activity_capture);

        qrCodeTool.init((SurfaceView) findViewById(R.id.preview_view), (ScannerView) findViewById(R.id.scanner_view));
        qrCodeTool.setOnScanResultListener(new QrCodeTool.OnScanResultListener() {
            @Override
            public void onScanResult(Result result, Bitmap barcode) {
                if (result == null) {
                } else {
                    handleScanResult(result.getText());
                }
                qrCodeTool.restartScan();
            }
        });
    }

    private void handleScanResult(String text) {
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == QrCodeTool.SELECT_IMAGE_REQUEST_CODE) {
//                data.setData(FileProvider.getUriForFile());
                qrCodeTool.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        qrCodeTool.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeTool.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qrCodeTool.onDestroy();
    }

}
