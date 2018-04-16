package com.common.basecomponent.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.common.basecomponent.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wuxiaowei on 2017/6/21.
 */

public class PermissionsUtil {
    private boolean permissionGranted = true;
    private boolean shouldShowRequestPermissionRationale = true;
    private RxPermissions rxPermissions;
    private Activity activity;
    private OnPermissionGrantedListener onPermissionGrantedListener;
    private String message;
    private String[]permissonArray;
    private boolean force = true;
    public PermissionsUtil(Activity activity){
        this(activity,true);
    }
    public PermissionsUtil(Activity activity, boolean force){
        rxPermissions = new RxPermissions(activity);
        this.activity = activity;
        this.force = force;
    }
    public PermissionsUtil setPermissons(final String... permissions){
        this.permissonArray = permissions;
        return this;
    }
    public void startReqestPermissions(){
        permissionGranted = true;
        shouldShowRequestPermissionRationale = true;
        if(permissonArray==null||permissonArray.length==0){
            return;
        }
        rxPermissions.requestEach(permissonArray)
                .subscribe(new Observer<Permission>() {

                    @Override
                    public void onComplete() {
                        if(permissionGranted) {
                            if(onPermissionGrantedListener!=null) {
                                onPermissionGrantedListener.onPermissionGranted(true,false);
                            }
                        }else if(!force){
                            if(onPermissionGrantedListener!=null) {
                                onPermissionGrantedListener.onPermissionGranted(false,true);
                            }
                        }else{
                            if(shouldShowRequestPermissionRationale){
                                new AlertDialog.Builder(activity)
                                        .setMessage(String.format(activity.getString(R.string.retry_require_permission), message))
                                        .setPositiveButton(activity.getString(R.string.confirm), new OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startReqestPermissions();
                                                dialog.dismiss();
                                            }
                                        }).setNegativeButton(activity.getString(R.string.cancel), new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if(onPermissionGrantedListener!=null) {
                                            onPermissionGrantedListener.onPermissionGranted(false,true);
                                        }
                                    }
                                }).show();
                            }else{
                                new AlertDialog.Builder(activity)
                                        .setMessage(String.format(activity.getString(R.string.goto_permission_manager_center), message))
                                        .setPositiveButton(activity.getString(R.string.confirm), new OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ActivityUtil.getAppDetailSettingIntent(activity);
                                                dialog.dismiss();
                                                onPermissionGrantedListener.onPermissionGranted(false,false);
                                            }
                                        }).setNegativeButton(activity.getString(R.string.cancel), new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if(onPermissionGrantedListener!=null) {
                                            onPermissionGrantedListener.onPermissionGranted(false,true);
                                        }
                                    }
                                }).show();
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Permission permission) {
                        if(!permission.granted){
                            permissionGranted = permission.granted;
                        }

                        if(!permission.shouldShowRequestPermissionRationale){
                            shouldShowRequestPermissionRationale = permission.shouldShowRequestPermissionRationale;
                        }
                    }

                });
    }

    public PermissionsUtil setMessage(String message){
        this.message = message;
        return this;
    }

    public PermissionsUtil setOnPermissionGrantedListener(OnPermissionGrantedListener onPermissionGrantedListener) {
        this.onPermissionGrantedListener = onPermissionGrantedListener;
        return this;
    }

    public interface OnPermissionGrantedListener{
        void onPermissionGranted(boolean granted, boolean userCancel);
    }
}
