package com.ebs.androidutilbase;

import android.support.v7.widget.RecyclerView;

import com.ebs.android_base_utility.base.BaseFragmentActivity;

import butterknife.BindView;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onActivityCreated() {
        super.onActivityCreated();

    }

}
