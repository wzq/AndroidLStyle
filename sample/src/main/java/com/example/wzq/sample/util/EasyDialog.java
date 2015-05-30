package com.example.wzq.sample.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wzq on 15/5/29.
 */
public class EasyDialog implements View.OnClickListener{

    private Context context;
    private AlertDialog dialog;
    private TextView title,cancel, sure, content;
    private LinearLayout root;

    public EasyDialog(Context context) {
        this.context = context;
    }

    public EasyDialog createTextDialog(){
//        root = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_text, null);
//        title = (TextView) root.findViewById(R.id.alert_title);
//        cancel = (TextView) root.findViewById(R.id.alert_cancel);
//        sure = (TextView) root.findViewById(R.id.alert_sure);
//        content = (TextView) root.findViewById(R.id.alert_content);
//        cancel.setOnClickListener(this);
//        dialog = new AlertDialog.Builder(context).setView(root).create();
//        dialog.setCanceledOnTouchOutside(true);
        return this;
    }

    public EasyDialog setContent(String s){
        this.content.setText(Html.fromHtml(s));
        return this;
    }

    public EasyDialog setRightButton(String s){
        this.sure.setVisibility(View.VISIBLE);
        this.sure.setText(Html.fromHtml(s));
        return this;
    }

    public AlertDialog getDialog(){
        return dialog;
    }

    public EasyDialog setTitle(String title){
        this.title.setVisibility(View.VISIBLE);
        this.title.setText(title);
        return this;
    }

    public EasyDialog setCustomView(View view){
        this.content.setVisibility(View.GONE);
        this.root.addView(view, 1);
        return this;
    }

    public void show(){
        dialog.show();
    }

    public void hidden(){
        dialog.dismiss();
    }

    public TextView getLeftButton(){
        return cancel;
    }

    public TextView getRightButton(){
        return sure;
    }

    public TextView getTitle(){
        return title;
    }

    public TextView getContent(){
        return content;
    }

    @Override
    public void onClick(View arg0) {
        dialog.dismiss();
    }
}
