package com.fullshare.basebusiness.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Window;

import com.fullshare.basebusiness.R;

/**
 * User: wuxiaowei
 * Date: 2015-09-12
 * Time: 17:50
 */
public class BaseDialog extends Dialog {
    protected Context mContext;
    public BaseDialog(Context context) {
        super(context, R.style.customDialog);
        this.mContext = context;
    }

}
