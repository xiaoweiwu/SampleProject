package com.fullshare.basebusiness.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.common.basecomponent.eventbus.BusProvider;
import com.common.basecomponent.eventbus.EventModel;
import com.common.basecomponent.exception.AppHttpException;
import com.common.basecomponent.exception.ErrorType;
import com.common.basecomponent.net.BaseResponse;
import com.common.basecomponent.net.OkHttpHelper;
import com.common.basecomponent.util.L;
import com.fullshare.basebusiness.api.RespCode;
import com.fullshare.basebusiness.constants.Argumentkey;
import com.fullshare.basebusiness.constants.BusinessEvent;
import com.fullshare.basebusiness.util.GsonHelper;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

import okhttp3.Response;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaowei on 2016/12/26.
 */

public class HttpService {

    public static <T> void request(final Context context, final CommonHttpRequest request, final OnResponseCallback<T> callback) {
        request(context, null, request, callback);
    }

    public static <T> void request(final Context context, LifecycleProvider lifecycleProvider, final CommonHttpRequest request, final OnResponseCallback<T> callback) {
        WeakReference<Activity> weakReference = null;
        if (context instanceof Activity) {
            weakReference = new WeakReference<Activity>((Activity) context);
        }
        Observable<T> tObservable = Observable.create(new OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                CommonHttpResponse<T> response = getRemoteData(context, request, callback != null ? callback.getType() : null);
                if (response.isBusinessSuccessful()) {
                    subscriber.onNext(response.getData());
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(response.getException());
                }

            }
        });
        if (lifecycleProvider == null) {
            if (context instanceof LifecycleProvider) {
                tObservable = tObservable.compose(RxLifecycle.<T, ActivityEvent>bindUntilEvent(((LifecycleProvider<ActivityEvent>) context).lifecycle(), ActivityEvent.DESTROY));
            }
        } else {
            tObservable = tObservable.compose(RxLifecycle.<T, FragmentEvent>bindUntilEvent(lifecycleProvider.lifecycle(), FragmentEvent.DESTROY_VIEW));
        }
        final WeakReference<Activity> finalWeakReference = weakReference;
        tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onStart() {
                        if (callback != null) {
                            callback.onStart();
                        }
                    }

                    @Override
                    public void onNext(T model) {
                        if (callback != null) {
                            callback.onSuccess(model);
                            callback.onFinish(true, new ResponseStatus(0, ErrorType.SUCCESS, ""));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onHandleError(context, request, e, callback, finalWeakReference);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    public static CommonHttpResponse<?> requestSync(final Context context, final CommonHttpRequest request, Type type) {
        return getRemoteData(context, request, type);
    }

    private static BaseResponse http(CommonHttpRequest request) throws IOException {
        Response response = OkHttpHelper.executeRequest(HttpUtil.buildHttpRequest(request));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccessFul(response.isSuccessful());
        baseResponse.setStatusCode(response.code());
        baseResponse.setResponse(response.body() == null ? null : response.body().string());
        return baseResponse;
    }

    private static <T> CommonHttpResponse<T> getRemoteData(Context context, CommonHttpRequest request, Type type) {
        CommonHttpResponse<T> realResponse = new CommonHttpResponse<>();
        try {
            BaseResponse baseResponse = http(request);
            realResponse.setStatusCode(baseResponse.getStatusCode());
            L.d(request.url() + "--" + "--" + GsonHelper.getGson().toJson(request.body()));
            if (baseResponse.isSuccessFul()) {
                L.json(baseResponse.getResponse());
            } else {
                L.e(baseResponse.getResponse());
            }
            //网络请求是否成功，状态码code >= 200 && code < 300
            if (baseResponse.isSuccessFul()) {
                if (type == String.class) {
                    realResponse.setHttpSuccessful(true);
                    JSONObject jsonObject = new JSONObject(baseResponse.getResponse());
                    realResponse.setCode(jsonObject.getInt("code"));
                    if (jsonObject.get("data") != null) {
                        realResponse.setData((T) jsonObject.get("data").toString());
                    }
                    realResponse.setMsg(jsonObject.getString("msg"));
                } else {
                    if (type == null) {
                        type = Object.class;
                    }
                    ParameterizeTypeImpl realType = ParameterizeTypeImpl.get(CommonHttpResponse.class, type);
                    CommonHttpResponse<T> responseModel = GsonHelper.getGson().fromJson(baseResponse.getResponse(), realType);
                    realResponse.setHttpSuccessful(true);
                    realResponse.setCode(responseModel.getCode());
                    realResponse.setData(responseModel.getData());
                    realResponse.setMsg(responseModel.getMsg());
                }

            } else {
                realResponse.setHttpSuccessful(false);
                if (realResponse.getStatusCode() >= 400 && realResponse.getStatusCode() < 500) {
                    realResponse.setException(new AppHttpException(ErrorType.REQUEST_ERROR, baseResponse.getStatusCode(),
                            baseResponse.getResponse()));
                } else if (realResponse.getStatusCode() >= 500) {
                    realResponse.setException(new AppHttpException(ErrorType.SERVER_ERROR, baseResponse.getStatusCode(),
                            baseResponse.getResponse()));
                }
            }

            if (realResponse.isHttpSuccessful()) {
                //接口异常返回
                if (realResponse.getCode() != RespCode.BUSINESS_RESPONSE_OK) {
                    if (realResponse.getCode() == 500) {
                        realResponse.setException(new AppHttpException(ErrorType.SERVER_ERROR, realResponse.getCode(),
                                realResponse.getMsg()));
                    } else {
                        realResponse.setException(new AppHttpException(ErrorType.BUSINESS_ERROR, realResponse.getCode(),
                                realResponse.getMsg()));
                    }
                    realResponse.setBusinessSuccessful(false);

                } else {
                    realResponse.setBusinessSuccessful(true);

                }
            }

        } catch (IOException e) {
            realResponse.setHttpSuccessful(false);
            realResponse.setException(new AppHttpException(ErrorType.NETWORK_ERROR, e));
        } catch (Exception e) {
            realResponse.setBusinessSuccessful(false);
            realResponse.setException(new AppHttpException(ErrorType.UNKNOW_ERROR, e));
        }
        return realResponse;
    }

    private static void onHandleError(Context context, CommonHttpRequest request, Throwable e, OnResponseCallback callback, WeakReference<Activity> finalWeakReference) {
        if (e == null) {
            if (callback != null) {
                ResponseStatus responseStatus = new ResponseStatus(-1, ErrorType.UNKNOW_ERROR
                        , "unknow_error");
                callback.onFinish(false, responseStatus);
            }
            return;
        }
        L.e(request.businessMethod() + request.body().toString());
        try {
            L.e(e.toString(), "");
        } catch (Exception e1) {
            MobclickAgent.reportError(context, e);
        }

        if (e instanceof AppHttpException) {
            if (((AppHttpException) e).getErrorType() == ErrorType.UNKNOW_ERROR) {
                MobclickAgent.reportError(context, e);
            }
            AppHttpException ex = (AppHttpException) e;
            ResponseStatus responseStatus = new ResponseStatus(ex.getExCode(), ex.getErrorType()
                    , ex.getMessage());
            if (((AppHttpException) e).getErrorType() == ErrorType.NETWORK_ERROR) {
                responseStatus.setMessage("网络不给力");
            }
            if (callback != null) {
                callback.onFinish(false, responseStatus);
            }

            ErrorType error = ex.getErrorType();
            if (error == ErrorType.BUSINESS_ERROR) {
                if (responseStatus.getCode() == RespCode.TOKEN_CONFLICT ||
                        responseStatus.getCode() == RespCode.TOKEN_IS_EMPTY ||
                        responseStatus.getCode() == RespCode.TOKEN_ILLEGAL || responseStatus.getCode() == RespCode.USER_NOT_EXISTS) {
                    //是否需要重新登录
//                    UserInfoUtil.clearUserInfo();
                    BusProvider.getInstance().post(EventModel.newBuilder().eventId(BusinessEvent.EVENT_ON_LOGIN_OUT).build());
                    Intent intent = new Intent();
                    intent.setClassName(context, "com.fullshare.fsb.auth.LoginActivity");
                    intent.putExtra(Argumentkey.REQUEST_LOGIN, true);
                    context.startActivity(intent);
                } else if (responseStatus.getCode() == RespCode.TOKEN_OUT_DATE) {
                }
                if (callback != null) {
                    callback.onGlobalResponse(responseStatus);
                }
            }
        } else {
            throw new RuntimeException(e);
        }
    }
}