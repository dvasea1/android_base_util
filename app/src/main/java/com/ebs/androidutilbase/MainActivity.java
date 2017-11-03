package com.ebs.androidutilbase;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebs.android_base_utility.base.BaseFragmentActivity;
import com.ebs.android_base_utility.base.recyclerview_utils.RecyclerLazyLoad;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {

    @Override
    public int getLayoutResourceIdLoading() {
        return R.layout.loading;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }


    @Override
    public void onActivityCreated() {
        super.onActivityCreated();

    }



}
