package com.fullshare.zxing.decoding;

import android.graphics.Bitmap;
import android.os.Handler;

import com.fullshare.zxing.view.ScannerView;
import com.google.zxing.Result;

/**
 * Created by wuxiaowei on 2017/9/25.
 */

public interface DecodeCallback {

    ScannerView getScannerView();

    Handler getHandler();

    void drawViewfinder();

    void handleDecode(Result result, Bitmap barcode);
}
