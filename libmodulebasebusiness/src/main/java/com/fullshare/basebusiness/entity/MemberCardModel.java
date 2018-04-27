package com.fullshare.basebusiness.entity;

import android.os.Parcel;

import com.common.basecomponent.entity.BaseData;

/**
 * Created by wuxiaowei on 2018/4/25.
 */

public class MemberCardModel implements BaseData {

    private int cardCode;
    private int cardCreatecoach;
    private String cardCreatedate;
    private String cardId;
    private String cardMemberId;
    private String cardTarget;
    private int cardTimes;
    private int cardTimesConsume;
    private int cardTimesRemainder;
    private int cardType;

    public int getCardCode() {
        return cardCode;
    }

    public void setCardCode(int cardCode) {
        this.cardCode = cardCode;
    }

    public int getCardCreatecoach() {
        return cardCreatecoach;
    }

    public void setCardCreatecoach(int cardCreatecoach) {
        this.cardCreatecoach = cardCreatecoach;
    }

    public String getCardCreatedate() {
        return cardCreatedate;
    }

    public void setCardCreatedate(String cardCreatedate) {
        this.cardCreatedate = cardCreatedate;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardMemberId() {
        return cardMemberId;
    }

    public void setCardMemberId(String cardMemberId) {
        this.cardMemberId = cardMemberId;
    }

    public String getCardTarget() {
        return cardTarget;
    }

    public void setCardTarget(String cardTarget) {
        this.cardTarget = cardTarget;
    }

    public int getCardTimes() {
        return cardTimes;
    }

    public void setCardTimes(int cardTimes) {
        this.cardTimes = cardTimes;
    }

    public int getCardTimesConsume() {
        return cardTimesConsume;
    }

    public void setCardTimesConsume(int cardTimesConsume) {
        this.cardTimesConsume = cardTimesConsume;
    }

    public int getCardTimesRemainder() {
        return cardTimesRemainder;
    }

    public void setCardTimesRemainder(int cardTimesRemainder) {
        this.cardTimesRemainder = cardTimesRemainder;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cardCode);
        dest.writeInt(this.cardCreatecoach);
        dest.writeString(this.cardCreatedate);
        dest.writeString(this.cardId);
        dest.writeString(this.cardMemberId);
        dest.writeString(this.cardTarget);
        dest.writeInt(this.cardTimes);
        dest.writeInt(this.cardTimesConsume);
        dest.writeInt(this.cardTimesRemainder);
        dest.writeInt(this.cardType);
    }

    public MemberCardModel() {
    }

    protected MemberCardModel(Parcel in) {
        this.cardCode = in.readInt();
        this.cardCreatecoach = in.readInt();
        this.cardCreatedate = in.readString();
        this.cardId = in.readString();
        this.cardMemberId = in.readString();
        this.cardTarget = in.readString();
        this.cardTimes = in.readInt();
        this.cardTimesConsume = in.readInt();
        this.cardTimesRemainder = in.readInt();
        this.cardType = in.readInt();
    }

    public static final Creator<MemberCardModel> CREATOR = new Creator<MemberCardModel>() {
        @Override
        public MemberCardModel createFromParcel(Parcel source) {
            return new MemberCardModel(source);
        }

        @Override
        public MemberCardModel[] newArray(int size) {
            return new MemberCardModel[size];
        }
    };

    @Override
    public String getDisplayTitle() {
        return null;
    }
}
