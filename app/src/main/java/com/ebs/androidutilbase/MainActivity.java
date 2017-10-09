package com.ebs.androidutilbase;

import com.ebs.android_base_utility.base.BaseFragmentActivity;

import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {



    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onActivityCreated() {
        super.onActivityCreated();
      ///change();
    }

    @OnClick(R.id.btn) void click(){
        changeFragmentPopup(R.id.root,MyFragment.newInstance(),true,true,false);
    }
    public void change(){
        changeFragmentPopup(R.id.root,MyFragment.newInstance(),true,true,false);
    }
}
