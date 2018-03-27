package com.common.basecomponent.exception;


import com.common.basecomponent.fragment.refresh.LoadErrorType;

/**
 * Created by fs_wuxiaowei on 2016/9/8.
 */
public class AppHttpException extends Exception {

    private ErrorType type;

    private int exCode = -1;

    public AppHttpException(ErrorType type) {
        this.type = type;
    }

    public AppHttpException(ErrorType type, Exception e) {
        super(e);
        this.type = type;
    }

    public AppHttpException(ErrorType type, Exception e, String message) {
        super(message, e);
        this.type = type;
    }

    public AppHttpException(ErrorType type, int code, String message) {
        super(message);
        this.type = type;
        this.exCode = code;
    }

    public int getExCode() {
        return exCode;
    }

    public ErrorType getErrorType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(type.toString() + "--[ exCode = " + exCode + "]--");
        sb.append("\n").append(super.toString());
        if (getCause() != null && getCause().getCause() != null) {
            sb.append("\n").append(getCause().getCause().toString());
        }
        return sb.toString();
    }


    public LoadErrorType getLoadErrorType() {
        if (getErrorType() == ErrorType.NETWORK_ERROR) {
            return LoadErrorType.NETWORD_UNAVAILABLE;
        }
        return LoadErrorType.SERVER_ERROR;
    }
}
