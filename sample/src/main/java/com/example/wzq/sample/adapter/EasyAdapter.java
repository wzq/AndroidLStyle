package com.example.wzq.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wzq.sample.R;
import com.example.wzq.sample.util.CommenUtil;
import com.example.wzq.sample.util.EasyMap;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzq on 15/4/13.
 */
public class EasyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == 0) {
            return new EasyHolder(LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false));
        }else{
            return new LoadHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.load_default, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof  EasyHolder) {
            EasyHolder holder = (EasyHolder) viewHolder;
            if (mode == MODE_MAP) {
                EasyMap item = (EasyMap) data.get(position);
                // text 1
                for (int i = 0; i < keys[1].length; i++) {
                    holder.tv.get(i).setText(Html.fromHtml(item.getString(keys[1][i])));
                }
                // img 0
                for (int i = 0; i < keys[0].length; i++) {
                    ImageLoader.getInstance().displayImage(item.getString(keys[0][i]), holder.img.get(i), CommenUtil.options);
                }
            }
            if (callBack != null) {
                callBack.bindItemView(holder, data.get(position), position);
            }
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

    public void showLoad(){
        data.add(getItemCount(), null);
        notifyItemInserted(getItemCount());
    }

    public void loadComplete(){
        removeItem(null);
    }

    public boolean isLoading(){
        return data.indexOf(null)>0?true:false;
    }

//    public void insertItem(Collection<?> items, int position){
//        data.addAll(position, items);
//        notifyItemRangeInserted(position, items.size());
//    }

    public static class EasyHolder extends RecyclerView.ViewHolder {
        public List<ImageView> img;
        public List<TextView> tv;
        public List<Button> btn;
        public List<View> v;

        public EasyHolder(View itemView) {
            super(itemView);
            img = new ArrayList<>();
            tv = new ArrayList<>();
            btn = new ArrayList<>();
            for (int i = 0; i < views.length; i++) {
                View temp = itemView.findViewById(views[i]);
                if (temp instanceof ImageView) {
                    img.add((ImageView) temp);
                    continue;
                } else if (temp instanceof TextView) {
                    if (temp instanceof Button) {
                        btn.add((Button) temp);
                    }else{
                        tv.add((TextView) temp);
                    }
                    continue;
                } else {
                    v.add(temp);
                    continue;
                }
            }
        }
    }

    public static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public interface  CallBack{
        void bindItemView(EasyHolder holder, Object item, int position);
    }

    /**
     * @param position
     * @return Type: 0 normal, 1 load
     */
    @Override
    public int getItemViewType(int position) {
        return (data.get(position) == null)?1:0;
    }
}
