package com.example.wzq.sample.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wzq.sample.R;
import com.example.wzq.sample.util.CommenUtil;
import com.example.wzq.sample.util.Constants;
import com.example.wzq.sample.util.EasyMap;
import com.example.wzq.sample.util.network.EasyListener;
import com.example.wzq.sample.util.network.VolleyHelper;
import com.example.wzq.sample.view.FadingActionBarHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by wzq on 15/4/14.
 */
public class DetailActivity extends BaseActivity implements EasyListener.CallBack {

    private ImageView headPic;

    private List<EasyMap> imgList;

    @Override
    protected void mainCode(Bundle savedInstanceState) {
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.color.swipe_b)
                .headerLayout(R.layout.view_fading_header)
                .contentLayout(R.layout.activity_detail);
        setContentView(helper.createView(this));
        helper.initActionBar(this);
        openHome();
        initData();
        headPic = (ImageView) findViewById(R.id.fading_header_img);
        int H = CommenUtil.getScreenMetrics(this).y;
        LinearLayout.LayoutParams layoutParams =  (LinearLayout.LayoutParams) headPic.getLayoutParams();
        layoutParams.height = H * 9 / 16;	//TODO  height 9/16 or equals screen width. looks like 9/16 is better
        headPic.setLayoutParams(layoutParams);
    }


    private void initData() {
        String id = getIntent().getStringExtra("id");
        String uid = getIntent().getStringExtra("uid");
        EasyMap params = new EasyMap();
        params.put("id", id);
        params.put("user_id", uid);
        VolleyHelper.getInstance(this).post(Constants.FIND_ITEMS_REQ, Constants.FIND_ITEMS, "post_json", params, EasyMap.class, this);
    }

    @Override
    public void updateUI(Object result, int reqCode) {
        EasyMap temp = (EasyMap) result;
        imgList = temp.getMap("post").getList("item_pics");
        if(imgList != null && imgList.size() > 0){
            String uri = imgList.get(0).getString("pic_url");
            ImageLoader.getInstance().displayImage(uri, headPic, CommenUtil.options);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            //overridePendingTransition(R.anim.anim_activity_in_f,R.anim.anim_activity_out_f);
        }
        return super.onOptionsItemSelected(item);
    }
}
