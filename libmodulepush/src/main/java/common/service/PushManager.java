package common.service;

import android.content.Context;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by wuxiaowei on 2018/3/23.
 */

public class PushManager {
    public static void init(Context context, String channel, boolean debug, final IUmengRegisterCallback registerCallback) {
        PushAgent mPushAgent = PushAgent.getInstance(context);
//        UmengMessageHandler notificationClickHandler = new UmengMessageHandler () {
//            @Override
//            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//                new Handler(getMainLooper()).post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
////                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        };
//        mPushAgent.setMessageHandler(notificationClickHandler);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                if (registerCallback != null) {
                    registerCallback.onSuccess(deviceToken);
                }
            }

            @Override
            public void onFailure(String s, String s1) {
                if (registerCallback != null) {
                    registerCallback.onFailure(s, s1);
                }
            }
        });
        mPushAgent.setMessageChannel(channel);
        mPushAgent.setDebugMode(debug);
    }
}
