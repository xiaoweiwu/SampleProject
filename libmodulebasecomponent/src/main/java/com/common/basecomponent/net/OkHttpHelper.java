package com.common.basecomponent.net;

import android.text.TextUtils;

import com.common.basecomponent.constant.BaseConstant;
import com.common.basecomponent.util.L;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpHelper {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static Interceptor LOG_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long startTime = System.currentTimeMillis();
            Response res = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            L.d(String.format("请求时间: %d ms", (endTime - startTime)));
            return res;
        }
    };
    private static String USER_AGENT = null;
    private static OkHttpClient sClient;

    public static void setUserAgent(String userAgent) {
        USER_AGENT = userAgent;
    }

    public static void setOkHttpClient(OkHttpClient client) {
        sClient = client;
    }

    public static OkHttpClient getClient() {
        if (sClient == null) {
            synchronized (OkHttpHelper.class) {
                if (sClient == null) {
                    sClient = createClient();
                }
            }
        }
        return sClient;
    }

    private static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(BaseConstant.HttpConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(BaseConstant.HttpConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(BaseConstant.HttpConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(LOG_INTERCEPTOR)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    public static Request.Builder getRequestBuilder(String url, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (String name : headers.keySet()) {
                builder.addHeader(name, headers.get(name));
            }
        }
        if (USER_AGENT != null && !TextUtils.isEmpty(USER_AGENT)) {
            builder.addHeader("User-Agent", USER_AGENT);
        }
        return builder;
    }

    private static String executeRequestString(Request request) throws Exception {
        Response response = executeRequest(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static Response executeRequest(Request request) throws IOException {
        try {
            return getClient().newCall(request).execute();
        } catch (IOException e) {
            throw new IOException(request.toString(), e);
        }
    }

    public static Response executeRequestTimeOut(Request request, long timeout) throws IOException {
        OkHttpClient client = getClient().newBuilder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new IOException(request.toString(), e);
        }
    }

    public static String requestGetString(String url, Map<String, String> headers) throws Exception {
        Request.Builder builder = getRequestBuilder(url, headers);
        Request request = builder.build();
        return executeRequestString(request);
    }

    public static String requestPost(String url, Map<String, String> headers, String jsonData) throws Exception {
        Request.Builder builder = getRequestBuilder(url, headers);
        Request request;
        if (jsonData != null) {
            request = builder.post(RequestBody.create(JSON, jsonData)).build();
        } else {
            request = builder.post(RequestBody.create(null, new byte[0])).build();
        }
        return executeRequestString(request);
    }

    public static String requestDelete(String url, Map<String, String> headers) throws Exception {
        Request.Builder builder = getRequestBuilder(url, headers).delete();
        return executeRequestString(builder.build());
    }

    public static String requestPut(String url, Map<String, String> headers) throws Exception {
        Request.Builder builder = getRequestBuilder(url, headers).put(RequestBody.create(null, new byte[0]));
        return executeRequestString(builder.build());
    }

    public static ResponseBody download(String url) {
        Map map = new HashMap();
        map.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        try {
            return getResponseBodyFromURL(url, map);
        } catch (IOException e) {
            return null;
        }
    }

    public static ResponseBody getResponseBodyFromURL(String url, Map headers) throws IOException {
        Request.Builder builder = getRequestBuilder(url, headers);
        Request request = builder.build();
        Response response = executeRequest(request);
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    public static ResponseBody getInputStreamFromURL(String url) throws IOException {
        ResponseBody body = getResponseBodyFromURL(url, null);
        return body;
    }

    public static void cancelRequest(String tag) {
        if (sClient == null)
            return;

        for (Call call : sClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : sClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
