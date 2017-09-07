package com.ebs.android_base_utility.base.recyclerview_utils;

import android.view.View;


public interface OnListLoadNextPageListener {

     void onLoadNextPage(View view);
     void onScroll(int dx, int dy);
     void onScrollStateChanged(int newState);
}
