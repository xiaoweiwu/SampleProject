package com.fullshare.basebusiness.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.fullshare.basebusiness.BaseBusinessApplication;

/**
 * Created by wuxiaowei on 2017/6/6.
 */

public class TinkerUtil {
    private static boolean background = false;

    public static boolean isBackground() {
        return background;
    }

    public static void setBackground(boolean background) {
        TinkerUtil.background = background;
    }

    public static void restartProcess() {
        BaseBusinessApplication.get().onExitApp();
    }

    public static class ScreenState {
        public ScreenState(final Context context, final IOnScreenOff onScreenOffInterface) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            context.registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent in) {
                    String action = in == null ? "" : in.getAction();
                    if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                        if (onScreenOffInterface != null) {
                            onScreenOffInterface.onScreenOff();
                        }
                    }
                    context.unregisterReceiver(this);
                }
            }, filter);
        }

        public interface IOnScreenOff {
            void onScreenOff();
        }
    }
}
