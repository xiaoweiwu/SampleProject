package com.fstop.simpleproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.fullshare.basebusiness.base.CommonBaseActivity;

import org.xml.sax.XMLReader;

public class MainActivity extends CommonBaseActivity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = this;;
        tv_html = (TextView) findViewById(R.id.tv_html);
        tv_html.setText(getClickableHtml(html));
        tv_html.setClickable(true);
        tv_html.setMovementMethod(LinkMovementMethod.getInstance());
    }

    String html = "<p>点击打开我的博客<a href=\"http://blog.csdn.net/u013653230\">diy_coder</a></p>"
            + "<p>这个<a href=\"#搞笑视频\">#搞笑视频#</a>笑死我了，哈哈，快来看看"
            + "<a href=\"@24710\">@生活如此多娇</a></p>"
            + "<p><tag>云音乐福利</tag></p>"
//            + "<p><img src=\"" + R.mipmap.ic_launcher + "\"/></p>"
            + "<p><img src=\"http://avatar.csdn.net/E/F/8/1_u013653230.jpg\"></p>";

    private static final int TOPIC_TAG = 0;
    private static final int AT_USER = 1;
    private static final int NORMAL_URL = 2;
    private static final int CUSTOM_TAG = 3;


    private Context mContext;
    private TextView tv_html;

    private CharSequence getClickableHtml(String html) {
        Spanned spannedHtml = Html.fromHtml(html, mImageGetter, new CustomTagHandler());
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            int start = clickableHtmlBuilder.getSpanStart(span);
            int end = clickableHtmlBuilder.getSpanEnd(span);
            int flag = clickableHtmlBuilder.getSpanFlags(span);
            final String url = span.getURL();
            setUpUrl(clickableHtmlBuilder, span, start, end, flag, url);
        }
        return clickableHtmlBuilder;
    }

    private void setUpUrl(SpannableStringBuilder clickableHtmlBuilder, URLSpan span, int start, int end, int flag, final String url) {
        CustomClickableSpan clickableSpan = null;
        if (url.startsWith("@")) {
            clickableSpan = new CustomClickableSpan(AT_USER, url);
        } else if (url.startsWith("#")) {
            clickableSpan = new CustomClickableSpan(TOPIC_TAG, url);
        } else if (url.startsWith("http") || url.startsWith("https")) {
            clickableSpan = new CustomClickableSpan(NORMAL_URL, url);
        }
        clickableHtmlBuilder.removeSpan(span);//清除当前span
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flag);
    }


    //处理点击事件
    class CustomClickableSpan extends ClickableSpan {
        String text;
        int type;

        public CustomClickableSpan(int type, String text) {
            this.text = text;
            this.type = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            int color = Color.parseColor("#0000ff");
            ds.setColor(color);//设置点击文字的颜色
            ds.setUnderlineText(false); //去掉下划线
        }

        @Override
        public void onClick(View widget) {
            //点击事件处理
            Intent intent = null;
            switch (type) {
                case AT_USER://@用户
//                    intent = new Intent(mContext, UserActivity.class);
//                    intent.putExtra("userId", text);
                    break;
                case TOPIC_TAG://#话题#
//                    intent = new Intent(mContext, TopicActivity.class);
//                    intent.putExtra("topic", text);
                    break;
                case NORMAL_URL://网页
//                    intent = new Intent(mContext, WebActivity.class);
//                    intent.putExtra("url", text);
                    break;
                case CUSTOM_TAG://自定义标签
//                    intent = new Intent(mContext, CustomTagActivity.class);
//                    intent.putExtra("tag", text);
                    break;
            }
            startActivity(intent);
        }
    }

    //处理自定义标签
    class CustomTagHandler implements Html.TagHandler {
        private int startIndex = 0;
        private int stopIndex = 0;

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.toLowerCase().equals("tag")) {//自定义标签处理
                if (opening) {//开始标签
                    startTag(tag, output, xmlReader);
                } else {//结束标签
                    endTag(tag, output, xmlReader);
                }
            }
        }

        public void startTag(String tag, Editable output, XMLReader xmlReader) {
            startIndex = output.length();
        }

        public void endTag(String tag, Editable output, XMLReader xmlReader) {
            stopIndex = output.length();
            String content = output.subSequence(startIndex, stopIndex).toString();//获取点击内容
            output.setSpan(new CustomClickableSpan(CUSTOM_TAG, content), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            Log.e("clickText ", content);
        }

    }

    //绘制图片
    class URLDrawable extends BitmapDrawable {
        public Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }

    //处理图片
    Html.ImageGetter mImageGetter = new Html.ImageGetter() {
        private URLDrawable urlDrawable = new URLDrawable();

        //加载网络图片
        @Override
        public Drawable getDrawable(String source) {
            Glide.with(mContext).load(source).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    urlDrawable.bitmap = bitmap;
                    urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    //不加这个图片加载不上来，不知道什么原因
                    tv_html.setText(tv_html.getText());
                }

            });

            return urlDrawable;
        }

//         加载本地图片
//        @Override
//        public Drawable getDrawable(final String source) {
//            int id = Integer.parseInt(source);
//            Drawable drawable = mContext.getResources().getDrawable(id);
//            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
//                    .getIntrinsicHeight());
//            return drawable;
//        }

    };
}
