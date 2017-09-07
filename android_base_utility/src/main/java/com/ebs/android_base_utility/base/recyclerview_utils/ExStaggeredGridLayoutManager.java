package com.ebs.android_base_utility.base.recyclerview_utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class ExStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    private final String TAG = getClass().getSimpleName();

    GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    public ExStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return mSpanSizeLookup;
    }

    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        mSpanSizeLookup = spanSizeLookup;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        //Log.d(TAG, "item count = " + getItemCount());
        for (int i = 0; i < getItemCount(); i++) {

            if(mSpanSizeLookup!=null) {
                if (mSpanSizeLookup.getSpanSize(i) > 1) {
                    //Log.d(TAG, "lookup > 1 = " + i);
                    try {
                        View view = recycler.getViewForPosition(i);
                        if (view != null) {
                            LayoutParams lp = (LayoutParams) view.getLayoutParams();
                            lp.setFullSpan(true);
                        }
                        // recycler.recycleView(view);
                    } catch (Exception e) {
                       // e.printStackTrace();
                    }
                }
            }
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }
}