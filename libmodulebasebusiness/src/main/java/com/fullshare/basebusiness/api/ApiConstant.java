package com.fullshare.basebusiness.api;

import com.common.basecomponent.SystemInfo;
import com.fullshare.basebusiness.BaseBusinessApplication;

/**
 * Created by wuxiaowei on 2017/1/16.
 */
public class ApiConstant {

    public static final int EN_TEST = 1;//测试，debug和release都可以
    public static final int EN_FORMAL = 2;//正式，上线
    public static final String APP_ID = "123456";
    public static final String APP_SECURITY_KEY = "d67266586dffe8085126f3383afe8e3c";
    public static final String APP_SHARE_URL = "";
    public static String BASE_HOST = ApiEnvironment.RELEASE.getBaseApiUrl();
    public static String HTML5_HOST = ApiEnvironment.RELEASE.getBaseH5Url();
    public static final int ENVIRONMENT = SystemInfo.getSystemInfo().getHostType();
    static {
        if(ENVIRONMENT == ApiEnvironment.DEVELOP.getType()){
            BASE_HOST = ApiEnvironment.DEVELOP.getBaseApiUrl();
            HTML5_HOST = ApiEnvironment.DEVELOP.getBaseH5Url();
        }else if(ENVIRONMENT == ApiEnvironment.TEST.getType()){
            BASE_HOST = ApiEnvironment.TEST.getBaseApiUrl();
            HTML5_HOST = ApiEnvironment.TEST.getBaseH5Url();
        }else if(ENVIRONMENT == ApiEnvironment.RELEASE.getType()){
            BASE_HOST = ApiEnvironment.RELEASE.getBaseApiUrl();
            HTML5_HOST = ApiEnvironment.RELEASE.getBaseH5Url();
        }
    }
    public static String COACH_API = BASE_HOST+"/coach";
    public static String ACTION_API = BASE_HOST+"/action";
    public static String MEMBER_API = BASE_HOST+"/member/manager";
    public enum ApiEnvironment {
        DEVELOP(1, "https://api.donkeyplay.com/gym-api", "http://tm.fshtop.com"),
        TEST(2, "https://api.donkeyplay.com/gym-api", "http://tm.fshtop.com"),
        RELEASE(3, "https://api.donkeyplay.com/gym-api", "http://m.fshtop.com");

        private int type;
        private String baseApiUrl;
        private String baseH5Url;

        ApiEnvironment(int type, String baseApiUrl, String baseH5Url) {
            this.baseApiUrl = baseApiUrl;
            this.baseH5Url = baseH5Url;
            this.type = type;
        }

        public int getType() {
            return type;
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
