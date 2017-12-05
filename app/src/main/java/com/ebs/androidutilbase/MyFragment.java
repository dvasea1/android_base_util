package com.ebs.androidutilbase;

import android.view.View;

import com.ebs.android_base_utility.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by barbaros.vasile on 10/5/2017.
 */

public class MyFragment extends BaseFragment {

    public static MyFragment newInstance(){
        return new MyFragment();
    }
    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment;
    }

    @Override
    public void onActivityCreated() {
        super.onActivityCreated();
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(R.id.root,MyFragment.newInstance(),true,true,true);
            }
        });
    }
}
