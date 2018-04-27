package com.fullshare.basebusiness.api;

import android.content.Context;

import com.fullshare.basebusiness.entity.MemberCardModel;
import com.fullshare.basebusiness.entity.MemberDetailResponseModel;
import com.fullshare.basebusiness.entity.MemberModel;
import com.fullshare.basebusiness.net.CommonHttpRequest;
import com.fullshare.basebusiness.net.HttpService;
import com.fullshare.basebusiness.net.OnResponseCallback;

/**
 * Created by wuxiaowei on 2018/4/24.
 */

public class MemberApi {

    public static void listMember(Context context, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.MEMBER_API)
                .businessMethod("listMember")
                .build(), callback);
    }

    public static void getMemberDetail(Context context,String memberId, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.MEMBER_API)
                .businessMethod("getMemberDetail")
                .addbody("memberId",String.valueOf(memberId))
                .build(), callback);
    }

    /**
     * memberCode (string, optional): 用户编号 ,
     memberId (integer, optional): 用户ID ,
     memberMobile (string, optional): 用户手机号 ,
     memberName (string, optional): 用户姓名 ,
     memberSex (integer, optional): 用户性别（0=性别未知，1=男，2=女）
     * @param context
     * @param member
     * @param callback
     */
    public static void updateMember(Context context, MemberModel member, OnResponseCallback<MemberDetailResponseModel> callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.MEMBER_API)
                .businessMethod("updateMember")
                .addbody("memberCode",member.getMemberCode())
                .addbody("memberId",member.getMemberId())
                .addbody("memberMobile",member.getMemberMobile())
                .addbody("memberName",member.getMemberName())
                .addbody("memberSex",member.getMemberSex())
                .build(), callback);
    }

    public static void getMemberBody(Context context,String memberId, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.MEMBER_API)
                .businessMethod("getMemberBody")
                .addbody("memberId",String.valueOf(memberId))
                .build(), callback);
    }

    /**
     * bodyMemberId (integer, optional): 会员id ,
     bodyPartType (integer, optional): 身体部位(1=体重;2=身高;3=体脂率;4=肌肉含量;5=基础代谢;6=臂围;7=腰围;8=臀围;9=胸围;10=大腿围;11=小腿围) ,
     bodyPartUnit (string, optional): 单位: 尺,公斤,百分比 ,
     bodyPartValue (string, optional): 记录值 ,
     * @param context
     * @param bodyMemberId
     * @param callback
     */
    public static void addMemberBodyDetail(Context context,String bodyMemberId,int bodyPartType, String bodyPartValue,
                                           String bodyPartUnit,
                                           OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.MEMBER_API)
                .businessMethod("addMemberBodyDetail")
                .addbody("memberId",String.valueOf(bodyMemberId))
                .addbody("bodyPartType",bodyPartType)
                .addbody("bodyPartValue",bodyPartValue)
                .addbody("bodyPartUnit",bodyPartUnit)
                .build(), callback);
    }

    /**
     * cardTarget (string, optional): 目标类型 ,
     cardTimes (integer, optional): 课程总次数 ,
     memberId (integer, optional): 用户ID ,
     memberMobile (string, optional): 用户手机号 ,
     memberName (string, optional): 用户姓名 ,
     memberSex (integer, optional): 用户性别（0=性别未知，1=男，2=女）
     */
    public static void createMember(Context context, MemberModel memberModel, MemberCardModel memberCardModel, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.MEMBER_API)
                .businessMethod("createMember")
                .addbody("memberId",memberModel.getMemberId())
                .addbody("memberMobile",memberModel.getMemberMobile())
                .addbody("memberName",memberModel.getMemberName())
                .addbody("memberSex",memberModel.getMemberSex())
                .addbody("cardTarget",memberCardModel.getCardTarget())
                .addbody("cardTimes",memberCardModel.getCardTimes())
                .build(), callback);
    }

    public static void updateMemberCard(Context context,String cardId,String cardTimes, OnResponseCallback callback) {
        HttpService.request(context, new CommonHttpRequest.Builder()
                .url(ApiConstant.MEMBER_API)
                .businessMethod("updateMemberCard")
                .addbody("cardId",String.valueOf(cardId))
                .addbody("cardTimes",String.valueOf(cardTimes))
                .build(), callback);
    }

    public static String createFullPath(String name){
        return ApiConstant.MEMBER_API +"/"+ ApiConstant.ApiVersion.VERSION_1_0_0 +"/"+name;
    }
}
