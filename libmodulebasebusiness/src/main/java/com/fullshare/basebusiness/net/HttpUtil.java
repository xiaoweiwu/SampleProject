package com.fullshare.basebusiness.net;

import com.common.basecomponent.BaseApplication;
import com.common.basecomponent.constant.BaseConstant.HttpParam;
import com.common.basecomponent.constant.BaseConstant.HttpParamKey;
import com.common.basecomponent.util.EncryptUtil;
import com.common.basecomponent.util.L;
import com.fullshare.basebusiness.api.ApiConstant;
import com.common.basecomponent.SystemInfo;
import com.fullshare.basebusiness.api.UserAuthInfo;
import com.fullshare.basebusiness.constants.AppConfig.BusinessMethod;
import com.fullshare.basebusiness.util.GsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by wuxiaowei on 2017/3/22.
 */

public class HttpUtil {

    public static Request buildHttpRequest(CommonHttpRequest baseRequest) {
        Request request = null;
        signRequest(baseRequest);
        addHeaders(baseRequest);
        if ("GET".equals(baseRequest.method())) {
            request = buildGetRequest(baseRequest);
        } else if ("POST".equals(baseRequest.method())) {
            request = buildPostRequest(baseRequest);
        }
        if (SystemInfo.getSystemInfo().isLogDebug()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("url", baseRequest.url());
                jsonObject.put("headers", new JSONObject(baseRequest.headers()));
                jsonObject.put("params", new JSONObject(baseRequest.body()));
                jsonObject.put("file", new JSONObject(baseRequest.files()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            L.json(jsonObject.toString());
        }
        return request;
    }

    private static void signRequest(CommonHttpRequest baseRequest) {
        String body = GsonHelper.getGson().toJson(baseRequest.body()).trim();
        long clientTime = System.currentTimeMillis();
        L.d(body);
        String requestSign = EncryptUtil.md5(body + ApiConstant.APP_ID + "fullshare" + clientTime * 10 + ApiConstant.APP_SECURITY_KEY);
        addSignHeaders(baseRequest, clientTime, requestSign);
    }

    private static void addSignHeaders(CommonHttpRequest baseRequest, long clientTime, String requestSign) {
        Map<String, String> headers = baseRequest.headers();
        headers.put("clienttime", clientTime + "");
        headers.put("appid", ApiConstant.APP_ID);
        headers.put("requestsign", requestSign);
    }

    public static void addHeaders(CommonHttpRequest baseRequest) {
        Map<String, String> headers = baseRequest.headers();
        headers.put(HttpParamKey.DEVICE_ID, SystemInfo.getSystemInfo().getDeviceId());
        headers.put(HttpParamKey.CONTENT_TYPE, HttpParam.CONTENT_TYPE);
        headers.put(HttpParamKey.ACCEPT, HttpParam.ACCEPT);
        headers.put(HttpParamKey.AUTHORIZATION, UserAuthInfo.getUserAuthInfo().getToken());
        headers.put(HttpParamKey.PLATFORM, HttpParam.PLATFORM);
        headers.put(HttpParamKey.OS, HttpParam.OS);
        headers.put(HttpParamKey.APP_VERSION, SystemInfo.getSystemInfo().getVersionName());
        headers.put(HttpParamKey.MODEL, HttpParam.MODEL);
        headers.put(HttpParamKey.CHANNEL, SystemInfo.getSystemInfo().getChannel());
        headers.put(HttpParamKey.BRAND, HttpParam.BRAND);
    }

    public static String getRequestDescription(String requestMethod) {
        return requestMethod + "----" + BusinessMethod.methodDescMap.get(requestMethod);
    }

    private static Request buildGetRequest(CommonHttpRequest request) {
        String url = request.url();
        url = (request.body() != null && request.body().size() > 0) ? (url + "?" + concatGetParams(request.body())) : url;
        return new Request.Builder()
                .url(url)
                .headers(Headers.of(request.headers()))
                .build();
    }

    private static Request buildPostRequest(CommonHttpRequest request) {
        RequestBody requestBody = null;
        if (request.files() != null && request.files().size() != 0) {
            //组装文件参数
            MultipartBody.Builder mutiBuilder = new MultipartBody.Builder();
            for (Entry<String, String> fileSet : request.files().entrySet()) {
                String fileName = fileSet.getValue();
                File file = new File(fileName);
                String fileType = getMimeType(fileName);
                mutiBuilder.setType(MultipartBody.FORM)
                        .addFormDataPart(fileSet.getKey(), file.getName(),
                                RequestBody.create(MediaType.parse(fileType), file));
            }
            //组装参数
            for (Entry<String, Object> params : request.body().entrySet()) {
                mutiBuilder.addFormDataPart(params.getKey(), (String) params.getValue());
            }
            requestBody = mutiBuilder.build();
        } else {
            //组装参数
            MediaType JSON = MediaType.parse("application/json;");
            String body = GsonHelper.getGson().toJson(request.body()).trim();
//            FormBody.Builder builder = new FormBody.Builder();
//            Iterator iter = request.body().entrySet().iterator();
//            while (iter.hasNext()) {
//                Entry entry = (Entry) iter.next();
//                builder.add((String) entry.getKey(), (String) entry.getValue());
//            }
            requestBody = RequestBody.create(JSON, body);
        }
        return new Request.Builder().tag(request.tag())
                .url(request.url())
                .headers(Headers.of(request.headers()))
                .post(requestBody)
                .build();
    }

    private static String concatGetParams(Map<String, Object> params) {
        if (params == null || params.size() == 0)
            return "";
        StringBuilder buffer = new StringBuilder();
        for (Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            if (key != null) {
                Object entryValue = entry.getValue();
                String value = "";
                if (entryValue instanceof String) {
                    value = (String) entryValue;
                } else if (entryValue != null) {
                    value = GsonHelper.getGson().toJson(entryValue);
                }
                buffer.append(key).append("=").append(URLEncoder.encode(value)).append("&");
            }
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }

    /**
     * 获取文件MimeType
     *
     * @param filename 文件名
     * @return
     */
    private static String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; //* exe,所有的可执行程序
        }
        return contentType;
    }
}
