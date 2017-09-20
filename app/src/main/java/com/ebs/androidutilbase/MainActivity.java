package com.ebs.androidutilbase;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ebs.android_base_utility.base.BaseFragmentActivity;
import com.ebs.android_base_utility.base.recyclerview_utils.BaseAdapterRecycler;
import com.ebs.android_base_utility.base.recyclerview_utils.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

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
