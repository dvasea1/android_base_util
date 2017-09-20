package com.ebs.android_base_utility.base.recyclerview_utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;




public class RecyclerViewUtils {

    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {

            int headerViewCounter = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - headerViewCounter;
            }
        }

        return holder.getLayoutPosition();
    }

    public static RecyclerView.ItemAnimator getAnimator(){
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        itemAnimator.setRemoveDuration(500);
        return itemAnimator;
    }

    public static LinearLayoutManager getLayoutManager(Context context, boolean horizontal){
        int orientation = LinearLayoutManager.VERTICAL;
        if(horizontal){
            orientation = LinearLayoutManager.HORIZONTAL;
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context,orientation,false);
        mLayoutManager.setAutoMeasureEnabled(true);
        return mLayoutManager;
    }
    public static GridLayoutManager getGridLayoutManager(Context context, int columns){
        return new GridLayoutManager(context,columns);

    }

    public static CustomGridLayoutManager getCustomGridLayoutManager(Context context, int columns){
        return new CustomGridLayoutManager(context,columns);

    }

    public static class CustomGridLayoutManager extends GridLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomGridLayoutManager(Context context,int columns) {
            super(context,columns);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            return isScrollEnabled && super.canScrollVertically();
        }
    }

    public static ExStaggeredGridLayoutManager getStaggeredGridLayoutManager(int columns){
        return new ExStaggeredGridLayoutManager(columns,StaggeredGridLayoutManager.VERTICAL);
    }

    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, LoadingFooter.State state, @LayoutRes int resourceLayout, @IdRes int ResourceIdRoot, @IdRes int ResourceIdProgress) {
        System.out.println("state is "+state);

        if(instance==null || instance.isFinishing()) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;


        LoadingFooter footerView;

        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();
            footerView.setState(state);
        } else {
            footerView = new LoadingFooter(instance,resourceLayout,ResourceIdRoot,ResourceIdProgress);
            footerView.setState(state);
            headerAndFooterAdapter.addFooterView(footerView);
        }
    }

    public static LoadingFooter.State getFooterViewState(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                System.out.println("onLoadNextPage no footer");
                return footerView.getState();
            }
        }

        return LoadingFooter.State.Normal;
    }
}