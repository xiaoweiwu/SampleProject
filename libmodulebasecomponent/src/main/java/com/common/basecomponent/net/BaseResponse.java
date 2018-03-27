package com.common.basecomponent.net;

/**
 * Created by wuxiaowei on 2017/3/21.
 */

public class BaseResponse {

    private boolean successFul;
    private int statusCode;
    private String response;

    public boolean isSuccessFul() {
        return successFul;
    }

    public void setSuccessFul(boolean successFul) {
        this.successFul = successFul;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
