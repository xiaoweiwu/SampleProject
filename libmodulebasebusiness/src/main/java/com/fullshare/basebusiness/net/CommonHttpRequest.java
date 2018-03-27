package com.fullshare.basebusiness.net;

import android.text.TextUtils;

import com.fullshare.basebusiness.api.ApiConstant.ApiVersion;
import com.fullshare.basebusiness.util.GsonHelper;

import java.util.HashMap;
import java.util.Map;


/**
 * User: wuxiaowei
 * Date: 2016-08-30
 * Time: 17:14
 */
public final class CommonHttpRequest {
    private final String url;
    private final String method;
    private final String businessMethod;
    private final Map<String, String> headers;
    private final Map<String, Object> body;
    private final String tag;
    private final String version;
    private final Map<String, Object> extras;
    private String fullUrl;
    private Map<String, String> files;

    private CommonHttpRequest(Builder builder) {
//		this.url = builder.url + builder.version + "/" + builder.businessMethod;
        this.method = builder.method;
        this.businessMethod = builder.businessMethod;
        this.headers = builder.headers;
        this.body = builder.body;
        this.tag = builder.tag;
        this.extras = builder.extras;
        this.version = builder.version;
        this.fullUrl = builder.fullUrl;
        this.files = builder.files;

        if (TextUtils.isEmpty(fullUrl)) {
            if (version != null && version.length() > 0) {
                this.url = builder.url + "/" + version + "/" + businessMethod;
            } else {
                this.url = builder.url + "/" + businessMethod;
            }
        } else {
            this.url = fullUrl;
        }
    }

    public String businessMethod() {
        return businessMethod;
    }

    public String url() {
        return url;
    }

    public String method() {
        return method;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Map<String, Object> body() {
        return body;
    }

    public Map<String, String> files() {
        return files;
    }

    public String data() {
        return GsonHelper.getGson().toJson(body);
    }

    public String tag() {
        return tag;
    }

    public String version() {
        return version;
    }


    public static class Builder {
        private final Map<String, Object> extras;
        private String url;//TODO
        private String fullUrl;
        private String method;
        private String businessMethod;
        private Map<String, String> headers;
        private Map<String, Object> body;
        private Map<String, String> files;
        private String version = ApiVersion.VERSION_1_0_0;
        private String tag;

        public Builder() {
            this.method = "POST";
            this.headers = new HashMap<>();
            this.body = new HashMap<>();
            this.files = new HashMap<>();
            extras = new HashMap<>();
        }

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            this.url = url;
            return this;
        }

        public Builder fullUrl(String fullUrl) {
            if (fullUrl == null) throw new NullPointerException("url == null");
            this.fullUrl = fullUrl;
            return this;
        }

        /**
         * 版本信息
         *
         * @param version
         * @return
         */
        public Builder version(String version) {
            this.version = version;
            return this;
        }

        /**
         * 添加参数
         *
         * @param name
         * @param value
         * @return
         */
        public Builder addbody(String name, Object value) {
            body.put(name, value);
            return this;
        }

        //添加需要上传的文件
        public Builder addFile(String name, String value) {
            files.put(name, value);
            return this;
        }

        public Builder addBodyMap(Map<String, Object> bodyMap) {
            if (bodyMap != null)
                body.putAll(bodyMap);
            return this;
        }

        /**
         * 业务方法
         *
         * @param businessMehod
         * @return
         */
        public Builder businessMethod(String businessMehod) {
            this.businessMethod = businessMehod;
            return this;
        }

        public Builder header(String name, String value) {
            headers.put(name, value);
            return this;
        }

        public Builder extras(String name, Object value) {
            extras.put(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        public Builder get() {
            return method("GET", body);
        }

        public Builder post(Map<String, Object> body) {
            return method("POST", body);
        }

        private Builder method(String method, Map<String, Object> body) {
            if (method == null) throw new NullPointerException("method == null");
            if (method.length() == 0) throw new IllegalArgumentException("method.length() == 0");
            if (body == null && "POST".equals(method)) {
                throw new IllegalArgumentException("method " + method + " must have a request body.");
            }
            this.body = body;
            this.method = method;
            return this;
        }

        /**
         * Attaches {@code tag} to the request. It can be used later to cancel the request. If the tag
         * is unspecified or null, the request is canceled by using the request itself as the tag.
         */
        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public CommonHttpRequest build() {
            if (url == null && fullUrl == null) throw new IllegalStateException("url == null");
            return new CommonHttpRequest(this);
        }
    }
}

