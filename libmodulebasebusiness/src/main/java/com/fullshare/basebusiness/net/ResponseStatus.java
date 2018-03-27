package com.fullshare.basebusiness.net;

import com.common.basecomponent.exception.ErrorType;

/**
 * Created by wuxiaowei on 2017/3/17.
 */

public class ResponseStatus {

    private int code;

    private String message;

    private ErrorType errorType;

    public ResponseStatus(int code, ErrorType errorType, String message) {
        this.code = code;
        this.message = message;
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
