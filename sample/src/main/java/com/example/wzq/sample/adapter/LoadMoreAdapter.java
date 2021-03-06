package com.example.wzq.sample.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * Created by wzq on 15/5/11.
 */
abstract public class LoadMoreAdapter extends OnScrollListener {


    private int firstVisibleItem, visibleItems, totalItems;

    public LoadMoreAdapter() {
        super();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        freeSpace(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            visibleItems = linearLayoutManager.getChildCount();
            totalItems = linearLayoutManager.getItemCount();
            if ((visibleItems + firstVisibleItem) >= totalItems) {
                loadData(totalItems);
            }
        }
    }

    abstract protected void loadData(int size);

    protected void freeSpace(RecyclerView recyclerView, int dx, int dy) {

    }
}
