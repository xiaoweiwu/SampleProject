package com.common.basecomponent.exception;

/**
 * Created by fs_wuxiaowei on 2016/9/9.
 */
public enum ErrorType {
    SUCCESS("正常", 0),
    NETWORK_ERROR("网络异常", 1000),
    REQUEST_ERROR("请求错误", 1100),
    SERVER_ERROR("服务端错误", 1200),
    BUSINESS_ERROR("接口业务异常", 1300),
    UNKNOW_ERROR("未知错误", 1400);
    int code;
    String desc;

    ErrorType(String desc, int type) {
        this.desc = desc;
        this.code = type;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "[" + code + "," + desc + "]";
    }
}
