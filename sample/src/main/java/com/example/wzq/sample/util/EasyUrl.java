package com.example.wzq.sample.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wzq on 15/3/13.
 *
 * replace the website url
 */
public class EasyUrl {

    private List<String> urlList = new ArrayList<>();

    private Context context;

    private ClickListener listener;

    private int imageId = -1;

    private int colorId = android.R.color.holo_blue_light;

    private String linkString = "🔗网页链接";

    public EasyUrl(Context context) {
        this.context = context;
    }

    public EasyUrl(Context context, ClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public EasyUrl setLinkString(String prefix, String body) {
        this.linkString = prefix + body;
        return this;
    }

    public EasyUrl setColorId(int colorId) {
        this.colorId = colorId;
        return this;
    }

    public EasyUrl setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    /**
     * @param str 需要筛选的内容
     * @return URL全部替换成可点击的文字或图片
     */
    public SpannableStringBuilder replace(String str) {
        str = stringFilter(str);
        SpannableStringBuilder result = new SpannableStringBuilder(str);
        int temp = -1;
        int len = linkString.length();
        for (int i = 0; i < urlList.size(); i++) {
            temp = str.indexOf(linkString, temp + 1);
            result.setSpan(clickableSpanFactory(urlList.get(i)), temp, temp + len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if(this.imageId > 0) result.setSpan(imageSpanFactory(), temp, temp+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return result;
    }

    /**
     * @param str
     * @return 过滤并替换内容
     */
    private String stringFilter(String str) {
        String regexp
                = "(((http|ftp|https|file)://)|((?<!((http|ftp|https|file)://))www\\.))"  // 以http...或www开头
                + ".*?"                                                                   // 中间为任意内容，惰性匹配
                + "(?=(&nbsp;|\\s|　|<br />|$|[<>]))";                                     // 结束条件
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String url = matcher.group().substring(0, 3).equals("www") ? "http://" + matcher.group() : matcher.group();
            urlList.add(url);
            str = str.toString().replace(matcher.group().trim(), linkString);
        }
        return str;
    }

    /**
     * @param url
     * @return 生成clickable span
     */
    private ClickableSpan clickableSpanFactory(final String url) {
        return new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                listener.callback(url);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(colorId));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
    }

    /**
     * @return 生成图片
     */
    private ImageSpan imageSpanFactory() {
        Drawable d = context.getResources().getDrawable(imageId);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        return span;
    }

    /**
     *替换字符点击事件
     */
    public interface ClickListener {

        /**
         * @param url 原始URL
         */
        void callback(String url);
    }
}
