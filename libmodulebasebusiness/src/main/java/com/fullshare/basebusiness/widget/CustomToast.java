package com.fullshare.basebusiness.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.basecomponent.util.UnitConvertUtil;
import com.fullshare.basebusiness.R;
import com.fullshare.basebusiness.base.CommonBaseActivity;

/**
 * Created by xpf on 2017/1/12.
 */

public class CustomToast {

    /**
     * 显示表示为正确的自定义Toast
     *
     * @param context 上下文
     * @param message 文字描述
     */
    public static void showSuccessToast(Context context, String message) {
        Toast toast = getToast(context, message, R.drawable.ic_120_yes_normal_b);
        toast.show();
    }


    public static void showSuccessToast(Context context, int contentRes) {
        showSuccessToast(context, context.getResources().getString(contentRes));
    }

    /**
     * 显示表示为错误的自定义Toast
     *
     * @param context 上下文
     * @param message 文字描述
     */
    public static void showFailedToast(Context context, String message) {
        Toast toast = getToast(context, message, R.drawable.ic_120_no_normal_b);
        toast.show();
    }

    public static void showFailedToast(Context context, int contentRes) {
        showFailedToast(context, context.getResources().getString(contentRes));
    }

    /**
     * 显示表示为错误的自定义Toast
     */
    public static void showTextToast(Context context, String message) {
        Toast toast = getToast(context, message, 0);
        toast.show();
    }

    public static void showSuccessDialog(Context context, String message) {
        showSuccessDialog(context, message, null);
    }

    /**
     * @param context
     * @param message
     * @param runnable dismiss时的回调
     */
    public static void showSuccessDialog(Context context, String message, final Runnable runnable) {
        final Dialog dialog = getDialog(context, message, R.drawable.ic_120_yes_normal_b, runnable);
        dialog.show();

    }

    /**
     * dialog弹窗
     *
     * @param context
     * @param message
     * @param runnable 弹窗消失的回调
     */

    public static Dialog showFailedDialog(Context context, String message, final Runnable runnable) {
        final Dialog dialog = getDialog(context, message, R.drawable.ic_120_no_normal_b, runnable);
        dialog.show();
        return dialog;
    }

    public static Dialog showFailedDialog(Context context, String message) {
        return showFailedDialog(context, message, null);
    }

    public static Dialog showTextDialog(Context context, String message, final Runnable runnable) {
        final Dialog dialog = getDialog(context, message, 0, runnable);
        dialog.show();
        return dialog;
    }

    public static void showTextDialog(Context context, String message) {
        showTextDialog(context, message, null);
    }

    private static Dialog getDialog(Context context, String message, int redId, final Runnable runnable) {
        final Dialog mDialog = new Dialog(context, R.style.customDialog);
        mDialog.setContentView(R.layout.layout_toast);
        ImageView imageView = (ImageView) mDialog.findViewById(R.id.iv_imageView);
        TextView textView = (TextView) mDialog.findViewById(R.id.tv_textView);
        imageView.setImageResource(redId);
        if (redId == 0) {
            imageView.setVisibility(View.GONE);
            textView.setPadding(textView.getPaddingLeft(), UnitConvertUtil.dip2px(context, 20), textView.getPaddingRight(), UnitConvertUtil.dip2px(context, 20));
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(redId);
        }
        textView.setText(message);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        CommonBaseActivity activity = (CommonBaseActivity) context;
        activity.postDelay(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
            }
        }, 2000);
        return mDialog;
    }


    private static Toast getToast(Context context, String content, int resId) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_imageView);
        TextView textView = (TextView) view.findViewById(R.id.tv_textView);
        if (resId == 0) {
            imageView.setVisibility(View.GONE);
            textView.setPadding(textView.getPaddingLeft(), UnitConvertUtil.dip2px(context, 20), textView.getPaddingRight(), UnitConvertUtil.dip2px(context, 20));
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(resId);
        }
        textView.setText(content);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        return toast;
    }
}
