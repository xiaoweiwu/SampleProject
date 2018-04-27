package com.fullshare.basebusiness.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.common.basecomponent.fragment.refresh.LoadErrorType;
import com.fullshare.basebusiness.R;

/**
 * author: wuxiaowei
 * date : 2017/3/31
 */

public class CommonWebViewActivity extends BaseBusinessWebViewActivity {

    int loadId;
    private String title;
    private String url;
    View container;

    @Override
    protected void initArguments() {
        super.initArguments();
        getSettingOptions().setEventEnable(true);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mWebView = (WebView) findViewById(R.id.common_webview);
        super.init(savedInstanceState);
        loadId = getIntent().getIntExtra(LOAD_ID,-1);
        title = getIntent().getStringExtra(CommonWebViewActivity.TITLE);
        url = getIntent().getStringExtra(CommonWebViewActivity.LOAD_UR);
        getToolBarEx().setTitle(title).setTitleCenter(true);
        container = findViewById(R.id.container);
        loadOpenUrl();
        getLoadingViewController().setLoadingActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadOpenUrl();
            }
        });
    }

    @Override
    protected WebView getWebView() {
        return mWebView;
    }

    @Override
    protected void loadOpenUrl() {
        getLoadingViewController().showLoading();
        onLoadedOpenUrl(url);
    }

    @Override
    protected void onPageFinished() {
        if (isLoadedSuccess()) {
            getLoadingViewController().hideLoading();
            String webTitle = mWebView.getTitle();
            if (TextUtils.isEmpty(title)) {
                title = webTitle;
                getToolBarEx().setTitle(title);
            }
        } else {
            getLoadingViewController().setErrorType(LoadErrorType.NETWORD_UNAVAILABLE);
            getLoadingViewController().showLoadingError();
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_common_webview2;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
