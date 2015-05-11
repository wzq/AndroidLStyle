package com.example.wzq.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.example.wzq.sample.R;
import com.example.wzq.sample.adapter.EasyAdapter;
import com.example.wzq.sample.adapter.LoadMoreAdapter;
import com.example.wzq.sample.util.CommenUtil;
import com.example.wzq.sample.util.EasyMap;
import com.example.wzq.sample.util.EasyUrl;
import com.example.wzq.sample.util.HostSet;
import com.example.wzq.sample.util.network.EasyListener.CallBack;
import com.example.wzq.sample.util.network.VolleyHelper;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;


public class MainActivity extends BaseActivity implements EasyAdapter.CallBack, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, CallBack{

    private static int ANIM_TIME = 300;

    private int startRow = 0, pageSize = 20;

    private RecyclerView recyclerView;

    private EasyAdapter adapter;

    private List<Object> data = new ArrayList<>();

    private SwipeRefreshLayout swipe;

    private FloatingActionButton fab;

    private FloatingActionMenu fabMenu;

    private int mScrollOffset = 4;

    @Override
    protected void mainCode(Bundle savedInstanceState) {
        setBodyView(R.layout.activity_main);
        swipe = (SwipeRefreshLayout) findViewById(R.id.main_swipe);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);

        fab = (FloatingActionButton) findViewById(R.id.main_fab);
        fabMenu = (FloatingActionMenu) findViewById(R.id.main_fab_menu);
        fab.hide(false);
        fabMenu.hideMenuButton(false);
        fabMenu.setClosedOnTouchOutside(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new ScaleInBottomAnimator());
        recyclerView.getItemAnimator().setAddDuration(ANIM_TIME);
        recyclerView.getItemAnimator().setRemoveDuration(ANIM_TIME);
        swipe.setColorSchemeResources(R.color.swipe_a, R.color.swipe_b, R.color.swipe_c, R.color.swipe_d);
        swipe.setOnRefreshListener(this);
        fab.setOnClickListener(this);
        recyclerView.setOnScrollListener(new LoadMoreAdapter() {
            @Override
            protected void loadData() {
                adapter.insertItem(null, adapter.getItemCount());
            }

            @Override
            protected void freeSpace(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dy) > mScrollOffset) {
                    if (dy > 0) {
                        fabMenu.hideMenuButton(true);
                    } else {
                        fabMenu.showMenuButton(true);
                    }
                }
            }
        });
        initData();
    }

    private void initData(){
        EasyMap params = new EasyMap();
        params.put("page_size", pageSize);
        params.put("start_row", startRow);
        params.put("index", 0);
        VolleyHelper.getInstance(this).post(HostSet.FIND_NEWS, "user_news_json", params, EasyMap.class, this);
    }

    @Override
    public void updateUI(Object result, int reqCode) {
        EasyMap temp = (EasyMap) result;
        data.clear();
        data.addAll(temp.getList("list"));
        int views[] = {R.id.item_home_user_head,R.id.item_home_pic,R.id.item_home_user_name,R.id.item_home_type_name,R.id.item_home_content};
        if(adapter == null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    fab.show(true);
                    fabMenu.showMenuButton(true);
                    fabMenu.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_from_bottom));
                    fabMenu.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.hide_to_bottom));
                }
            }, 300);
            swipe.setVisibility(View.VISIBLE);
            baseLoading.setVisibility(View.GONE);
            adapter = new EasyAdapter(data, R.layout.item_main, views, this);
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
        if(swipe.isRefreshing())swipe.setRefreshing(false);
    }

    @Override
    public void bindItemView(EasyAdapter.EasyHolder holder, Object item, final int position) {
        EasyMap itemData = (EasyMap) item;
        List<EasyMap> imgList = itemData.getList("item_pics");
        ImageLoader.getInstance().displayImage(itemData.getString("owner_head_pic"), holder.img.get(0), CommenUtil.options);
        if(imgList!=null&&imgList.size() > 0){
            int W = CommenUtil.getScreenMetrics(this).x - CommenUtil.dip2px(this, 10);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.img.get(1).getLayoutParams();
            layoutParams.height = W * 3 / 4;
            holder.img.get(1).setLayoutParams(layoutParams);
            holder.img.get(1).setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(imgList.get(0).getString("pic_url"), holder.img.get(1), CommenUtil.options);
        }else{
            holder.img.get(1).setVisibility(View.GONE);
        }
        holder.tv.get(0).setText(itemData.getString("owner_nickname"));
        holder.tv.get(1).setText(getNewsType(itemData.getInt("object_type", 1)));
        holder.tv.get(2).setText(new EasyUrl(this).replace(itemData.getString("description")));
        holder.tv.get(2).setMovementMethod(LinkMovementMethod.getInstance());
        holder.img.get(1).setTag(item);
        holder.img.get(1).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_fab:

                break;
            case R.id.item_home_pic:
                EasyMap map = (EasyMap) view.getTag();
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("id", map.getString("object_id"));
                intent.putExtra("uid", map.getString("owner_id"));
                intent.putExtra("name", map.getString("owner_nickname"));
                intent.putExtra("pic", map.getString("owner_head_pic"));
                go(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }

    private String getNewsType(int type){
        String temp = "";
        switch (type){
            case 1:
                temp = "求购";
                break;
            case 2:
                temp = "分享";
                break;
            case 3:
                temp = "商品";
                break;
            case 4:
                temp = "用户";
                break;
            case 5:
                temp = "活动";
                break;
            case 6:
                temp = "拼单";
                break;
            default:
                break;
        }
        return temp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
