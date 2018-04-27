package com.common.basecomponent.eventbus;

/**
 * Created by fs_wuxiaowei on 2016/9/12.
 */
public class EventModel {
    /**
     * 事件ID
     */
    private String eventId;
    //单个
    private Object data1;

    private Object data2;

    private Object data3;

    private Object data4;

    private EventModel(Builder builder) {
        eventId = builder.eventId;
        data1 = builder.data1;
        data2 = builder.data2;
        data3 = builder.data3;
        data4 = builder.data4;
    }

    public Object getData1() {
        return data1;
    }

    public Object getData2() {
        return data2;
    }

    public Object getData3() {
        return data3;
    }

    public Object getData4() {
        return data4;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static boolean handleEvent(EventModel eventModel,String eventId) {
        return eventModel!=null&&eventModel.eventId.equals(eventId);
    }

    public static final class Builder {
        private String eventId;
        private Object data1;
        private Object data2;
        private Object data3;
        private Object data4;

        private Builder() {
        }

        public Builder eventId(String val) {
            eventId = val;
            return this;
        }

        public Builder data1(Object val) {
            data1 = val;
            return this;
        }

        public Builder data2(Object val) {
            data2 = val;
            return this;
        }

        public Builder data3(Object val) {
            data3 = val;
            return this;
        }

        public Builder data4(Object val) {
            data4 = val;
            return this;
        }

        public EventModel build() {
            return new EventModel(this);
        }
    }
}
