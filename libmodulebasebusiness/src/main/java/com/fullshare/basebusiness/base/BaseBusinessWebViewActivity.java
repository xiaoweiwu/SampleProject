package com.fullshare.basebusiness.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.common.basecomponent.util.L;
import com.fullshare.basebusiness.R;

import common.service.StatisticsManager;

/**
 * Created by wuxiaowei on 2017/6/4.
 */

public abstract class BaseBusinessWebViewActivity extends CommonBaseActivity {
    public static final String LOAD_ID = "LOAD_ID";
    public static final String LOAD_UR = "LOAD_UR";
    public static final String SHOW_TITLE = "SHOW_TITLE";
    public static final String TITLE = "TITLE";

    public static final int FILE_CHOOSE_CODE = 10000;

    protected WebView mWebView;
    protected boolean isStartLoading;
    private View xCustomView;
    private FrameLayout video_fullView;
    private CustomViewCallback xCustomViewCallback;
    private CustomWebChromeClient customWebChromeClient;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri> filePathCallbackPrevious;
    private String mFailUrl;
    private boolean isLoadedUrl;

    @Override
    protected void initArguments() {
        super.initArguments();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    private void initView() {
        mWebView = getWebView();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isStartLoading = false;
                doPageFinished();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isStartLoading = true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, final WebResourceError error) {
                mFailUrl = mWebView.getUrl();
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, final int errorCode, final String description, final String failingUrl) {
                mFailUrl = failingUrl;

                onError();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mContext != null) {
                            Toast.makeText(mContext, errorCode + "-" + description + "-" + failingUrl, Toast.LENGTH_SHORT).show();
                            StatisticsManager.reportError(mContext, errorCode + "-" + description + "-" + failingUrl);
                        }
                    }
                });
            }
        });
        customWebChromeClient = new CustomWebChromeClient();
        mWebView.setWebChromeClient(customWebChromeClient);
        mWebView.getSettings().setUserAgentString("fstop");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.addJavascriptInterface(this, getJavascriptInterfaceName());
        getLoadingViewController().setLoadingActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        video_fullView = (FrameLayout) findViewById(R.id.id_full_screen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFilePathCallback != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mFilePathCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            }
        }
        if (filePathCallbackPrevious != null && (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                filePathCallbackPrevious.onReceiveValue(data.getData());
            } else {
                filePathCallbackPrevious.onReceiveValue(null);
            }
        }
    }

    protected abstract WebView getWebView();

    protected abstract void loadOpenUrl();

    protected final void onLoadedOpenUrl(String url) {
        if (url != null) {
            mFailUrl = null;
            startLoadUrl(url);
            isLoadedUrl = true;
        } else {
            getLoadingViewController().showNoData();
        }
    }

    @SuppressLint("JavascriptInterface")
    private void startLoadUrl(String url) {
        if (mWebView != null) {
            mWebView.loadUrl(url);

        }
        L.d(url);
    }

    protected String getJavascriptInterfaceName() {
        return "fullShare";
    }

    private void onError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mWebView != null) {
                    mWebView.setVisibility(View.INVISIBLE);
                }
                onPageError();
            }
        });
    }

    protected void onPageError() {
    }

    private void doPageFinished() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mFailUrl == null) {
                    mWebView.setVisibility(View.VISIBLE);
                }
                onPageFinished();
            }
        });
    }

    protected void onPageFinished() {
    }

    public boolean isLoadedSuccess() {
        return mFailUrl == null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null)
            mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            if (video_fullView != null) {
                video_fullView.removeAllViews();
            }
            mWebView.stopLoading();
            mWebView.removeAllViews();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }
    }

    public void reload() {
        //showLoading();
        if (isLoadedUrl) {
            mFailUrl = null;
            mWebView.reload();
        } else {
            loadOpenUrl();
        }
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        customWebChromeClient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onBackPressed() {
        if (inCustomView()) {
            hideCustomView();
        } else if (isWaitForBack()) {
            onWaitForBack();
        } else if (isLoadedUrl && mFailUrl == null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isWaitForBack() {
        return false;
    }

    protected void onWaitForBack() {
        super.onBackPressed();
    }

    public class CustomWebChromeClient extends WebChromeClient {
        private View xprogressvideo;
        private ValueCallback<Uri> mUploadMessage;

        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mWebView.setVisibility(View.INVISIBLE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            video_fullView.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            video_fullView.setVisibility(View.VISIBLE);
        }

        // 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
            if (xCustomView == null)// 不是全屏播放状态
                return;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            xCustomView.setVisibility(View.GONE);
            video_fullView.removeView(xCustomView);
            xCustomView = null;
            video_fullView.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            mWebView.setVisibility(View.VISIBLE);
        }

        // 视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater
                        .from(mContext);
                xprogressvideo = inflater.inflate(
                        R.layout.layout_h5_video_loading, null);
            }
            return xprogressvideo;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress > 30 && isStartLoading) {
                isStartLoading = false;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onShowFileChooser(WebView webView, final ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            String acceptType;
            if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null) {
                acceptType = fileChooserParams.getAcceptTypes()[0];
            } else {
                acceptType = "*/*";
            }
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(acceptType);
            mFilePathCallback = filePathCallback;
            startActivityForResult(intent, FILE_CHOOSE_CODE);
            overridePendingTransition(0, 0);
            return true;
        }

        public void openFileChooser(final ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(acceptType);
            filePathCallbackPrevious = filePathCallback;
            overridePendingTransition(0, 0);
        }
    }
}
