package com.fullshare.basebusiness.api;

import android.content.Context;

import com.fullshare.basebusiness.entity.TrainingActionModel;
import com.fullshare.basebusiness.net.CommonHttpRequest;
import com.fullshare.basebusiness.net.HttpService;
import com.fullshare.basebusiness.net.OnResponseCallback;

/**
 * Created by wuxiaowei on 2018/4/24.
 */

public class ActionApi {

    public static void getAllTrainType(Context context, boolean countAction, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.ACTION_API)
                .businessMethod("getAllTrainType")
                .addbody("countAction",countAction)
                .build(), callback);
    }

    public static void editAction(Context context, TrainingActionModel actionModel, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.ACTION_API)
                .businessMethod("editAction")
                .addbody("actionCalorie",actionModel.getActionCalorie())
                .addbody("actionCalorieLevel",actionModel.getActionCalorieLevel())
                .addbody("actionId",actionModel.getActionId())
                .addbody("actionName",actionModel.getActionName())
                .addbody("actionPartId",actionModel.getActionPartId())
                .addbody("actionTrainType",actionModel.getActionTrainType())
                .addbody("actionType",actionModel.getActionType())
                .addbody("actionUnit",actionModel.getActionUnit())
                .build(), callback);
    }


    public static String createFullPath(String name){
        return ApiConstant.ACTION_API +"/"+ ApiConstant.ApiVersion.VERSION_1_0_0 +"/"+name;
    }
}
