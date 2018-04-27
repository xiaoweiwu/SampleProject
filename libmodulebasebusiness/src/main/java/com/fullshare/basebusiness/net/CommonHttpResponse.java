package com.fullshare.basebusiness.net;

import com.common.basecomponent.exception.AppHttpException;

/**
 * Created by wuxiaowei on 2017/1/14.
 */

public class CommonHttpResponse<T> {

    private int statusCode;
    private int code;
    private String msg;
    private T data;
    private AppHttpException exception;
    private boolean businessSuccessful;
    private boolean httpSuccessful;

    public boolean dataNull;

    public boolean isDataNull() {
        return dataNull;
    }

    public void setDataNull(boolean dataNull) {
        this.dataNull = dataNull;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AppHttpException getException() {
        return exception;
    }

    public void setException(AppHttpException exception) {
        this.exception = exception;
    }

    public boolean isBusinessSuccessful() {
        return businessSuccessful;
    }

    public void setBusinessSuccessful(boolean businessSuccessful) {
        this.businessSuccessful = businessSuccessful;
    }

    public boolean isHttpSuccessful() {
        return httpSuccessful;
    }

    public void setHttpSuccessful(boolean httpSuccessful) {
        this.httpSuccessful = httpSuccessful;
    }
}
