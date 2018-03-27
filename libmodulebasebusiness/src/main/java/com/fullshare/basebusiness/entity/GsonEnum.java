package com.fullshare.basebusiness.entity;

/**
 * Created by wuxiaowei on 2017/4/26.
 */

public interface GsonEnum<E> {

    String serialize();

    E deserialize(String jsonEnum);

}
