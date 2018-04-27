package com.fullshare.basebusiness.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.common.basecomponent.adapter.recyclerview.BaseRecyclerAdapter;
import com.common.basecomponent.util.UnitConvertUtil;
import com.fullshare.basebusiness.R;

import java.util.List;


/**
 * Created by wuxiaowei on 2017/5/18.
 */

public class CommonAlertDialog extends BaseDialog {
    TextView tvMessage;
    TextView tvTip;
    Button btnLeft;
    Button btnRight;
    View llMessage;
    View llbuttons;
    RecyclerView recyclerView;
    BaseRecyclerAdapter adapter;

    private CommonAlertDialog(Context context, int layoutId) {
        super(context);
        if (layoutId == 0) {
            layoutId = R.layout.layout_common_alert_dialog;
        }
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        tvMessage = (TextView) view.findViewById(R.id.tv_title_message);
        btnLeft = (Button) view.findViewById(R.id.btnLeft);
        btnRight = (Button) view.findViewById(R.id.btnRight);
        tvTip = (TextView) view.findViewById(R.id.tv_sub_message);
        recyclerView = view.findViewById(R.id.recyclerView);
        llMessage = view.findViewById(R.id.ll_message);
        llbuttons = view.findViewById(R.id.ll_buttons);

        setContentView(view);
        Display d = getWindow().getWindowManager().getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.86); // 宽度设置为屏幕的0.65
    }

    private CommonAlertDialog(Context context) {
        this(context, R.layout.layout_common_alert_dialog);
    }

    public static Dialog showConfirmAlert(Context context, String message) {
        Dialog dialog = new Builder(context)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        return dialog;
    }

    public static Dialog showConfirmAlert(Context context, String button, String title, String message) {
        Dialog dialog = showConfirmAlert(context, button, title, message, null);
        return dialog;
    }

    public static Dialog showConfirmAlert(Context context, String button, String title, String message, final DialogInterface.OnClickListener clickListener) {
        Dialog dialog = new Builder(context)
                .setMessage(title)
                .setTip(message)
                .setPositiveButton(button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (clickListener != null) {
                            clickListener.onClick(dialog, which);
                        }
                    }
                }).show();
        return dialog;
    }

    public static Dialog showConfirmAlert(Context context, String positive, String negative, String title, String message, DialogInterface.OnClickListener clickListener) {
        Dialog dialog = new Builder(context)
                .setMessage(title)
                .setTip(message)
                .setPositiveButton(positive, clickListener).setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        return dialog;
    }

    public TextView getTvMessage() {
        return tvMessage;
    }

    public TextView getTvTip() {
        return tvTip;
    }

    public void setBtnLeft(String text) {
        btnLeft.setText(text);
    }

    public void setBtnRight(String text) {
        btnRight.setText(text);
    }

    public void setMessage(String message) {
        this.tvMessage.setText(message);
    }

    public static class Builder {
        protected String message;
        protected String negative;
        protected String tip;
        protected String positive;
        protected int layoutId;
        protected Context context;
        protected DialogInterface.OnClickListener negativeListener;
        protected DialogInterface.OnClickListener positiveListener;
        protected DialogInterface.OnDismissListener onDismissListener;
        protected DialogInterface.OnCancelListener onCancelListener;
        protected BaseRecyclerAdapter adapter;
        protected int which;
        protected List datas;
        protected int itemLayout;
        protected DialogInterface.OnClickListener choiceListener;
        protected RenderCallback renderCallback;
        public Builder(Context context) {
            this.context = context;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setNegativeButton(String text, DialogInterface.OnClickListener clickListener) {
            this.negative = text;
            this.negativeListener = clickListener;
            return this;
        }

        public Builder setPositiveButton(String text, DialogInterface.OnClickListener clickListener) {
            this.positive = text;
            this.positiveListener = clickListener;
            return this;
        }

        public Builder setTip(String tip) {
            this.tip = tip;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public CommonAlertDialog show() {
            final CommonAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public Builder setSingleChoice(int itemLayout, List datas,int which,OnClickListener clickListener,RenderCallback renderCallback) {
            this.choiceListener = clickListener;
            this.itemLayout = itemLayout;
            this.datas = datas;
            this.which = which;
            this.renderCallback = renderCallback;
            return this;
        }


        protected CommonAlertDialog create() {
            final CommonAlertDialog dialog = new CommonAlertDialog(context, layoutId);
            if (TextUtils.isEmpty(negative)) {
                if (dialog.btnLeft != null)
                    dialog.btnLeft.setVisibility(View.GONE);
            } else {
                dialog.setBtnLeft(negative);
            }
            if (TextUtils.isEmpty(positive)) {
                if (dialog.btnRight != null)
                    dialog.btnRight.setVisibility(View.GONE);
            } else {
                dialog.setBtnRight(positive);
            }

            if(TextUtils.isEmpty(positive)&&TextUtils.isEmpty(negative)){
                dialog.llbuttons.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(message)) {
                dialog.tvMessage.setText(message);
            }else {
                dialog.llMessage.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(tip)) {
                dialog.tvTip.setText(tip);
                dialog.tvTip.setVisibility(View.VISIBLE);
            }
            if (negativeListener != null) {
                dialog.btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negativeListener.onClick(dialog, 0);
                    }
                });
            }
            if (positiveListener != null) {
                dialog.btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        positiveListener.onClick(dialog, 1);
                    }
                });
            }
            if(datas !=null) {
                dialog.recyclerView.getLayoutParams().height = UnitConvertUtil.dip2px(context,50)*datas.size();
                this.adapter = new BaseRecyclerAdapter<Object>(context, datas, itemLayout) {
                    @Override
                    public void renderItemView(final ViewHolder holder, Object o) {
                        if(renderCallback!=null){
                            renderCallback.renderItemView(holder,o,which == holder.getLayoutPosition());
                        }
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                which = holder.getLayoutPosition();
                                notifyDataSetChanged();
                                choiceListener.onClick(dialog,which);
                            }
                        });
                    }
                };
                dialog.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                dialog.recyclerView.setAdapter(adapter);
            }
            return dialog;
        }
    }

    public interface RenderCallback{
        void renderItemView(final BaseRecyclerAdapter.ViewHolder holder, Object o,boolean selected);
    }
}
