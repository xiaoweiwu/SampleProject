package com.fullshare.basebusiness.api;

/**
 * Created by wuxiaowei on 2017/2/20.
 */

public class ApiTestUtil {

    public static void setEnvironment(Environment environment) {
        ApiConstant.ApiEnvironment apiEnvironment = null;
        switch (environment) {
            case RELEASE:
                apiEnvironment = ApiConstant.ApiEnvironment.RELEASE;
                break;
            case PRE_RELEASE:
                apiEnvironment = ApiConstant.ApiEnvironment.RELEASE;
                break;
            case TEST:
                apiEnvironment = ApiConstant.ApiEnvironment.TEST;
                break;
            case DEVELOP:
                apiEnvironment = ApiConstant.ApiEnvironment.DEVELOP;
                break;
        }
        ApiConstant.BASE_HOST = apiEnvironment.getBaseApiUrl();
        ApiConstant.HTML5_HOST = apiEnvironment.getBaseH5Url();
        ApiConstant.COACH_API = ApiConstant.BASE_HOST + "/coach";

    }

    public enum Environment {
        TEST(1), DEVELOP(2), PRE_RELEASE(3), RELEASE(4);
        private int value;

        Environment(int value) {
            this.value = value;
        }

    }
}
