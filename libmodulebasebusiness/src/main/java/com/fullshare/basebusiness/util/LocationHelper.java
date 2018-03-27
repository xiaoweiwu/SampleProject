package com.fullshare.basebusiness.util;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.common.basecomponent.util.L;
import com.fullshare.basebusiness.BaseBusinessApplication;

/**
 * author: wuxiaowei
 * date : 2017/3/23
 */
public class LocationHelper {
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption = null;

    public LocationHelper() {
        init();
    }

    private void init() {
        // 声明mLocationOption对象
        mLocationClient = new AMapLocationClient(BaseBusinessApplication.get());
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        // 设置定位参数
        mLocationOption.setLocationCacheEnable(true);
    }

    public LocationHelper setOnceLocation(boolean onceLocation) {
        mLocationOption.setOnceLocation(onceLocation);
        if (!onceLocation) {
            mLocationOption.setInterval(2000);
        }
        return this;
    }

    /**
     * 设置之定位结果回调
     *
     * @param listener
     */
    public LocationHelper setLocationListener(final LocationListener listener) {
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                if (mLocationOption != null) {
                    mLocationClient.stopLocation();
                }
                if (listener != null) {
                    if (location != null) {
                        L.d(location);
                    }
                    listener.result(location);
                }
            }
        });
        return this;
    }

    /**
     * 销毁定位，连续定位必须在退出程序时调用
     */
    public LocationHelper onDestroy() {
        mLocationClient.onDestroy();
        mLocationClient = null;
        mLocationOption = null;
        return this;
    }

    public LocationHelper start() {
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
        return this;
    }

    public interface LocationListener {
        void result(AMapLocation location);
    }
}
