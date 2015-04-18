package com.example.wzq.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wzq.sample.util.CommenUtil;
import com.example.wzq.sample.util.EasyMap;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wzq on 15/4/13.
 */
public class EasyAdapter extends RecyclerView.Adapter<EasyAdapter.ViewHolder> {

    public static int MODE_MAP = 0;

    public static int MODE_SELF = 1;

    private List<Object> data;
    private int layoutId;
    private String[][] keys;
    private static int[] views;
    private int mode;
    private CallBack callBack;

    public EasyAdapter(List<Object> data, int layoutId, String[][] keys, int[] views, CallBack callBack) {
        this.mode = MODE_MAP;
        this.data = data;
        this.layoutId = layoutId;
        this.keys = keys;
        this.views = views;
        this.callBack = callBack;
    }

    public EasyAdapter(List<Object> data, int layoutId, int[] views, CallBack callBack) {
        this.mode = MODE_SELF;
        this.data = data;
        this.layoutId = layoutId;
        this.views = views;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(mode == MODE_MAP) {
            EasyMap item = (EasyMap) data.get(position);
            // text 1
            for (int i = 0; i < keys[1].length; i++) {
                viewHolder.tv.get(i).setText(Html.fromHtml(item.getString(keys[1][i])));
            }
            // img 0
            for (int i = 0; i < keys[0].length; i++) {
                ImageLoader.getInstance().displayImage(item.getString(keys[0][i]), viewHolder.img.get(i), CommenUtil.options);
            }
        }
        if(callBack != null){
            callBack.bindItemView(viewHolder,data.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void removeItem(Object item){
        int realPosition = data.indexOf(item);
        if(realPosition > -1) {
            data.remove(realPosition);
            notifyItemRemoved(realPosition);
        }
    }

    public void insertItem(Object item, int position){
        data.add(position, item);
        notifyItemInserted(position);
    }

    public void insertItem(Collection<?> items, int position){
        data.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public List<ImageView> img;
        public List<TextView> tv;
        public List<Button> btn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            img = new ArrayList<>();
            tv = new ArrayList<>();
            btn = new ArrayList<>();
            for (int i = 0; i < views.length; i++) {
                View temp = itemView.findViewById(views[i]);
                if (temp instanceof ImageView) {
                    img.add((ImageView) temp);
                } else if (temp instanceof TextView) {
                    if (temp instanceof Button) {
                        btn.add((Button) temp);
                    }else{
                        tv.add((TextView) temp);
                    }
                }
            }
        }
    }

    public interface  CallBack{
        public void bindItemView(ViewHolder holder, Object item, int position);
    }
}
