package com.fullshare.basebusiness.api;

/**
 * Created by wuxiaowei on 2017/1/16.
 */
public class ApiConstant {

    public static final int EN_TEST = 1;//测试，debug和release都可以
    public static final int EN_FORMAL = 2;//正式，上线
    public static final String APP_ID = "123456";
    public static final String APP_SECURITY_KEY = "d67266586dffe8085126f3383afe8e3c";
    public static String BASE_HOST = ApiEnvironment.RELEASE.getBaseApiUrl();
    public static String HTML5_HOST = ApiEnvironment.RELEASE.getBaseH5Url();
    public static String MEMBER_API = BASE_HOST + "/member-api";

    public enum ApiEnvironment {
        DEVELOP(1, "http://192.168.7.212:8082", "http://tm.fshtop.com"),
        TEST(1, "http://tapi.fshtop.com", "http://tm.fshtop.com"),
        RELEASE(1, "http://api.fshtop.com", "http://m.fshtop.com");

        private int type;
        private String baseApiUrl;
        private String baseH5Url;

        private ApiEnvironment(int type, String baseApiUrl, String baseH5Url) {
            this.baseApiUrl = baseApiUrl;
            this.baseH5Url = baseH5Url;
        }

        public String getBaseApiUrl() {
            return baseApiUrl;
        }

        public String getBaseH5Url() {
            return baseH5Url;
        }
    }

    /**
     * api 版本
     **/
    public static class ApiVersion {

        public static final String VERSION_1_0_0 = "1.0.0";
        public static final String VERSION_2_0_0 = "2.0.0";
    }

}
