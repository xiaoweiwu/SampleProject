package com.fullshare.basebusiness.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wuxiaowei on 2017/3/16.
 */

public abstract class OnResponseCallback<T> {
    private Type type;

    public OnResponseCallback() {
        type = getSuperclassTypeParameter();
    }

    public OnResponseCallback(Type type) {
        this.type = type;
        if (type == null) {
            this.type = getSuperclassTypeParameter();
        }
    }

    private Type getSuperclassTypeParameter() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterize = (ParameterizedType) superclass;
        return parameterize.getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

    /**
     * 请求开始回调
     */
    public void onStart() {
    }

    /**
     * 完成回调
     *
     * @param isResponseSuccess http请求不是200的返回false,接口请求不是正常数据的也是false
     */
    public void onFinish(boolean isResponseSuccess, ResponseStatus status) {
    }

    /**
     * 成功回调
     */
    public abstract void onSuccess(T result);


    public void onGlobalResponse(ResponseStatus responseStatus) {
    }
}
