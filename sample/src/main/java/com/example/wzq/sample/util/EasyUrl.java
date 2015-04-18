package com.example.wzq.sample.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wzq on 15/3/13.
 */
public class EasyUrl {
    private List<String> urlList = new ArrayList<>();
    private String linkString = "🔗网页链接";
    private Context context;
    private ClickListener listener;

    public EasyUrl(Context context, ClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public SpannableStringBuilder getResult(String str) {
        str = stringFilter(str);
        SpannableStringBuilder result = new SpannableStringBuilder(str);
        if(listener!=null) {
            int temp = -1;
            int len = linkString.length();
            for (int i = 0; i < urlList.size(); i++) {
                temp = str.indexOf(linkString, temp + 1);
                result.setSpan(clickableSpanFactory(urlList.get(i)), temp, temp + len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //result.setSpan(imageSpanFactory(), temp, temp+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return result;
    }

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

//    private SpannableStringBuilder parseStr(String url){
//        SpannableStringBuilder ssb = new SpannableStringBuilder(linkString);
//        ssb.setSpan(clickableSpanFactory(url), 0, linkString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return ssb;
//    }

    private ClickableSpan clickableSpanFactory(final String url) {
        return new ClickableSpan() {
            @Override
            public void onClick(View view) {
                listener.callback(url);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(android.R.color.holo_blue_light));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();

            }
        };

    }

//    private ImageSpan imageSpanFactory(){
//        Drawable d = context.getResources().getDrawable(R.drawable.ic_link_nomal);
//        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
//        return span;
//    }

    public interface ClickListener {
        public void callback(String url);
    }
}
